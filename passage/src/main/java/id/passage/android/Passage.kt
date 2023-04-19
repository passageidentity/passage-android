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
import id.passage.android.api.RegisterAPI
import id.passage.android.model.ApiloginWebAuthnFinishRequest
import id.passage.android.model.ApiloginWebAuthnStartRequest
import id.passage.android.model.ApiregisterWebAuthnFinishRequest
import id.passage.android.model.ApiregisterWebAuthnStartRequest
import id.passage.android.model.IdentityAuthResult
import id.passage.android.model.ProtocolCredentialAssertionResponseJsonAdapter
import id.passage.android.model.ProtocolCredentialCreationResponseJsonAdapter
import id.passage.android.model.ProtocolPublicKeyCredentialCreationOptionsJsonAdapter
import id.passage.android.model.ProtocolPublicKeyCredentialRequestOptionsJsonAdapter
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
        private fun getResourceFromContext(activity: Activity, resName: String): String {
            val stringRes = activity.resources.getIdentifier(resName, "string", activity.packageName)
            require(stringRes != 0) {
                String.format(
                    "The 'R.string.%s' value it's not defined in your project's resources file.",
                    resName
                )
            }
            return activity.getString(stringRes)
        }
    }

    val appId = getResourceFromContext(activity, "passage_app_id")

    suspend fun register(identifier: String) {
        // Check if we should use passkeys
        registerWithPasskey(identifier)
        // Handle fallback methods
    }

    // TODO: Error handling
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
        val createCredResponse = createPasskey(createCredOptionsJson) ?: return null
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

    // TODO: Error handling
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

    suspend fun login(identifier: String) {
        // Check if we should use passkeys
        loginWithPasskey(identifier)
        // Handle fallback methods
    }

    // TODO: Error handling
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

    // TODO: Error handling
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

}
