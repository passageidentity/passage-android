package id.passage.android

import id.passage.android.api.TokensAPI
import id.passage.android.model.ApirefreshAuthTokenRequest
import java.lang.Exception

@Suppress("unused", "RedundantVisibilityModifier", "RedundantModalityModifier")
public final class PassageToken {

    public companion object {

        /**
         * Refresh Auth Token
         *
         * Creates and returns a new auth and refresh tokens
         * @param refreshToken Existing refresh token
         * @return PassageAuthResult
         * @throws PassageTokenException If the Passage API returns a null auth result
         * @throws PassageClientException If the Passage API returns a client error response
         * @throws PassageServerException If the Passage API returns a server error response
         * @throws PassageException If the request fails for another reason
         */
        public suspend fun refreshAuthToken(refreshToken: String): PassageAuthResult {
            val api = TokensAPI(Passage.BASE_PATH)
            val request = ApirefreshAuthTokenRequest(refreshToken)
            val authResult = try {
                api.refreshAuthToken(Passage.appId, request).authResult
                    ?: throw PassageTokenException(PassageTokenException.REFRESH_FAILED)
            } catch (e: Exception) {
                throw PassageException.checkException(e)
            }
            return authResult
        }

        /**
         * Revoke Refresh Token
         *
         * Creates and returns a new auth and refresh tokens
         * @param refreshToken Refresh token
         * @return void
         * @throws PassageClientException If the Passage API returns a client error response
         * @throws PassageServerException If the Passage API returns a server error response
         * @throws PassageException If the request fails for another reason
         */
        public suspend fun revokeRefreshToken(refreshToken: String) {
            val api = TokensAPI(Passage.BASE_PATH)
            api.revokeRefreshToken(Passage.appId, refreshToken)
        }

    }

}