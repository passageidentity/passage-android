package id.passage.passageflex

import android.app.Activity
import id.passage.android.passageflex.api.AuthenticateAPI
import id.passage.android.passageflex.api.RegisterAPI
import id.passage.android.passageflex.model.AuthenticateWebAuthnFinishWithTransactionRequest
import id.passage.android.passageflex.model.AuthenticateWebAuthnStartWithTransactionRequest
import id.passage.android.passageflex.model.AuthenticatorAttachment
import id.passage.android.passageflex.model.RegisterWebAuthnFinishWithTransactionRequest
import id.passage.android.passageflex.model.RegisterWebAuthnStartWithTransactionRequest

/**
 *  Use `PassageFlex` to easily add passkeys to your existing authentication system on Apple devices.
 *
 *  Find out more at: https://passage.1password.com
 */
public object PassageFlex {
    /**
     * The base struct for utilizing Apple's native passkey APIs and Passage Flex APIs together.
     */
    public object Passkey {
        /**
         * Registers a new passkey.
         *
         * Prompts the user associated with the provided Passage `transactionId` to create and
         * register a new passkey for use with your app.
         *
         * @param transactionId The Passage transaction id provided by your app's server.
         * @param activity Activity to surface the Credentials Manager prompt within.
         * @param authenticatorAttachment (Optional) The type of authentication that will be used in
         * this WebAuthN flow request. Defaults to `AuthenticatorAttachment.platform`. Use
         * `AuthenticatorAttachment.cross-platform` for physical security key registration.
         * @return A single-use "nonce" from Passage server to be exchanged for an authentication
         * token on your app's server.
         * @throws RegisterException
         */
        public suspend fun register(
            transactionId: String,
            activity: Activity,
            authenticatorAttachment: AuthenticatorAttachment = AuthenticatorAttachment.platform,
        ): String {
            try {
                // Get Passage App ID from developer's `strings.xml` resource.
                val appId = Utils.getAppId(activity)
                // Request a Registration Start Handshake from Passage server
                val registerAPI = RegisterAPI()
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

        /**
         * Authenticates with a passkey.
         *
         * Prompts the user to select a passkey for authentication for your app. If a Passage
         * `transactionId` is provided, this method will attempt to show only passkeys associated
         * with that user account.
         *
         * @param transactionId (Optional) The Passage transaction id provided by your app's server.
         * @param activity Activity to surface the Credentials Manager prompt within.
         * @return A single-use "nonce" from Passage server to be exchanged for an authentication
         * token on your app's server.
         * @throws AuthenticateException
         */
        public suspend fun authenticate(
            transactionId: String? = null,
            activity: Activity,
        ): String {
            try {
                // Get Passage App ID from developer's `strings.xml` resource.
                val appId = Utils.getAppId(activity)
                // Request an Assertion Start Handshake from Passage server
                val authenticateAPI = AuthenticateAPI()
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
}
