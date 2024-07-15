package id.passage.android

import android.app.Activity
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import id.passage.android.exceptions.HostedAuthorizationError
import id.passage.android.model.AuthResult
import id.passage.client.infrastructure.ClientException
import id.passage.client.infrastructure.ServerException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.net.URLEncoder

internal class PassageHosted {
    internal companion object {
        private var verifier = ""
        private var state = ""
        private const val CODE_CHALLENGE_METHOD = "S256"
        private val basePathOIDC = "https://${Passage.authOrigin}"
        private val appId = Passage.appId
        private val packageName = Passage.packageName

        internal fun openChromeTab(activity: Activity) {
            val redirectUri = "$basePathOIDC/android/$packageName/callback"
            state = Utils.getRandomString()
            val randomString = Utils.getRandomString()
            verifier = Utils.getRandomString()
            val codeChallenge = Utils.sha256Hash(randomString)
            val newParams =
                listOf(
                    "client_id" to appId,
                    "redirect_uri" to redirectUri,
                    "state" to state,
                    "code_challenge" to codeChallenge,
                    "code_challenge_method" to CODE_CHALLENGE_METHOD,
                    "scope" to "openid",
                    "response_type" to "code",
                ).joinToString("&") { (key, value) ->
                    "$key=${URLEncoder.encode(value, "UTF-8")}"
                }
            val url = "$basePathOIDC/authorize?$newParams"
            val intent = CustomTabsIntent.Builder().build()
            intent.launchUrl(activity, Uri.parse(url))
        }

        internal suspend fun finishHostedAuth(
            code: String,
            clientSecret: String,
            state: String,
        ): Pair<AuthResult, String> {
            val redirectUri = "$basePathOIDC/android/$packageName/callback"
            if (PassageHosted.state != state) {
                throw HostedAuthorizationError("State is Invalid")
            }
            var authResult: AuthResult
            var idToken: String
            val client = OkHttpClient()
            val moshi =
                Moshi
                    .Builder()
                    .build()
            val jsonAdapter = moshi.adapter(OIDCResponse::class.java)
            val mediaType = "application/json; charset=utf-8".toMediaType()
            val requestBody = "{\"code\":\"$code\"}".toRequestBody(mediaType)

            val params =
                listOf(
                    "grant_type" to "authorization_code",
                    "code" to code,
                    "client_id" to Passage.appId,
                    "verifier" to verifier,
                    "client_secret" to clientSecret,
                    "redirect_uri" to redirectUri,
                ).joinToString("&") { (key, value) ->
                    "$key=${URLEncoder.encode(value, "UTF-8")}"
                }

            val url = "$basePathOIDC/token?$params"
            val request =
                Request
                    .Builder()
                    .url(url)
                    .post(requestBody)
                    .build()

            withContext(Dispatchers.IO) {
                client.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) {
                        if (response.code == 500) {
                            throw ServerException("Server error : ${response.code} ${response.message}", response.code)
                        }
                        throw ClientException("Client error : ${response.code} ${response.message}", response.code)
                    }
                    val responseBody = response.body?.string()
                    if (responseBody != null) {
                        val apiResponse = jsonAdapter.fromJson(responseBody)!!
                        authResult =
                            AuthResult(
                                authToken = apiResponse.accessToken,
                                redirectUrl = "",
                                refreshToken = apiResponse.refreshToken,
                                refreshTokenExpiration = null,
                            )
                        idToken = apiResponse.idToken
                    } else {
                        throw Exception("Response body is null : ${response.code} ${response.message}")
                    }
                }
            }
            return Pair(authResult, idToken)
        }

        fun logout(
            activity: Activity,
            idToken: String,
        ) {
            val redirectUri = "$basePathOIDC/android/$packageName/logout"
            verifier = Utils.getRandomString()
            val url =
                Uri
                    .parse("$basePathOIDC/logout")
                    .buildUpon()
                    .appendQueryParameter("id_token_hint", idToken)
                    .appendQueryParameter("client_id", appId)
                    .appendQueryParameter("post_logout_redirect_uri", redirectUri)
                    .appendQueryParameter("state", verifier)
                    .build()

            val customTabsIntent = CustomTabsIntent.Builder().build()
            customTabsIntent.launchUrl(activity, url)
        }
    }
}

@JsonClass(generateAdapter = true)
data class OIDCResponse(
    @Json(name = "access_token")
    val accessToken: String,
    @Json(name = "refresh_token")
    val refreshToken: String?,
    @Json(name = "id_token")
    val idToken: String,
)
