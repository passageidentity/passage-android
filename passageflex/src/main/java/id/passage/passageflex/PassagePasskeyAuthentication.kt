package id.passage.passageflex

import android.app.Activity
import id.passage.android.passageflex.api.AuthenticateAPI
import id.passage.android.passageflex.api.RegisterAPI
import id.passage.android.passageflex.model.AuthenticateWebAuthnFinishWithTransactionRequest
import id.passage.android.passageflex.model.AuthenticateWebAuthnStartWithTransactionRequest
import id.passage.android.passageflex.model.AuthenticatorAttachment
import id.passage.android.passageflex.model.RegisterWebAuthnFinishWithTransactionRequest
import id.passage.android.passageflex.model.RegisterWebAuthnStartWithTransactionRequest
import id.passage.passageflex.exceptions.AuthenticateException
import id.passage.passageflex.exceptions.RegisterException

object PassagePasskeyAuthentication {
    private const val BASE_PATH = "https://auth.passage.id/v1"

    internal suspend fun register(
        transactionId: String,
        activity: Activity,
        appId: String,
        authenticatorAttachment: AuthenticatorAttachment = AuthenticatorAttachment.platform,
        apiBasePath: String? = null,
    ): String {
        try {
            // Request a Registration Start Handshake from Passage server
            val registerAPI = RegisterAPI(apiBasePath ?: BASE_PATH)
            val startRequest =
                RegisterWebAuthnStartWithTransactionRequest(
                    transactionId,
                    authenticatorAttachment,
                )
            val startResponse =
                registerAPI
                    .registerWebauthnStartWithTransaction(
                        appId,
                        startRequest,
                    )
            // Use the Registration Start Handshake to prompt the app user to create a passkey
            val createCredOptionsJson =
                PasskeyUtils
                    .getCreateCredentialOptionsJson(startResponse.handshake)
            val createCredResponse = PasskeyUtils.createPasskey(createCredOptionsJson, activity)
            val handshakeResponse =
                PasskeyUtils
                    .getCreateCredentialHandshakeResponse(createCredResponse)
            // Send the new Credential Handshake Response to Passage server
            val finishRequest =
                RegisterWebAuthnFinishWithTransactionRequest(
                    handshakeId = startResponse.handshake.id,
                    handshakeResponse,
                    transactionId,
                )
            val finishResponse =
                registerAPI
                    .registerWebauthnFinishWithTransaction(
                        appId,
                        finishRequest,
                    )
            // If successful, Passage server will return a nonce.
            return finishResponse.nonce
        } catch (e: Exception) {
            throw RegisterException.convert(e)
        }
    }

    internal suspend fun authenticate(
        transactionId: String? = null,
        activity: Activity,
        appId: String,
        apiBasePath: String? = null,
    ): String {
        try {
            // Request an Assertion Start Handshake from Passage server
            val authenticateAPI = AuthenticateAPI(apiBasePath ?: BASE_PATH)
            val startRequest = AuthenticateWebAuthnStartWithTransactionRequest(transactionId)
            val startResponse =
                authenticateAPI
                    .authenticateWebauthnStartWithTransaction(
                        appId,
                        startRequest,
                    )
            // Use the Assertion Start Handshake to prompt the app user to select a passkey
            val credOptionsJson = PasskeyUtils.getCredentialOptionsJson(startResponse.handshake)
            val credResponse = PasskeyUtils.getPasskey(credOptionsJson, activity)
            val handshakeResponse = PasskeyUtils.getCredentialHandshakeResponse(credResponse)
            // Send the Credential Handshake Response to Passage server
            val finishRequest =
                AuthenticateWebAuthnFinishWithTransactionRequest(
                    handshakeId = startResponse.handshake.id,
                    handshakeResponse,
                    transactionId,
                )
            val finishResponse =
                authenticateAPI
                    .authenticateWebauthnFinishWithTransaction(
                        appId,
                        finishRequest,
                    )
            // If successful, Passage server will return a nonce.
            return finishResponse.nonce
        } catch (e: Exception) {
            throw AuthenticateException.convert(e)
        }
    }
}
