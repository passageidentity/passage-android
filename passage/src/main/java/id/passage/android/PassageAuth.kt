package id.passage.android

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.credentials.CreateCredentialResponse
import androidx.credentials.CreatePublicKeyCredentialRequest
import androidx.credentials.CredentialManager
import androidx.credentials.exceptions.CreateCredentialException
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import id.passage.android.api.RegisterAPI
import id.passage.android.api.LoginAPI
import id.passage.android.model.ApiloginMagicLinkRequest
import id.passage.android.model.ApiregisterMagicLinkRequest
import id.passage.android.model.ApiregisterWebAuthnFinishRequest
import id.passage.android.model.ApiregisterWebAuthnStartRequest
import id.passage.android.model.AuthResult
import id.passage.android.model.IdentityAuthResult
import id.passage.android.model.ProtocolCredentialCreationResponse
import id.passage.android.model.ProtocolCredentialCreationResponseJsonAdapter
import id.passage.android.model.ProtocolPublicKeyCredentialCreationOptions
import id.passage.android.model.ProtocolPublicKeyCredentialCreationOptionsJsonAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Suppress("unused")
class PassageAuth {

    companion object {
        private const val TAG = "PassageAuth"
        private const val APP_ID = "iz3anQSmjVguwpsUq6PhY9cs"
        private const val BASE_PATH = "https://auth-uat.passage.dev/v1"
        suspend fun login(identifier: String) {
            val login = LoginAPI()
            val request = ApiloginMagicLinkRequest(identifier)
            login.loginMagicLink(appId = "", user = request)
        }

        suspend fun registerMagicLinkTest(identifier: String): String {
            val register = RegisterAPI(basePath = "https://auth-uat.passage.dev/v1")
            val request = ApiregisterMagicLinkRequest(identifier)
            val response = register.registerMagicLink(APP_ID, request)
            val str = response.magicLink?.id ?: "NO WORKY :'("
            Log.e(TAG, str)
            return str
        }

        suspend fun register(identifier: String, activity: Activity): IdentityAuthResult? {
            val register = RegisterAPI(basePath = "https://auth-uat.passage.dev/v1")
            val startRequest = ApiregisterWebAuthnStartRequest(identifier)
            val response = register.registerWebauthnStart(APP_ID, startRequest)
            val credRequest = response.handshake?.challenge?.publicKey ?: return null
            Log.w(TAG, credRequest.toString())

            val moshi = Moshi.Builder().build()
            val adapter = ProtocolPublicKeyCredentialCreationOptionsJsonAdapter(moshi)

            val json: String = adapter.toJson(credRequest)
//
            Log.w(TAG, json)
            val handshakeResponseString = createPasskey(json, activity) ?: return null
            val handshakeAdapter = ProtocolCredentialCreationResponseJsonAdapter(moshi)
            val handshakeResponse = handshakeAdapter.fromJson(handshakeResponseString)
            val finishRequest = ApiregisterWebAuthnFinishRequest(
                handshakeId = response.handshake.id,
                handshakeResponse = handshakeResponse,
                userId = response.user?.id
            )
            val authResponse = register.registerWebauthnFinish(APP_ID, finishRequest)
            return authResponse.authResult
        }

        suspend fun createPasskey(request: String, activity: Activity): String? {
            val pubKeyRequest = CreatePublicKeyCredentialRequest(request)
            val credentialManager = CredentialManager.create(activity)
            var result: String? = null
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = credentialManager.createCredential(
                        request = pubKeyRequest,
                        activity = activity
                    )
                    result = response.data.toString()
//                handlePasskeyRegistrationResult(result)
                } catch (e: CreateCredentialException) {
                    Log.e(TAG, e.toString())
//                    handleFailure(e)
                }
            }
            return result
        }

        suspend fun webauthnStart() {

        }

    }

}
