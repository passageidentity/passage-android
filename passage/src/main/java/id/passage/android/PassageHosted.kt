package id.passage.android

import android.app.Activity
import id.passage.android.exceptions.HostedAuthorizationError
import id.passage.android.model.AuthResult
import id.passage.android.utils.HostedUtils

class PassageHosted(
    private val activity: Activity,
    private val tokenStore: PassageTokenStore,
) {
    /**
     * Authentication Method for Hosted Apps
     *
     * If your Passage app is Hosted, use this method to register and log in your user.
     * This method will open up a Passage login experience on a Chrome tab.
     */
    fun hostedAuthStart() {
        HostedUtils.openChromeTab(
            activity,
        )
    }

    /**
     * Finish Hosted Auth for Hosted Apps
     *
     * This method completes the hosted authentication process by exchanging the provided authorization code for Passage tokens.
     *
     * @param code The code returned from app link redirect to your activity.
     * @param state The state returned from app link redirect to your activity.
     * @throws HostedAuthorizationError
     */

    suspend fun hostedAuthFinish(
        code: String,
        state: String,
    ): Pair<AuthResult, String> {
        try {
            val finishHostedAuthResult = HostedUtils.finishHostedAuth(code, state)
            finishHostedAuthResult.let { (authResult, idToken) ->
                tokenStore.setTokens(authResult)
                tokenStore.setIdToken(idToken)
            }
            return finishHostedAuthResult
        } catch (e: Exception) {
            throw HostedAuthorizationError.convert(e)
        }
    }
}
