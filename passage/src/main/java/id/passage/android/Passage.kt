package id.passage.android

import android.app.Activity
import android.util.Log
import androidx.credentials.CreateCredentialResponse
import androidx.credentials.CreatePublicKeyCredentialRequest
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.GetPublicKeyCredentialOption
import androidx.credentials.exceptions.CreateCredentialException
import androidx.credentials.exceptions.GetCredentialException
import com.squareup.moshi.Moshi
import id.passage.android.api.LoginAPI
import id.passage.android.api.MagicLinkAPI
import id.passage.android.api.OTPAPI
import id.passage.android.api.RegisterAPI
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
import id.passage.client.infrastructure.ClientError
import id.passage.client.infrastructure.ServerError
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import java.io.IOException
import java.lang.IllegalStateException

@Suppress("UNUSED")
class Passage(private val activity: Activity) {

    private companion object {
        private const val TAG = "Passage"
        private const val BASE_PATH = "https://auth-uat.passage.dev/v1"
        private const val REGISTRATION_RESPONSE_BUNDLE_KEY = "androidx.credentials.BUNDLE_KEY_REGISTRATION_RESPONSE_JSON"
        private const val AUTH_RESPONSE_BUNDLE_KEY = "androidx.credentials.BUNDLE_KEY_AUTHENTICATION_RESPONSE_JSON"

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

        private fun checkException(e: Exception): Exception {
            return when (e) {
                is PassageClientException -> {
                    val errorBody = (e.response as? ClientError<*>)?.body?.toString()
                    errorBody?.let {
                        val message = PassageErrorBody.getMessageString(it)
                        return PassageClientException(message, e.statusCode, e.response)
                    }
                    e
                }
                is PassageServerException -> {
                    val errorBody = (e.response as? ServerError<*>)?.body?.toString()
                    errorBody?.let {
                        val message = PassageErrorBody.getMessageString(it)
                        return PassageServerException(message, e.statusCode, e.response)
                    }
                    e
                }
                else -> {
                    e
                }
            }
        }
    }

    // region INSTANCE VARIABLES

    private val appId = getRequiredResourceFromApp(activity, "passage_app_id")
    var language = getOptionalResourceFromApp(activity, "passage_language")

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
            val createCredResponse = createPasskey(createCredOptionsJson)
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
            // Return auth result
            return authResponse.authResult
        } catch (e: Exception) {
            throw checkException(e)
        }
    }

    /**
     * Registers a user passkey credential that can be used to authenticate the user to
     * the app in the future.
     *
     * The execution potentially launches framework UI flows for a user to view their registration
     * options, grant consent, etc.
     *
     * @param requestJson The privileged request in JSON format in the [standard webauthn web json](https://w3c.github.io/webauthn/#dictdef-publickeycredentialcreationoptionsjson).
     * @throws CreateCredentialException If the request fails
     */
    suspend fun createPasskey(requestJson: String): CreateCredentialResponse {
        val credentialManager = CredentialManager.create(activity)
        val publicKeyCredRequest = CreatePublicKeyCredentialRequest(requestJson)
        val publicKeyCredResponse = CoroutineScope(Dispatchers.IO).async {
            // Show the user Credential Manager with option to create a Passkey
            return@async credentialManager.createCredential(
                publicKeyCredRequest,
                activity
            )
        }.await()
        return publicKeyCredResponse
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
            val credResponse = getPasskey(credOptionsJson)
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
            // Return auth result
            return authResponse.authResult
        } catch(e: Exception) {
            throw checkException(e)
        }
    }

    /**
     * Requests a passkey credential from the user.
     *
     * The execution potentially launches framework UI flows for a user to view available
     * credentials, consent to using one of them, etc.
     *
     * @param requestJson The privileged request in JSON format in the [standard webauthn web json](https://w3c.github.io/webauthn/#dictdef-publickeycredentialcreationoptionsjson).
     * @throws GetCredentialException If the request fails
     */
    suspend fun getPasskey(requestJson: String): GetCredentialResponse {
        val credentialManager = CredentialManager.create(activity)
        val getCredOption = GetPublicKeyCredentialOption(requestJson)
        val getCredRequest = GetCredentialRequest(listOf(getCredOption))
        val getCredResponse = CoroutineScope(Dispatchers.IO).async {
            // Show the user Credential Manager with option to login with a Passkey
            return@async credentialManager.getCredential(
                getCredRequest,
                activity
            )
        }
        return getCredResponse.await()
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
        return PassageAuthResult(
            authToken = otpAuthResult.authToken,
            redirectUrl = otpAuthResult.redirectUrl,
            refreshToken = otpAuthResult.refreshToken,
            refreshTokenExpiration = otpAuthResult.refreshTokenExpiration
        )
    }

    // endregion
}
