package id.passage.passageflex

import android.app.Activity
import id.passage.android.passageflex.api.AuthenticateAPI
import id.passage.android.passageflex.api.RegisterAPI
import id.passage.android.passageflex.model.AuthenticateWebAuthnFinishWithTransactionRequest
import id.passage.android.passageflex.model.AuthenticateWebAuthnStartWithTransactionRequest
import id.passage.android.passageflex.model.AuthenticatorAttachment
import id.passage.android.passageflex.model.RegisterWebAuthnFinishWithTransactionRequest
import id.passage.android.passageflex.model.RegisterWebAuthnStartWithTransactionRequest

public object PassageFlex {

    public object Passkey {

        public suspend fun register(transactionId: String, activity: Activity, authenticatorAttachment: AuthenticatorAttachment? = null) : String {
            val appId = "" // TODO: Get from utility method
            val registerAPI = RegisterAPI()
            val startRequest = RegisterWebAuthnStartWithTransactionRequest(transactionId, authenticatorAttachment)
            val starResponse = registerAPI.registerWebauthnStartWithTransaction(appId, startRequest)
            val createCredOptionsJson = PasskeyUtils.getCreateCredentialOptionsJson(starResponse.handshake)
            val createCredResponse = PasskeyUtils.createPasskey(createCredOptionsJson, activity)
            // Complete registration and authenticate the user
            val handshakeResponse = PasskeyUtils.getCreateCredentialHandshakeResponse(createCredResponse)
            val finishRequest = RegisterWebAuthnFinishWithTransactionRequest(
                handshakeId = starResponse.handshake.id,
                handshakeResponse,
                transactionId
            )
            val finishResponse = registerAPI.registerWebauthnFinishWithTransaction(appId, finishRequest)
            return finishResponse.nonce
        }

        public suspend fun authenticate(transactionId: String? = null, activity: Activity): String {
            val appId = "" // TODO: Get from utility method
            val authenticateAPI = AuthenticateAPI()
            val startRequest = AuthenticateWebAuthnStartWithTransactionRequest(transactionId)
            val startResponse = authenticateAPI.authenticateWebauthnStartWithTransaction(appId, startRequest)
            val credOptionsJson = PasskeyUtils.getCredentialOptionsJson(startResponse.handshake)
            val credResponse = PasskeyUtils.getPasskey(credOptionsJson, activity)
            // Complete login and authenticate the user
            val handshakeResponse = PasskeyUtils.getCredentialHandshakeResponse(credResponse)
            val finishRequest = AuthenticateWebAuthnFinishWithTransactionRequest(
                handshakeId = startResponse.handshake.id,
                handshakeResponse,
                transactionId
            )
            val finishResponse = authenticateAPI.authenticateWebauthnFinishWithTransaction(appId, finishRequest)
            return finishResponse.nonce
        }

        public suspend fun autoFill(activity: Activity) {
            val appId = "" // TODO: Get from utility method
        }

    }

}