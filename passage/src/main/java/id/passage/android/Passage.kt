package id.passage.android

import android.app.Activity
import androidx.credentials.CreateCredentialResponse
import androidx.credentials.CreatePublicKeyCredentialRequest
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.GetPublicKeyCredentialOption
import com.squareup.moshi.Moshi
import id.passage.android.api.LoginAPI
import id.passage.android.api.MagicLinkAPI
import id.passage.android.api.OTPAPI
import id.passage.android.api.RegisterAPI
import id.passage.android.model.ActivateOneTimePasscodeRequest
import id.passage.android.model.ApiMagicLink
import id.passage.android.model.ApiactivateMagicLinkRequest
import id.passage.android.model.ApigetMagicLinkStatusRequest
import id.passage.android.model.ApiloginMagicLinkRequest
import id.passage.android.model.ApiloginWebAuthnFinishRequest
import id.passage.android.model.ApiloginWebAuthnStartRequest
import id.passage.android.model.ApiregisterMagicLinkRequest
import id.passage.android.model.ApiregisterWebAuthnFinishRequest
import id.passage.android.model.ApiregisterWebAuthnStartRequest
import id.passage.android.model.AuthResult
import id.passage.android.model.IdentityAuthResult
import id.passage.android.model.LoginOneTimePasscodeRequest
import id.passage.android.model.ProtocolCredentialAssertionResponseJsonAdapter
import id.passage.android.model.ProtocolCredentialCreationResponseJsonAdapter
import id.passage.android.model.ProtocolPublicKeyCredentialCreationOptionsJsonAdapter
import id.passage.android.model.ProtocolPublicKeyCredentialRequestOptionsJsonAdapter
import id.passage.android.model.RegisterOneTimePasscodeRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

@Suppress("UNUSED")
class Passage(val activity: Activity) {

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
    }

    // region INSTANCE VARIABLES

    private val appId = getRequiredResourceFromApp(activity, "passage_app_id")
    var language = getOptionalResourceFromApp(activity, "passage_language")

    // endregion

    // region SIMPLE AUTH METHODS
    suspend fun register(identifier: String) {
        // Check if we should use passkeys
        registerWithPasskey(identifier)
        // Handle fallback methods
    }

    suspend fun login(identifier: String) {
        // Check if we should use passkeys
        loginWithPasskey(identifier)
        // Handle fallback methods
    }

    // endregion

    // region PASSKEY AUTH METHODS

    suspend fun registerWithPasskey(identifier: String): IdentityAuthResult? {
        val registerAPI = RegisterAPI(BASE_PATH)
        // Get Create Credential challenge from Passage
        val webauthnStartRequest = ApiregisterWebAuthnStartRequest(identifier)
        val webauthnStartResponse = registerAPI.registerWebauthnStart(appId, webauthnStartRequest)
        val createCredOptions = webauthnStartResponse.handshake?.challenge?.publicKey ?: return null
        // Use Create Credential challenge to prompt user to create a passkey
        val moshi = Moshi.Builder().build()
        val createCredOptionsAdapter = ProtocolPublicKeyCredentialCreationOptionsJsonAdapter(moshi)
        val createCredOptionsJson = createCredOptionsAdapter.toJson(createCredOptions)
        val createCredResponse = createPasskey(createCredOptionsJson)
        // Complete registration and authenticate the user
        val handshakeResponseJson = createCredResponse.data.getString(REGISTRATION_RESPONSE_BUNDLE_KEY).toString()
        val handshakeResponseAdapter = ProtocolCredentialCreationResponseJsonAdapter(moshi)
        val handshakeResponse = handshakeResponseAdapter.fromJson(handshakeResponseJson)
        val webauthnFinishRequest = ApiregisterWebAuthnFinishRequest(
            handshakeId = webauthnStartResponse.handshake.id,
            handshakeResponse = handshakeResponse,
            userId = webauthnStartResponse.user?.id
        )
        val authResponse = registerAPI.registerWebauthnFinish(appId, webauthnFinishRequest)
        // Return auth result
        return authResponse.authResult
    }

    suspend fun createPasskey(optionsJson: String): CreateCredentialResponse {
        val credentialManager = CredentialManager.create(activity)
        val publicKeyCredRequest = CreatePublicKeyCredentialRequest(optionsJson)
        val publicKeyCredResponse = CoroutineScope(Dispatchers.IO).async {
            // Show the user Credential Manager with option to create a Passkey
            return@async credentialManager.createCredential(
                publicKeyCredRequest,
                activity
            )
        }
        return publicKeyCredResponse.await()
    }

    suspend fun loginWithPasskey(identifier: String): IdentityAuthResult? {
        val loginAPI = LoginAPI(BASE_PATH)
        val webauthnStartRequest = ApiloginWebAuthnStartRequest(identifier)
        val webauthnStartResponse = loginAPI.loginWebauthnStart(appId, webauthnStartRequest)
        val credOptions = webauthnStartResponse.handshake?.challenge?.publicKey ?: return null

        val moshi = Moshi.Builder().build()
        val credOptionsAdapter = ProtocolPublicKeyCredentialRequestOptionsJsonAdapter(moshi)
        val credOptionsJson = credOptionsAdapter.toJson(credOptions)
        val credResponse = getPasskey(credOptionsJson)

        val handshakeResponseJson = credResponse.credential.data.getString(AUTH_RESPONSE_BUNDLE_KEY).toString()
        val handshakeResponseAdapter = ProtocolCredentialAssertionResponseJsonAdapter(moshi)
        val handshakeResponse = handshakeResponseAdapter.fromJson(handshakeResponseJson)
        val webauthnFinishRequest = ApiloginWebAuthnFinishRequest(
            handshakeId = webauthnStartResponse.handshake.id,
            handshakeResponse = handshakeResponse,
            userId = webauthnStartResponse.user?.id
        )
        val authResponse = loginAPI.loginWebauthnFinish(appId, webauthnFinishRequest)

        return authResponse.authResult
    }

    suspend fun getPasskey(optionsJson: String): GetCredentialResponse {
        val credentialManager = CredentialManager.create(activity)
        val getCredOption = GetPublicKeyCredentialOption(optionsJson)
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
    suspend fun newRegisterMagicLink(identifier: String, magicLinkPath: String? = null): ApiMagicLink? {
        val registerAPI = RegisterAPI(BASE_PATH)
        val request = ApiregisterMagicLinkRequest(
            identifier = identifier,
            language = language,
            magicLinkPath = magicLinkPath
        )
        val response = registerAPI.registerMagicLink(appId, request)
        return response.magicLink
    }

    suspend fun newLoginMagicLink(identifier: String, magicLinkPath: String? = null): ApiMagicLink? {
        val loginAPI = LoginAPI(BASE_PATH)
        val request = ApiloginMagicLinkRequest(
            identifier = identifier,
            language = language,
            magicLinkPath = magicLinkPath
        )
        val response = loginAPI.loginMagicLink(appId, request)
        return response.magicLink
    }

    suspend fun magicLinkActivate(userMagicLink: String): IdentityAuthResult? {
        val magicLinkAPI = MagicLinkAPI(BASE_PATH)
        val request = ApiactivateMagicLinkRequest(userMagicLink)
        val response = magicLinkAPI.activateMagicLink(appId, request)
        return response.authResult
    }

    suspend fun getMagicLinkStatus(magicLinkId: String): IdentityAuthResult? {
        val magicLinkAPI = MagicLinkAPI(BASE_PATH)
        val request = ApigetMagicLinkStatusRequest(magicLinkId)
        val response = magicLinkAPI.magicLinkStatus(appId, request)
        return response.authResult
    }

    // endregion

    // region ONE TIME PASSCODE AUTH METHODS
    suspend fun newRegisterOneTimePasscode(identifier: String): String {
        val registerAPI = RegisterAPI(BASE_PATH)
        val request = RegisterOneTimePasscodeRequest(identifier, language)
        val response = registerAPI.registerOneTimePasscode(appId, request)
        return response.otpId
    }

    suspend fun newLoginOneTimePasscode(identifier: String): String {
        val loginAPI = LoginAPI(BASE_PATH)
        val request = LoginOneTimePasscodeRequest(identifier, language)
        val response = loginAPI.loginOneTimePasscode(appId, request)
        return response.otpId
    }

    suspend fun oneTimePasscodeActivate(otp: String, otpId: String): AuthResult {
        val otpAPI = OTPAPI(BASE_PATH)
        val request = ActivateOneTimePasscodeRequest(otp, otpId)
        val response = otpAPI.activateOneTimePasscode(appId, request)
        return response.authResult
    }

    // endregion
}
