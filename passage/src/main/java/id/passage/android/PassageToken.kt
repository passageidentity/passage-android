package id.passage.android

import android.util.Log
import id.passage.android.api.TokensAPI
import id.passage.android.model.ApirefreshAuthTokenRequest
import id.passage.android.exceptions.*
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
            println("Payload: $payload")
            Log.w(Passage.TAG, "Payload: $payload")
            val json = Json { ignoreUnknownKeys = true } // configure to ignore unknown keys
            val data = json.decodeFromString<AuthTokenPayload>(payload)
            val expirationTime = data.exp * 1000 // convert to milliseconds
            val currentTime = System.currentTimeMillis()
            return expirationTime > currentTime
        } catch (e: Exception) {
            return false
        }
    }

}
