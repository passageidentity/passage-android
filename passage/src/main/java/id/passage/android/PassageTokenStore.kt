package id.passage.android

import android.app.Activity
import android.content.SharedPreferences
import android.util.Log
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import id.passage.android.exceptions.PassageTokenException
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

    internal val refreshToken: String?
        get() = sharedPreferences.getString(PASSAGE_REFRESH_TOKEN, null)

    init {
        ApiClient.accessToken = authToken
    }

    public suspend fun getValidAuthToken(): String {
        val currentAuthToken = authToken ?: throw PassageTokenException("Auth token not found in store.")
        val tokenIsValid = PassageToken.isAuthTokenValid(currentAuthToken)
        if (tokenIsValid) {
            return currentAuthToken
        }
        val currentRefreshToken = refreshToken ?: throw PassageTokenException("Auth token invalid. Refresh token not found in store.")
        val authResult = PassageToken.refreshAuthToken(currentRefreshToken)
        setTokens(authResult)
        return authResult.authToken
    }

    private fun setAuthToken(token: String?) {
        with (sharedPreferences.edit()) {
            putString(PASSAGE_AUTH_TOKEN, token)
            apply()
        }
        // Setting the ApiClient access token will authorize/un-authorize PassageUser instance methods.
        ApiClient.accessToken = token
    }

    private fun setRefreshToken(token: String?) {
        with (sharedPreferences.edit()) {
            putString(PASSAGE_REFRESH_TOKEN, token)
            apply()
        }
    }

    internal fun setTokens(authResult: PassageAuthResult?) {
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
                PassageToken.revokeRefreshToken(it)
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
                val authResult = PassageToken.refreshAuthToken(it)
                setTokens(authResult)
            } catch (e: Exception) {
                Log.e(Passage.TAG, e.message ?: "Exception: $e")
            }
        } ?: run {
            ApiClient.accessToken = authToken
        }
    }

}
