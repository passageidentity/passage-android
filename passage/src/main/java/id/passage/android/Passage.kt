package id.passage.android

import android.app.Activity
import android.util.Log
import android.webkit.WebSettings
import id.passage.android.ResourceUtils.Companion.getOptionalResourceFromApp
import id.passage.android.ResourceUtils.Companion.getRequiredResourceFromApp
import id.passage.android.api.AppsAPI
import id.passage.android.api.LoginAPI
import id.passage.android.api.MagicLinkAPI
import id.passage.android.api.OTPAPI
import id.passage.android.api.RegisterAPI
import id.passage.android.api.UsersAPI
import id.passage.android.model.ActivateOneTimePasscodeRequest
import id.passage.android.model.ApiactivateMagicLinkRequest
import id.passage.android.model.ApigetMagicLinkStatusRequest
import id.passage.android.model.ApiloginMagicLinkRequest
import id.passage.android.model.ApiloginWebAuthnStartRequest
import id.passage.android.model.ApiregisterMagicLinkRequest
import id.passage.android.model.LoginOneTimePasscodeRequest
import id.passage.android.model.LoginWebAuthnFinishRequest
import id.passage.android.model.RegisterOneTimePasscodeRequest
import id.passage.android.model.RegisterWebAuthnFinishRequest
import id.passage.android.model.RegisterWebAuthnStartRequest
import id.passage.client.infrastructure.ApiClient
import id.passage.android.exceptions.*
import okhttp3.OkHttpClient

@Suppress("unused", "RedundantVisibilityModifier", "RedundantModalityModifier")
public final class Passage(private val activity: Activity) {

    // region CONSTANTS AND SINGLETON VARIABLES
    internal companion object {
        internal const val TAG = "Passage"
        internal const val BASE_PATH = "https://auth-uat.passage.dev/v1"

        internal lateinit var appId: String
        internal var language: String? = null
    }
    // endregion

    // region INSTANCE VARIABLES

    private var passageTokenStore: PassageTokenStore? = null

    private val passageClient: OkHttpClient by lazy {
        val userAgent = WebSettings.getDefaultUserAgent(activity) ?: "Android"
        OkHttpClient.Builder()
            .addNetworkInterceptor { chain ->
                chain.proceed(
                    chain.request()
                        .newBuilder()
                        .header("User-Agent", userAgent)
                        .build()
                )
            }
            .build()
    }

    // endregion

    // region INITIALIZATION

    init {
        appId = getRequiredResourceFromApp(activity, "passage_app_id")
        language = getOptionalResourceFromApp(activity, "passage_language")

        // NOTE: Not sure I love this implementation yet
        val usePassageStore = getOptionalResourceFromApp(activity, "use_passage_store")
        if (usePassageStore == "true") {
            passageTokenStore = PassageTokenStore(activity)
        }
    }

    // endregion

    // region TOKEN METHODS
    // TODO: Document this method. Its for developer if they don't want to use the PassageStore
    public fun setAuthToken(token: String?) {
        ApiClient.accessToken = token
    }

    public fun getAuthToken(): String? {
        return ApiClient.accessToken
    }

    // endregion

    // region PASSAGE APP METHODS

    /**
     * App Info
     *
     * Get information about an application.
     * @return PassageApp?
     * @throws AppInfoException
     */
    public suspend fun appInfo(): PassageApp? {
        val appsAPI = AppsAPI(BASE_PATH, passageClient)
        return try {
            appsAPI.getApp(appId)
        } catch (e: Exception) {
            throw AppInfoException.convert(e)
        }.app
    }

    // endregion

    // region SIMPLE AUTH METHODS

    /**
     * Register
     *
     * Register a new user using a passkey. If your app requires identifier verification or if
     * passkey creation fails, this method will attempt to register the user using a fallback method
     * instead (one time passcode or magic link).
     *
     * If a PassageAuthResult is returned the user was registered and logged in.  If a
     * PassageAuthFallbackResult is returned the user was sent a magic link or one time passcode,
     * and if neither are returned, then the user was not registered and no fallback method was used.
     * @param identifier valid email or E164 phone number
     * @return Pair<PassageAuthResult?, PassageAuthFallbackResult?>
     * @throws RegisterException when there's a registration issue with the Passage app setup
     * @throws NewRegisterMagicLinkException when passkey registration AND magic link creation fails
     * @throws NewRegisterOneTimePasscodeException when passkey registration AND otp generation fails
     */
    public suspend fun register(identifier: String): Pair<PassageAuthResult?, PassageAuthFallbackResult?> {
        val passageApp = appInfo()
            ?: throw RegisterInvalidAppException()
        if (passageApp.publicSignup == false) {
            throw RegisterPublicDisabledException()
        }
        val user = try {
            identifierExists(identifier)
        } catch(_: Exception) {
            null
        }
        if (user != null) throw RegisterUserExistsException()
        val useFallback = passageApp.requireIdentifierVerification == true
        if (!useFallback) {
            try {
                val authResult = registerWithPasskey(identifier)
                return Pair(authResult, null)
            } catch(e: Exception) {
                Log.e(TAG, "Passkey registration attempt failed. ${e.message ?: e.toString()}")
            }
        }
        val fallbackMethod = passageApp.authFallbackMethod
            ?: throw RegisterNoFallbackException()
        val fallback: PassageAuthFallbackResult = when (fallbackMethod) {
            PassageAuthFallbackMethod.magicLink -> {
                val magicLinkId = newRegisterMagicLink(identifier)?.id ?: ""
                PassageAuthFallbackResult(id = magicLinkId, method = fallbackMethod)
            }
            PassageAuthFallbackMethod.otp -> {
                val otpId = newRegisterOneTimePasscode(identifier).otpId
                PassageAuthFallbackResult(id = otpId, method = fallbackMethod)
            }
            PassageAuthFallbackMethod.none -> {
                throw RegisterNoFallbackException()
            }
        }
        return Pair(null, fallback)
    }

    /**
     * Log In
     *
     * Log in an existing user with a passkey. If user does not have a passkey or passkey login
     * fails, this method will attempt to login with a fallback method instead (one time passcode or
     * magic link).
     *
     * If a PassageAuthResult is returned the user was logged in.  If a PassageAuthFallbackResult is
     * returned the user was sent a magic link or a one time passcode, and if neither are returned,
     * then the user was not logged in and no fallback method was used.
     *
     * @param identifier valid email or E164 phone number
     * @return PassageAuthResult?
     * @throws LoginException when there's a login issue with the Passage app setup
     * @throws NewLoginMagicLinkException when passkey login AND magic link creation fails
     * @throws NewLoginOneTimePasscodeException when passkey login AND otp generation fails
     */
    public suspend fun login(identifier: String): Pair<PassageAuthResult?, PassageAuthFallbackResult?> {
        val passageApp = appInfo()
            ?: throw LoginInvalidAppException()
        // If app requires id verification and user has not yet logged in with a passkey, use a
        // fallback method
        val user = identifierExists(identifier)
            ?: throw LoginNoExistingUserException()
        val useFallback = passageApp.requireIdentifierVerification == true && user.webauthn == false
        if (!useFallback) {
            try {
                val authResult = loginWithPasskey(identifier)
                return Pair(authResult, null)
            } catch (e: Exception) {
                Log.e(TAG, "Passkey login attempt failed. ${e.message ?: e.toString()}")
            }
        }
        // If useFallback or if passkey login fails, attempt new fallback login.
        val fallbackMethod = passageApp.authFallbackMethod
            ?: throw LoginNoFallbackException()
        val fallback: PassageAuthFallbackResult =
            when (fallbackMethod) {
                PassageAuthFallbackMethod.magicLink -> {
                    val magicLinkId = newLoginMagicLink(identifier)?.id ?: ""
                    PassageAuthFallbackResult(id = magicLinkId, method = fallbackMethod)
                }
                PassageAuthFallbackMethod.otp -> {
                    val otpId = newLoginOneTimePasscode(identifier).otpId
                    PassageAuthFallbackResult(id = otpId, method = fallbackMethod)
                }
                PassageAuthFallbackMethod.none -> {
                    throw LoginNoFallbackException()
                }
            }
        return Pair(null, fallback)
    }

    /**
     * Autofill Passkey Login
     *
     * If the user has a passkey for this app, prompt the user to select a passkey to login with and
     * authenticate the user. If the user does not have a passkey or login fails, returns null.
     *
     * @return PassageAuthResult?
     */
    public suspend fun autofillPasskeyLogin(): PassageAuthResult? {
        return try {
            loginWithPasskey("")
        } catch (e: Exception) {
            Log.e(TAG, e.message ?: e.toString())
            null
        }
    }

    // endregion

    // region PASSKEY AUTH METHODS

    /**
     * Register user with passkey
     *
     * Create a user, prompt the user to create a passkey, and register the user.
     * @param identifier valid email or E164 phone number
     * @return PassageAuthResult?
     * @throws RegisterWithPasskeyException
     */
    public suspend fun registerWithPasskey(identifier: String): PassageAuthResult {
        try {
            val registerAPI = RegisterAPI(BASE_PATH, passageClient)
            // Get Create Credential challenge from Passage
            val webauthnStartRequest = RegisterWebAuthnStartRequest(identifier)
            val webauthnStartResponse = registerAPI.registerWebauthnStart(appId, webauthnStartRequest)
            // Use Create Credential challenge to prompt user to create a passkey
            val createCredOptionsJson = PasskeyUtils.getCreateCredentialOptionsJson(webauthnStartResponse.handshake)
            val createCredResponse = PasskeyUtils.createPasskey(createCredOptionsJson, activity)
            // Complete registration and authenticate the user
            val handshakeResponse = PasskeyUtils.getCreateCredentialHandshakeResponse1(createCredResponse)
            val webauthnFinishRequest = RegisterWebAuthnFinishRequest(
                handshakeId = webauthnStartResponse.handshake.id,
                handshakeResponse = handshakeResponse,
                userId = webauthnStartResponse.user?.id ?: ""
            )
            val authResponse = registerAPI.registerWebauthnFinish(appId, webauthnFinishRequest)
            // Handle and return auth result
            handleAuthResult(authResponse.authResult)
            return authResponse.authResult
        } catch (e: Exception) {
            throw RegisterWithPasskeyException.convert(e)
        }
    }

    /**
     * Log in user with passkey
     *
     * Prompt the user to select a passkey to login with and authenticate the user.
     * @param identifier valid email or E164 phone number
     * @return PassageAuthResult?
     * @throws LoginWithPasskeyException
     */
    public suspend fun loginWithPasskey(identifier: String): PassageAuthResult {
        try {
            val loginAPI = LoginAPI(BASE_PATH, passageClient)
            // Get Credential challenge from Passage
            val webauthnStartRequest = ApiloginWebAuthnStartRequest(identifier)
            val webauthnStartResponse = loginAPI.loginWebauthnStart(appId, webauthnStartRequest)
            // Use Credential challenge to prompt user to login with a passkey
            val credOptionsJson = PasskeyUtils.getCredentialOptionsJson(webauthnStartResponse.handshake)
            val credResponse = PasskeyUtils.getPasskey(credOptionsJson, activity)
            // Complete login and authenticate the user
            val handshakeResponse = PasskeyUtils.getCredentialHandshakeResponse(credResponse)
            val webauthnFinishRequest = LoginWebAuthnFinishRequest(
                handshakeId = webauthnStartResponse.handshake?.id ?: "",
                handshakeResponse = handshakeResponse,
                userId = webauthnStartResponse.user?.id
            )
            val authResponse = loginAPI.loginWebauthnFinish(appId, webauthnFinishRequest)
            handleAuthResult(authResponse.authResult)
            // Return auth result
            return authResponse.authResult
        } catch(e: Exception) {
            throw LoginWithPasskeyException.convert(e)
        }
    }

    // endregion

    // region MAGIC LINK AUTH METHODS

    /**
     * Register with Magic Link
     *
     * Create a user and send a registration email or SMS to the user. The user will receive an email or text with a link to complete their registration.
     * @param identifier valid email or E164 phone number
     * @param magicLinkPath path relative to the app's auth origin (optional)
     * @return MagicLink
     * @throws NewRegisterMagicLinkException
     */
    public suspend fun newRegisterMagicLink(identifier: String, magicLinkPath: String? = null): MagicLink? {
        val registerAPI = RegisterAPI(BASE_PATH, passageClient)
        val request = ApiregisterMagicLinkRequest(
            identifier = identifier,
            language = language,
            magicLinkPath = magicLinkPath
        )
        val response = try {
            registerAPI.registerMagicLink(appId, request)
        } catch (e: Exception) {
            throw NewRegisterMagicLinkException.convert(e)
        }
        return response.magicLink
    }

    /**
     * Log in with Magic Link
     *
     * Send a login email or SMS to the user. The user will receive an email or text with a link to complete their login.
     * @param identifier valid email or E164 phone number
     * @param magicLinkPath path relative to the app's auth_origin (optional)
     * @return MagicLink?
     * @throws NewLoginMagicLinkException
     */
    public suspend fun newLoginMagicLink(identifier: String, magicLinkPath: String? = null): MagicLink? {
        val loginAPI = LoginAPI(BASE_PATH, passageClient)
        val request = ApiloginMagicLinkRequest(
            identifier = identifier,
            language = language,
            magicLinkPath = magicLinkPath
        )
        val response = try {
            loginAPI.loginMagicLink(appId, request)
        } catch (e: Exception) {
            throw NewLoginMagicLinkException.convert(e)
        }
        return response.magicLink
    }

    /**
     * Activate Magic Link
     *
     * Authenticate a magic link for a user. This endpoint checks that the magic link is valid,
     * then returns an authentication token for the user.
     * @param userMagicLink full magic link that starts with "ml" (sent via email or text to the user)
     * @return PassageAuthResult?
     * @throws MagicLinkActivateException
     */
    public suspend fun magicLinkActivate(userMagicLink: String): PassageAuthResult? {
        val magicLinkAPI = MagicLinkAPI(BASE_PATH, passageClient)
        val request = ApiactivateMagicLinkRequest(userMagicLink)
        val response = try {
            magicLinkAPI.activateMagicLink(appId, request)
        } catch (e: Exception) {
            throw MagicLinkActivateException.convert(e)
        }
        // TODO: Once BE issue is fixed, we won't need to transform data model
        val authResult = PassageAuthResult(
            authToken = response.authResult?.authToken ?: "",
            redirectUrl = response.authResult?.redirectUrl ?: "",
            refreshToken = response.authResult?.refreshToken,
            refreshTokenExpiration = response.authResult?.refreshTokenExpiration
        )
        handleAuthResult(authResult)
        return authResult
    }

    /**
     * Magic Link Status
     *
     * Check if a magic link has been activated yet or not. Once the magic link has been activated,
     * this endpoint will return an authentication token for the user. This endpoint can be used to
     * initiate a login in one device and then poll and wait for the login to complete on another
     * device.
     * @param magicLinkId Magic Link ID
     * @return PassageAuthResult?
     * @throws GetMagicLinkStatusException
     */
    public suspend fun getMagicLinkStatus(magicLinkId: String): PassageAuthResult? {
        val magicLinkAPI = MagicLinkAPI(BASE_PATH, passageClient)
        val request = ApigetMagicLinkStatusRequest(magicLinkId)
        val response = try {
            magicLinkAPI.magicLinkStatus(appId, request)
        } catch (e: Exception) {
            val exception = GetMagicLinkStatusException.convert(e)
            if (exception is GetMagicLinkStatusNotFoundException) {
                Log.w(TAG, "Magic link not activated.")
                return null
            }
            throw exception
        }
        // TODO: Once BE issue is fixed, we won't need to transform data model
        val authResult = PassageAuthResult(
            authToken = response.authResult?.authToken ?: "",
            redirectUrl = response.authResult?.redirectUrl ?: "",
            refreshToken = response.authResult?.refreshToken,
            refreshTokenExpiration = response.authResult?.refreshTokenExpiration
        )
        handleAuthResult(authResult)
        return authResult
    }

    // endregion

    // region ONE TIME PASSCODE AUTH METHODS

    /**
     * Register with One Time Passcode
     *
     * Create a user and send a registration email or SMS to the user. The user will receive an
     * email or text with a one time passcode to complete their registration.
     * @param identifier valid email or E164 phone number
     * @return OneTimePasscode
     * @throws NewRegisterOneTimePasscodeException
     */
    public suspend fun newRegisterOneTimePasscode(identifier: String): OneTimePasscode {
        val registerAPI = RegisterAPI(BASE_PATH, passageClient)
        val request = RegisterOneTimePasscodeRequest(identifier, language)
        val response = try {
            registerAPI.registerOneTimePasscode(appId, request)
        } catch (e: Exception) {
            throw NewRegisterOneTimePasscodeException.convert(e)
        }
        return response
    }

    /**
     * Login with One Time Passcode
     *
     * Send a login email or SMS to the user. The user will receive an email or text with a one time
     * passcode to complete their login.
     * @param identifier valid email or E164 phone number
     * @return OneTimePasscode
     * @throws NewLoginOneTimePasscodeException
     */
    public suspend fun newLoginOneTimePasscode(identifier: String): OneTimePasscode {
        val loginAPI = LoginAPI(BASE_PATH, passageClient)
        val request = LoginOneTimePasscodeRequest(identifier, language)
        val response = try {
            loginAPI.loginOneTimePasscode(appId, request)
        } catch (e: Exception) {
            throw NewLoginOneTimePasscodeException.convert(e)
        }
        return response
    }

    /**
     * Authenticate One Time Passcode
     *
     * Authenticate a one time passcode for a user. This endpoint checks that the one time passcode
     * is valid, then returns an authentication token for the user.
     * @param otp The one time passcode
     * @param otpId The OTP id
     * @return PassageAuthResult
     * @throws OneTimePasscodeActivateException
     */
    public suspend fun oneTimePasscodeActivate(otp: String, otpId: String): PassageAuthResult {
        val otpAPI = OTPAPI(BASE_PATH, passageClient)
        val request = ActivateOneTimePasscodeRequest(otp, otpId)
        val response = try {
            otpAPI.activateOneTimePasscode(appId, request)
        } catch (e: Exception) {
            throw OneTimePasscodeActivateException.convert(e)
        }
        val otpAuthResult = response.authResult
        // NOTE: The OpenAPI codegen produces an `IdentityAuthResult` for passkey and magic link
        // responses, but produces an `AuthResult` for OTP. They have the same shape, so we
        // created a `PassageAuthResult` alias for `IdentityAuthResult`, and return that alias
        // from all of the methods that produce an auth result. This should be temporary, until
        // the Passage API returns just `AuthResult` for all.
        val authResult = PassageAuthResult(
            authToken = otpAuthResult.authToken,
            redirectUrl = otpAuthResult.redirectUrl,
            refreshToken = otpAuthResult.refreshToken,
            refreshTokenExpiration = otpAuthResult.refreshTokenExpiration
        )
        handleAuthResult(authResult)
        return authResult
    }

    // endregion

    // region USER METHODS

    /**
     * Get Current User
     *
     * Returns an instance of PassageUser, which represents an authenticated Passage user.
     * The PassageUser class has methods that can be used to retrieve data on the current user
     * which require authentication.
     * @return PassageUser?
     */
    public suspend fun getCurrentUser(): PassageUser? {
        if (passageTokenStore != null && ApiClient.accessToken == null) {
            passageTokenStore?.attemptRefreshTokenStore()
        }
        val user = try {
            PassageUser.getCurrentUser()
        } catch (e: Exception) {
            Log.w(TAG, "Getting current user failed. ${e.message ?: e.toString()}")
            null
        }
        return user
    }

    /**
     * Sign Out Current User
     *
     * If using Passage Token Store, calling this method will revoke the user's refresh token,
     * clear all tokens from the Passage Token Store, and set the client's access token to null.
     * If not using Passage Token Store, calling this method will set the client's access token to null.
     * @return void
     * @throws PassageTokenException
     */
    public suspend fun signOutCurrentUser() {
        passageTokenStore?.clearAndRevokeTokens()
            ?: setAuthToken(null)
    }

    /**
     * Identifier Exists
     *
     * Checks if the identifier provided exists for the application. This method should be used to
     * determine whether to register or log in a user. This method also checks that the app supports
     * the identifier types (e.g., it will throw an error if a phone number is supplied to an app
     * that only supports emails as an identifier).
     * @return PassageUser?
     */
    public suspend fun identifierExists(identifier: String): PassageUser? {
        val usersAPI = UsersAPI(BASE_PATH, passageClient)
        val modelsUser = try {
            usersAPI.checkUserIdentifier(appId, identifier).user
        } catch (e: Exception) {
            return null
        } ?: return null
        return PassageUser.convertToPassageUser(modelsUser)
    }

    // endregion

    // region PRIVATE METHODS

    /**
     * Handle Auth Result
     *
     * Anytime a user gets a new Auth Result, update the tokens in Passage Token Store (if applicable).
     * @param authResult The new Auth Result
     */
    private fun handleAuthResult(authResult: PassageAuthResult?) {
        passageTokenStore?.setTokens(authResult)
    }
    // endregion

}
