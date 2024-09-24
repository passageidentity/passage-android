package id.passage.android

import android.app.Activity
import id.passage.android.api.OAuth2API
import id.passage.android.exceptions.FinishSocialAuthenticationException
import id.passage.android.model.AuthResult
import id.passage.android.utils.SocialConnection
import id.passage.android.utils.SocialUtils
import okhttp3.OkHttpClient

class PassageSocial(
    private val passageClient: OkHttpClient,
    private val activity: Activity,
    private val tokenStore: PassageTokenStore,
) {
    /**
     * Authorize with Social Connection
     *
     * Authorizes user via a supported third-party social provider.
     * @param connection The Social connection to use for authorization
     */
    fun authorize(connection: SocialConnection) {
        SocialUtils.openChromeTab(
            connection,
            Passage.authOrigin,
            activity,
            authUrl = "${PassageClientService.basePath}/apps/${Passage.appId}/social/authorize",
        )
    }

    /**
     * Finish Social Authentication
     *
     * Finishes a social login by exchanging the social login provider code for Passage tokens.
     * @param code The code returned from the social login provider.
     * @return PassageAuthResult
     * @throws FinishSocialAuthenticationException
     */
    suspend fun finish(code: String): AuthResult {
        val oauthAPI = OAuth2API(PassageClientService.basePath, passageClient)
        val authResult =
            try {
                oauthAPI.exchangeSocialToken(Passage.appId, code, SocialUtils.verifier).authResult
            } catch (e: java.lang.Exception) {
                throw FinishSocialAuthenticationException.convert(e)
            }
        SocialUtils.verifier = ""
        tokenStore.setTokens(authResult)
        return authResult
    }
}
