package id.passage.passageflex

import android.app.Activity
import id.passage.android.passageflex.model.AuthenticatorAttachment

/**
 * The base class for utilizing Android's native passkey APIs and Passage Flex APIs together.
 */
public class PassageFlexPasskey(
    val appId: String,
    val activity: Activity
) {
    /**
     * Registers a new passkey.
     *
     * Prompts the user associated with the provided Passage `transactionId` to create and
     * register a new passkey for use with your app.
     *
     * @param transactionId The Passage transaction id provided by your app's server.
     * @param authenticatorAttachment (Optional) The type of authentication that will be used in
     * this WebAuthN flow request. Defaults to `AuthenticatorAttachment.platform`. Use
     * `AuthenticatorAttachment.cross-platform` for physical security key registration.
     * @return A single-use "nonce" from Passage server to be exchanged for an authentication
     * token on your app's server.
     * @throws RegisterException
     */
    public suspend fun register(
        transactionId: String,
        authenticatorAttachment: AuthenticatorAttachment = AuthenticatorAttachment.platform,
    ): String {
        return PassagePasskeyAuthentication.register(
            transactionId,
            activity,
            appId,
            authenticatorAttachment,
        )
    }

    /**
     * Authenticates with a passkey.
     *
     * Prompts the user to select a passkey for authentication for your app. If a Passage
     * `transactionId` is provided, this method will attempt to show only passkeys associated
     * with that user account.
     *
     * @param transactionId (Optional) The Passage transaction id provided by your app's server.
     * @return A single-use "nonce" from Passage server to be exchanged for an authentication
     * token on your app's server.
     * @throws AuthenticateException
     */
    public suspend fun authenticate(
        transactionId: String? = null,
    ): String {
        return PassagePasskeyAuthentication.authenticate(
            transactionId,
            activity,
            appId
        )
    }
}