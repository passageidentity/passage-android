package id.passage.android

import id.passage.android.api.LoginAPI
import id.passage.android.api.MagicLinkAPI
import id.passage.android.api.RegisterAPI
import id.passage.android.exceptions.GetMagicLinkStatusException
import id.passage.android.exceptions.MagicLinkActivateException
import id.passage.android.exceptions.MagicLinkLoginException
import id.passage.android.exceptions.MagicLinkRegisterException
import id.passage.android.model.ActivateMagicLinkRequest
import id.passage.android.model.AuthResult
import id.passage.android.model.GetMagicLinkStatusRequest
import id.passage.android.model.LoginMagicLinkRequest
import id.passage.android.model.MagicLink
import id.passage.android.model.RegisterMagicLinkRequest
import okhttp3.OkHttpClient

class PassageMagicLink(
    private val passageClient: OkHttpClient,
    private val tokenStore: PassageTokenStore,
) {
    /**
     * Register with Magic Link
     *
     * Create a user and send a registration email or SMS to the user. The user will receive an email or text with a link to complete their registration.
     * @param identifier valid email or E164 phone number
     * @param magicLinkPath path relative to the app's auth origin (optional)
     * @param language optional language string for localizing emails, if no language or an invalid language is provided the application default language will be used
     * @return MagicLink
     * @throws MagicLinkRegisterException
     */
    suspend fun register(
        identifier: String,
        language: String? = null,
    ): MagicLink {
        val registerAPI = RegisterAPI(Passage.BASE_PATH, passageClient)
        val request =
            RegisterMagicLinkRequest(
                identifier = identifier,
                language = language
            )
        val magicLink =
            try {
                registerAPI.registerMagicLink(Passage.appId, request).magicLink
            } catch (e: Exception) {
                throw MagicLinkRegisterException.convert(e)
            }
        return magicLink
    }

    /**
     * Log in with Magic Link
     *
     * Send a login email or SMS to the user. The user will receive an email or text with a link to complete their login.
     * @param identifier valid email or E164 phone number
     * @param magicLinkPath path relative to the app's auth_origin (optional)
     * @param language optional language string for localizing emails, if no language or an invalid language is provided the application default language will be used
     * @return MagicLink?
     * @throws MagicLinkLoginException
     */
    suspend fun login(
        identifier: String,
        language: String? = null,
    ): MagicLink {
        val loginAPI = LoginAPI(Passage.BASE_PATH, passageClient)
        val request =
            LoginMagicLinkRequest(
                identifier = identifier,
                language = language
            )
        val response =
            try {
                loginAPI.loginMagicLink(Passage.appId, request)
            } catch (e: Exception) {
                throw MagicLinkLoginException.convert(e)
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
     * @throws MagicLinkActivateException
     */
    suspend fun activate(magicLink: String): AuthResult {
        val magicLinkAPI = MagicLinkAPI(Passage.BASE_PATH, passageClient)
        val request = ActivateMagicLinkRequest(magicLink)
        val response =
            try {
                magicLinkAPI.activateMagicLink(Passage.appId, request)
            } catch (e: Exception) {
                throw MagicLinkActivateException.convert(e)
            }
        tokenStore.setTokens(response.authResult)
        return response.authResult
    }

    /**
     * Magic Link Status
     *
     * Check if a magic link has been activated yet or not. Once the magic link has been activated,
     * this endpoint will return an authentication token for the user. This endpoint can be used to
     * initiate a login in one device and then poll and wait for the login to complete on another
     * device.
     * @param id Magic Link ID
     * @return PassageAuthResult?
     * @throws GetMagicLinkStatusException
     */
    suspend fun status(id: String): AuthResult {
        val magicLinkAPI = MagicLinkAPI(Passage.BASE_PATH, passageClient)
        val request = GetMagicLinkStatusRequest(id)
        val response =
            try {
                magicLinkAPI.magicLinkStatus(Passage.appId, request)
            } catch (e: Exception) {
                throw GetMagicLinkStatusException.convert(e)
            }

        tokenStore.setTokens(response.authResult)
        return response.authResult
    }
}
