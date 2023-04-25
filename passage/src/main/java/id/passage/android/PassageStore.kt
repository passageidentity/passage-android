package id.passage.android

import android.app.Activity
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import id.passage.client.infrastructure.ApiClient


@Suppress("UNUSED")
class PassageStore(activity: Activity) {

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

    val authToken: String?
        get() = sharedPreferences.getString(PASSAGE_AUTH_TOKEN, null)

    val refreshToken: String?
        get() = sharedPreferences.getString(PASSAGE_REFRESH_TOKEN, null)

    init {
        // TODO: Get a fresh auth token
    }

    fun setAuthToken(token: String?) {
        with (sharedPreferences.edit()) {
            putString(PASSAGE_AUTH_TOKEN, token)
            apply()
        }
        ApiClient.accessToken = token
    }

    fun setRefreshToken(token: String?) {
        with (sharedPreferences.edit()) {
            putString(PASSAGE_REFRESH_TOKEN, token)
            apply()
        }
    }

    fun setTokens(authResult: PassageAuthResult) {
        setAuthToken(authResult.authToken)
        setRefreshToken(authResult.refreshToken)
    }

}
