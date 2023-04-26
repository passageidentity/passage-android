package id.passage.android

import android.app.Activity
import android.content.SharedPreferences
import android.util.Log
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import id.passage.android.api.TokensAPI
import id.passage.android.model.ApirefreshAuthTokenRequest
import id.passage.client.infrastructure.ApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception


@Suppress("unused", "RedundantVisibilityModifier", "RedundantModalityModifier")
public final class PassageTokenStore(activity: Activity) {

    private companion object {
        private const val PASSAGE_SHARED_PREFERENCES = "PASSAGE_SHARED_PREFERENCES"
        private const val PASSAGE_AUTH_TOKEN = "PASSAGE_AUTH_TOKEN"
        private const val PASSAGE_REFRESH_TOKEN = "PASSAGE_REFRESH_TOKEN"

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
        private suspend fun refreshAuthToken(refreshToken: String): PassageAuthResult {
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
        private suspend fun revokeRefreshToken(refreshToken: String) {
            val api = TokensAPI(Passage.BASE_PATH)
            api.revokeRefreshToken(Passage.appId, refreshToken)
        }

    }

    private val masterKey: MasterKey = MasterKey.Builder(activity)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private var sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
        activity,
        PASSAGE_SHARED_PREFERENCES,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    public val authToken: String?
        get() = sharedPreferences.getString(PASSAGE_AUTH_TOKEN, null)

    public val refreshToken: String?
        get() = sharedPreferences.getString(PASSAGE_REFRESH_TOKEN, null)

    init {
        // If a refresh token exists, go ahead and refresh the auth token on initialization.
        refreshToken?.let {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val authResult = refreshAuthToken(it)
                    setTokens(authResult)
                } catch (e: Exception) {
                    Log.e(Passage.TAG, e.message ?: "Exception: $e")
                }
            }
        }
    }

    public fun setAuthToken(token: String?) {
        with (sharedPreferences.edit()) {
            putString(PASSAGE_AUTH_TOKEN, token)
            apply()
        }
        // Setting the ApiClient access token will authorize/un-authorize PassageUser instance methods.
        ApiClient.accessToken = token
    }

    public fun setRefreshToken(token: String?) {
        with (sharedPreferences.edit()) {
            putString(PASSAGE_REFRESH_TOKEN, token)
            apply()
        }
    }

    public fun setTokens(authResult: PassageAuthResult?) {
        authResult?.let {
            setAuthToken(it.authToken)
            setRefreshToken(it.refreshToken)
        } ?: CoroutineScope(Dispatchers.IO).launch {
            clearAndRevokeTokens()
        }
    }

    public suspend fun clearAndRevokeTokens() {
        refreshToken?.let {
            revokeRefreshToken(it)
        }
        setAuthToken(null)
        setRefreshToken(null)
    }

}
