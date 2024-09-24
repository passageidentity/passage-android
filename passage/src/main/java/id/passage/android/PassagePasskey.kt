package id.passage.android

import android.app.Activity
import id.passage.android.api.LoginAPI
import id.passage.android.api.RegisterAPI
import id.passage.android.exceptions.LoginWithPasskeyException
import id.passage.android.exceptions.RegisterWithPasskeyException
import id.passage.android.model.AuthResult
import id.passage.android.model.AuthenticatorAttachment
import id.passage.android.model.LoginWebAuthnFinishRequest
import id.passage.android.model.LoginWebAuthnStartRequest
import id.passage.android.model.RegisterWebAuthnFinishRequest
import id.passage.android.model.RegisterWebAuthnStartRequest
import id.passage.android.utils.PasskeyCreationOptions
import id.passage.android.utils.PasskeyUtils
import okhttp3.OkHttpClient

class PassagePasskey(
    private val passageClient: OkHttpClient,
    private val activity: Activity,
    private val tokenStore: PassageTokenStore,
) {
    /**
     * Register user with passkey
     *
     * Create a user, prompt the user to create a passkey, and register the user.
     * @param identifier valid email or E164 phone number
     * @param options optional configuration for passkey creation
     * @return PassageAuthResult
     * @throws RegisterWithPasskeyException
     */
    suspend fun register(
        identifier: String,
        options: PasskeyCreationOptions? = null,
    ): AuthResult {
        try {
            val registerAPI = RegisterAPI(PassageClientService.basePath, passageClient)
            // Get Create Credential challenge from Passage
            val authenticatorAttachment =
                options?.authenticatorAttachment
                    ?: AuthenticatorAttachment.platform
            val webauthnStartRequest = RegisterWebAuthnStartRequest(identifier, authenticatorAttachment)
            val webauthnStartResponse = registerAPI.registerWebauthnStart(Passage.appId, webauthnStartRequest)
            // Use Create Credential challenge to prompt user to create a passkey
            val createCredOptionsJson = PasskeyUtils.getCreateCredentialOptionsJson(webauthnStartResponse.handshake)
            val createCredResponse = PasskeyUtils.createPasskey(createCredOptionsJson, activity)
            // Complete registration and authenticate the user
            val handshakeResponse = PasskeyUtils.getCreateCredentialHandshakeResponse(createCredResponse)
            val webauthnFinishRequest =
                RegisterWebAuthnFinishRequest(
                    handshakeId = webauthnStartResponse.handshake.id,
                    handshakeResponse = handshakeResponse,
                    userId = webauthnStartResponse.user?.id ?: "",
                )
            val authResponse = registerAPI.registerWebauthnFinish(Passage.appId, webauthnFinishRequest)
            // Handle and return auth result
            tokenStore.setTokens(authResponse.authResult)
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
     * @return PassageAuthResult
     * @throws LoginWithPasskeyException
     */
    suspend fun login(identifier: String? = null): AuthResult {
        try {
            val loginAPI = LoginAPI(PassageClientService.basePath, passageClient)
            // Get Credential challenge from Passage
            val webauthnStartRequest = LoginWebAuthnStartRequest(identifier)
            val webauthnStartResponse = loginAPI.loginWebauthnStart(Passage.appId, webauthnStartRequest)
            // Use Credential challenge to prompt user to login with a passkey
            val credOptionsJson = PasskeyUtils.getCredentialOptionsJson(webauthnStartResponse.handshake)
            val credResponse = PasskeyUtils.getPasskey(credOptionsJson, activity)
            // Complete login and authenticate the user
            val handshakeResponse = PasskeyUtils.getCredentialHandshakeResponse(credResponse)
            val webauthnFinishRequest =
                LoginWebAuthnFinishRequest(
                    handshakeId = webauthnStartResponse.handshake.id,
                    handshakeResponse = handshakeResponse,
                    userId = webauthnStartResponse.user?.id,
                )
            val authResponse = loginAPI.loginWebauthnFinish(Passage.appId, webauthnFinishRequest)
            // Handle and return auth result
            tokenStore.setTokens(authResponse.authResult)
            return authResponse.authResult
        } catch (e: Exception) {
            throw LoginWithPasskeyException.convert(e)
        }
    }
}
