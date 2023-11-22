package id.passage.android

import id.passage.android.api.TokensAPI
import id.passage.android.exceptions.*
import id.passage.android.model.RefreshAuthTokenRequest
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.util.Base64

@Serializable
private data class AuthTokenPayload(val exp: Long)

@Suppress("unused", "RedundantVisibilityModifier")
public object PassageToken {

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
        val request = RefreshAuthTokenRequest(refreshToken)
        val apiAuthResult = try {
            api.refreshAuthToken(Passage.appId, request).authResult
        } catch (e: Exception) {
            throw PassageTokenException.convert(e)
        }
        return apiAuthResult
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

    /**
     * Is Auth Token Valid
     *
     * Decodes and checks expiration of a token.
     * @param token String
     * @return Boolean
     */
    public fun isAuthTokenValid(token: String): Boolean {
        try {
            val parts = token.split(".")
            if (parts.size != 3) {
                return false
            }
            val payload = String(Base64.getUrlDecoder().decode(parts[1]))
            val json = Json { ignoreUnknownKeys = true }
            val data = json.decodeFromString<AuthTokenPayload>(payload)
            val expirationTime = data.exp * 1000
            val currentTime = System.currentTimeMillis()
            return expirationTime > currentTime
        } catch (e: Exception) {
            return false
        }
    }

}
