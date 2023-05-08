package id.passage.android

import id.passage.android.api.TokensAPI
import id.passage.android.model.ApirefreshAuthTokenRequest
import id.passage.android.exceptions.*

@Suppress("unused", "RedundantVisibilityModifier", "RedundantModalityModifier")
public final class PassageToken {

    public companion object {

        /**
         * Refresh Auth Token
         *
         * Creates and returns a new auth and refresh tokens
         * @param refreshToken Existing refresh token
         * @return PassageAuthResult
         * @throws PassageTokenException
         */
        public suspend fun refreshAuthToken(refreshToken: String): PassageAuthResult {
            val api = TokensAPI(Passage.BASE_PATH)
            val request = ApirefreshAuthTokenRequest(refreshToken)
            val apiAuthResult = try {
                api.refreshAuthToken(Passage.appId, request).authResult
                    ?: throw PassageTokenException("Token refresh failed.")
            } catch (e: Exception) {
                throw PassageTokenException.convert(e)
            }
            // TODO: Once BE issue is fixed, we won't need to transform data model
            val authResult = PassageAuthResult(
                authToken = apiAuthResult.authToken,
                redirectUrl = apiAuthResult.redirectUrl,
                refreshToken = apiAuthResult.refreshToken,
                refreshTokenExpiration = apiAuthResult.refreshTokenExpiration
            )
            return authResult
        }

        /**
         * Revoke Refresh Token
         *
         * Creates and returns a new auth and refresh tokens
         * @param refreshToken Refresh token
         * @return void
         * @throws PassageTokenException
         */
        public suspend fun revokeRefreshToken(refreshToken: String) {
            val api = TokensAPI(Passage.BASE_PATH)
            try {
                api.revokeRefreshToken(Passage.appId, refreshToken)
            } catch (e: Exception) {
                throw PassageTokenException.convert(e)
            }
        }

    }

}
