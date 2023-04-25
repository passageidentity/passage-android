package id.passage.android

import android.app.Activity
import androidx.credentials.exceptions.CreateCredentialException
import androidx.credentials.exceptions.GetCredentialException
import com.squareup.moshi.Moshi
import id.passage.android.PassageException.Companion.checkException
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
import id.passage.android.model.ApiloginWebAuthnFinishRequest
import id.passage.android.model.ApiloginWebAuthnStartRequest
import id.passage.android.model.ApiregisterMagicLinkRequest
import id.passage.android.model.ApiregisterWebAuthnFinishRequest
import id.passage.android.model.ApiregisterWebAuthnStartRequest
import id.passage.android.model.LoginOneTimePasscodeRequest
import id.passage.android.model.ProtocolCredentialAssertionResponseJsonAdapter
import id.passage.android.model.ProtocolCredentialCreationResponseJsonAdapter
import id.passage.android.model.ProtocolPublicKeyCredentialCreationOptionsJsonAdapter
import id.passage.android.model.ProtocolPublicKeyCredentialRequestOptionsJsonAdapter
import id.passage.android.model.RegisterOneTimePasscodeRequest

@Suppress("UNUSED")
class Passage(private val activity: Activity) {

    internal companion object {
        internal const val TAG = "Passage"
        internal const val BASE_PATH = "https://auth-uat.passage.dev/v1"
        internal const val REGISTRATION_RESPONSE_BUNDLE_KEY = "androidx.credentials.BUNDLE_KEY_REGISTRATION_RESPONSE_JSON"
        private const val AUTH_RESPONSE_BUNDLE_KEY = "androidx.credentials.BUNDLE_KEY_AUTHENTICATION_RESPONSE_JSON"

        internal lateinit var appId: String
        internal var language: String? = null

        private fun getOptionalResourceFromApp(activity: Activity, resName: String): String? {
            val stringRes = activity.resources.getIdentifier(resName, "string", activity.packageName)
            if (stringRes == 0) return null
            return activity.getString(stringRes)
        }
        private fun getRequiredResourceFromApp(activity: Activity, resName: String): String {
            val stringRes = getOptionalResourceFromApp(activity, resName)
            require(!stringRes.isNullOrBlank()) {
                String.format(
                    "The 'R.string.%s' value it's not defined in your project's resources file.",
                    resName
                )
            }
            return stringRes
        }

    }

    private var passageTokenStore: PassageTokenStore? = null

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

    // region SIMPLE AUTH METHODS
    // TODO: This method should attempt a passkey registration, then try the designated fallback,
    // and throw specific errors if/when any of the steps fail.
    suspend fun register(identifier: String) {
        // Check if we should use passkeys
        registerWithPasskey(identifier)
        // Handle fallback methods
    }

    // TODO: This method should attempt a passkey login, then try the designated fallback,
    // and throw specific errors if/when any of the steps fail.
    suspend fun login(identifier: String) {
        // Check if we should use passkeys
        loginWithPasskey(identifier)
        // Handle fallback methods
    }

    // endregion

    // region PASSKEY AUTH METHODS

    /**
     * Register user with passkey
     *
     * Create a user, prompt the user to create a passkey, and register the user.
     * @param identifier valid email or E164 phone number
     * @return PassageAuthResult?
     * @throws CreateCredentialException If the attempt to create a passkey fails
     * @throws PassageClientException If the Passage API returns a client error response
     * @throws PassageServerException If the Passage API returns a server error response
     * @throws PassageException If the request fails for another reason
     */
    suspend fun registerWithPasskey(identifier: String): PassageAuthResult? {
        try {
            // Get Create Credential challenge from Passage
            val registerAPI = RegisterAPI(BASE_PATH)
            val webauthnStartRequest = ApiregisterWebAuthnStartRequest(identifier)
            val webauthnStartResponse =
                registerAPI.registerWebauthnStart(appId, webauthnStartRequest)
            val createCredOptions = webauthnStartResponse.handshake?.challenge?.publicKey
                ?: throw PassageWebAuthnException(PassageWebAuthnException.CHALLENGE_MISSING)
            // Use Create Credential challenge to prompt user to create a passkey
            val moshi = Moshi.Builder().build()
            val createCredOptionsAdapter =
                ProtocolPublicKeyCredentialCreationOptionsJsonAdapter(moshi)
            val createCredOptionsJson = createCredOptionsAdapter.toJson(createCredOptions)
                ?: throw PassageWebAuthnException(PassageWebAuthnException.PARSING_FAILED)
            val createCredResponse = PasskeyUtils.createPasskey(createCredOptionsJson, activity)
            // Complete registration and authenticate the user
            val handshakeResponseJson =
                createCredResponse.data.getString(REGISTRATION_RESPONSE_BUNDLE_KEY).toString()
            val handshakeResponseAdapter = ProtocolCredentialCreationResponseJsonAdapter(moshi)
            val handshakeResponse = handshakeResponseAdapter.fromJson(handshakeResponseJson)
                ?: throw PassageCredentialException(PassageCredentialException.PARSING_FAILED)
            val webauthnFinishRequest = ApiregisterWebAuthnFinishRequest(
                handshakeId = webauthnStartResponse.handshake.id,
                handshakeResponse = handshakeResponse,
                userId = webauthnStartResponse.user?.id
            )
            val authResponse = registerAPI.registerWebauthnFinish(appId, webauthnFinishRequest)
            handleAuthResult(authResponse.authResult)
            // Return auth result
            return authResponse.authResult
        } catch (e: Exception) {
            throw checkException(e)
        }
    }

    /**
     * Log in user with passkey
     *
     * Prompt the user to select a passkey to login with and authenticate the user.
     * @param identifier valid email or E164 phone number
     * @return PassageAuthResult?
     * @throws GetCredentialException If the attempt to create a passkey fails
     * @throws PassageClientException If the Passage API returns a client error response
     * @throws PassageServerException If the Passage API returns a server error response
     * @throws PassageException If the request fails for another reason
     */
    suspend fun loginWithPasskey(identifier: String): PassageAuthResult? {
        try {
            // Get Credential challenge from Passage
            val loginAPI = LoginAPI(BASE_PATH)
            val webauthnStartRequest = ApiloginWebAuthnStartRequest(identifier)
            val webauthnStartResponse = loginAPI.loginWebauthnStart(appId, webauthnStartRequest)
            val credOptions = webauthnStartResponse.handshake?.challenge?.publicKey
                ?: throw PassageWebAuthnException(PassageWebAuthnException.CHALLENGE_MISSING)
            // Use Credential challenge to prompt user to login with a passkey
            val moshi = Moshi.Builder().build()
            val credOptionsAdapter = ProtocolPublicKeyCredentialRequestOptionsJsonAdapter(moshi)
            val credOptionsJson = credOptionsAdapter.toJson(credOptions)
                ?: throw PassageWebAuthnException(PassageWebAuthnException.PARSING_FAILED)
            val credResponse = PasskeyUtils.getPasskey(credOptionsJson, activity)
            // Complete login and authenticate the user
            val handshakeResponseJson = credResponse.credential.data.getString(AUTH_RESPONSE_BUNDLE_KEY).toString()
            val handshakeResponseAdapter = ProtocolCredentialAssertionResponseJsonAdapter(moshi)
            val handshakeResponse = handshakeResponseAdapter.fromJson(handshakeResponseJson)
                ?: throw PassageCredentialException(PassageCredentialException.PARSING_FAILED)
            val webauthnFinishRequest = ApiloginWebAuthnFinishRequest(
                handshakeId = webauthnStartResponse.handshake.id,
                handshakeResponse = handshakeResponse,
                userId = webauthnStartResponse.user?.id
            )
            val authResponse = loginAPI.loginWebauthnFinish(appId, webauthnFinishRequest)
            handleAuthResult(authResponse.authResult)
            // Return auth result
            return authResponse.authResult
        } catch(e: Exception) {
            throw checkException(e)
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
     * @throws PassageClientException If the API returns a client error response
     * @throws PassageServerException If the API returns a server error response
     * @throws PassageException If the request fails for another reason
     */
    suspend fun newRegisterMagicLink(identifier: String, magicLinkPath: String? = null): MagicLink? {
        val registerAPI = RegisterAPI(BASE_PATH)
        val request = ApiregisterMagicLinkRequest(
            identifier = identifier,
            language = language,
            magicLinkPath = magicLinkPath
        )
        val response = try {
            registerAPI.registerMagicLink(appId, request)
        } catch (e: Exception) {
            throw checkException(e)
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
     * @throws PassageClientException If the API returns a client error response
     * @throws PassageServerException If the API returns a server error response
     * @throws PassageException If the request fails for another reason
     */
    suspend fun newLoginMagicLink(identifier: String, magicLinkPath: String? = null): MagicLink? {
        val loginAPI = LoginAPI(BASE_PATH)
        val request = ApiloginMagicLinkRequest(
            identifier = identifier,
            language = language,
            magicLinkPath = magicLinkPath
        )
        val response = try {
            loginAPI.loginMagicLink(appId, request)
        } catch (e: Exception) {
            throw checkException(e)
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
     * @throws PassageClientException If the API returns a client error response
     * @throws PassageServerException If the API returns a server error response
     * @throws PassageException If the request fails for another reason
     */
    suspend fun magicLinkActivate(userMagicLink: String): PassageAuthResult? {
        val magicLinkAPI = MagicLinkAPI(BASE_PATH)
        val request = ApiactivateMagicLinkRequest(userMagicLink)
        val response = try {
            magicLinkAPI.activateMagicLink(appId, request)
        } catch (e: Exception) {
            throw checkException(e)
        }
        handleAuthResult(response.authResult)
        return response.authResult
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
     * @throws PassageClientException If the API returns a client error response
     * @throws PassageServerException If the API returns a server error response
     * @throws PassageException If the request fails for another reason
     */
    suspend fun getMagicLinkStatus(magicLinkId: String): PassageAuthResult? {
        val magicLinkAPI = MagicLinkAPI(BASE_PATH)
        val request = ApigetMagicLinkStatusRequest(magicLinkId)
        val response = try {
            magicLinkAPI.magicLinkStatus(appId, request)
        } catch (e: Exception) {
            throw checkException(e)
        }
        handleAuthResult(response.authResult)
        return response.authResult
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
     * @throws PassageClientException If the API returns a client error response
     * @throws PassageServerException If the API returns a server error response
     * @throws PassageException If the request fails for another reason
     */
    suspend fun newRegisterOneTimePasscode(identifier: String): OneTimePasscode {
        val registerAPI = RegisterAPI(BASE_PATH)
        val request = RegisterOneTimePasscodeRequest(identifier, language)
        val response = try {
            registerAPI.registerOneTimePasscode(appId, request)
        } catch (e: Exception) {
            throw checkException(e)
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
     * @throws PassageClientException If the API returns a client error response
     * @throws PassageServerException If the API returns a server error response
     * @throws PassageException If the request fails for another reason
     */
    suspend fun newLoginOneTimePasscode(identifier: String): OneTimePasscode {
        val loginAPI = LoginAPI(BASE_PATH)
        val request = LoginOneTimePasscodeRequest(identifier, language)
        val response = try {
            loginAPI.loginOneTimePasscode(appId, request)
        } catch (e: Exception) {
            throw checkException(e)
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
     * @throws PassageClientException If the API returns a client error response
     * @throws PassageServerException If the API returns a server error response
     * @throws PassageException If the request fails for another reason
     */
    suspend fun oneTimePasscodeActivate(otp: String, otpId: String): PassageAuthResult {
        val otpAPI = OTPAPI(BASE_PATH)
        val request = ActivateOneTimePasscodeRequest(otp, otpId)
        val response = try {
            otpAPI.activateOneTimePasscode(appId, request)
        } catch (e: Exception) {
            throw checkException(e)
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

    /**
     * Get Current User
     * Returns an instance of PassageUser, which represents an authenticated Passage user.
     * The PassageUser class has methods that can be used to retrieve data on the current user
     * which require authentication.
     * @return PassageUser?
     * @throws PassageClientException If the API returns a client error response
     * @throws PassageServerException If the API returns a server error response
     * @throws PassageException If the request fails for another reason
     */
    suspend fun getCurrentUser(): PassageUser? {
        val user = try {
            PassageUser.getCurrentUser()
        } catch (e: Exception) {
            throw checkException(e)
        }
        return user
    }

    /**
     * App Info
     * Get information about an application.
     * @return PassageApp?
     * @throws PassageClientException If the API returns a client error response
     * @throws PassageServerException If the API returns a server error response
     * @throws PassageException If the request fails for another reason
     */
    suspend fun appInfo(): PassageApp? {
        val appsAPI = AppsAPI()
        val appInfo = try {
            appsAPI.getApp(appId)
        } catch (e: Exception) {
            throw checkException(e)
        }
        return appInfo.app
    }

    /**
     * Identifier Exists
     *
     * Checks if the identifier provided exists for the application. This method should be used to
     * determine whether to register or log in a user. This method also checks that the app supports
     * the identifier types (e.g., it will throw an error if a phone number is supplied to an app
     * that only supports emails as an identifier).
     * @return PassageUser?
     * @throws PassageClientException If the API returns a client error response
     * @throws PassageServerException If the API returns a server error response
     * @throws PassageException If the request fails for another reason
     */
    suspend fun identifierExists(identifier: String): PassageUser? {
        val usersAPI = UsersAPI()
        val modelsUser = try {
            usersAPI.checkUserIdentifier(BASE_PATH, identifier).user
        } catch (e: Exception) {
            throw checkException(e)
        } ?: return null
        return PassageUser.convertToPassageUser(modelsUser)
    }

    private fun handleAuthResult(authResult: PassageAuthResult?) {
        authResult?.let {
            passageTokenStore?.setTokens(it)
        }
    }

}
