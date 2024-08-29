package id.passage.android

import android.app.Activity
import android.content.SharedPreferences
import android.util.Log
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import id.passage.android.api.TokensAPI
import id.passage.android.exceptions.PassageTokenException
import id.passage.android.model.AuthResult
import id.passage.android.model.RefreshAuthTokenRequest
import id.passage.client.infrastructure.ApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.lang.Exception
import java.util.Base64

class PassageTokenStore(
    activity: Activity,
) {
    private companion object {
        private const val PASSAGE_SHARED_PREFERENCES = "PASSAGE_SHARED_PREFERENCES"
        private const val PASSAGE_AUTH_TOKEN = "PASSAGE_AUTH_TOKEN"
        private const val PASSAGE_REFRESH_TOKEN = "PASSAGE_REFRESH_TOKEN"
        private const val PASSAGE_ID_TOKEN = "PASSAGE_ID_TOKEN"
    }

    private val masterKey: MasterKey =
        MasterKey
            .Builder(activity)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

    private var sharedPreferences: SharedPreferences =
        EncryptedSharedPreferences.create(
            activity,
            PASSAGE_SHARED_PREFERENCES,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
        )

    val authToken: String?
        get() = sharedPreferences.getString(PASSAGE_AUTH_TOKEN, null)

    internal val idToken: String?
        get() = sharedPreferences.getString(PASSAGE_ID_TOKEN, null)

    internal val refreshToken: String?
        get() = sharedPreferences.getString(PASSAGE_REFRESH_TOKEN, null)

    init {
        ApiClient.accessToken = authToken
    }

    /**
     * Retrieves a valid authentication token, refreshing it if necessary.
     *
     * Creates and returns a new auth token by refreshing it if the current one is invalid.
     * @throws PassageTokenException If the token could not be refreshed or if there is no refresh token saved in the keychain.
     * @return A valid authentication token.
     */
    suspend fun getValidAuthToken(): String {
        val currentAuthToken = authToken ?: throw PassageTokenException("Auth token not found in store.")
        val tokenIsValid = isAuthTokenValid(currentAuthToken)
        if (tokenIsValid) {
            return currentAuthToken
        }
        val currentRefreshToken = refreshToken ?: throw PassageTokenException("Auth token invalid. Refresh token not found in store.")
        val authResult = refreshAuthToken(currentRefreshToken)
        setTokens(authResult)
        return authResult.authToken
    }

    /**
     * Refresh Auth Token
     *
     * Creates and returns a new auth and refresh tokens
     * @param refreshToken Existing refresh token
     * @return PassageAuthResult
     * @throws PassageTokenException
     */
    suspend fun refreshAuthToken(refreshToken: String): AuthResult {
        val api = TokensAPI(Passage.BASE_PATH)
        val request = RefreshAuthTokenRequest(refreshToken)
        val apiAuthResult =
            try {
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
    suspend fun revokeRefreshToken(refreshToken: String) {
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
    fun isAuthTokenValid(token: String): Boolean {
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

    private fun setAuthToken(token: String?) {
        with(sharedPreferences.edit()) {
            putString(PASSAGE_AUTH_TOKEN, token)
            apply()
        }
        // Setting the ApiClient access token will authorize/un-authorize PassageUser instance methods.
        ApiClient.accessToken = token
    }

    private fun setRefreshToken(token: String?) {
        with(sharedPreferences.edit()) {
            putString(PASSAGE_REFRESH_TOKEN, token)
            apply()
        }
    }

    internal fun setIdToken(token: String?) {
        with(sharedPreferences.edit()) {
            putString(PASSAGE_ID_TOKEN, token)
            apply()
        }
    }

    internal fun setTokens(authResult: AuthResult?) {
        authResult?.let {
            setAuthToken(it.authToken)
            setRefreshToken(it.refreshToken)
        } ?: CoroutineScope(Dispatchers.IO).launch {
            clearAndRevokeTokens()
        }
    }

    internal suspend fun clearAndRevokeTokens() {
        refreshToken?.let {
            try {
                revokeRefreshToken(it)
            } catch (e: PassageTokenException) {
                Log.e(Passage.TAG, e.message ?: e.toString())
            }
        }
        setAuthToken(null)
        setRefreshToken(null)
    }

    internal suspend fun attemptRefreshTokenStore() {
        refreshToken?.let {
            try {
                val authResult = refreshAuthToken(it)
                setTokens(authResult)
            } catch (e: Exception) {
                Log.e(Passage.TAG, e.message ?: "Exception: $e")
            }
        } ?: run {
            ApiClient.accessToken = authToken
        }
    }
}

@Serializable
private data class AuthTokenPayload(
    val exp: Long,
)
