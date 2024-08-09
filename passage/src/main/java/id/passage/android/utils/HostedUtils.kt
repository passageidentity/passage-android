package id.passage.android.utils

import android.app.Activity
import android.net.Uri
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import id.passage.android.Passage
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

internal class HostedUtils {
    internal companion object {
        private var verifier = ""
        private var state = ""
        private const val CODE_CHALLENGE_METHOD = "S256"
        private val basePathOIDC = "https://${Passage.authOrigin}"
        private val appId = Passage.appId
        private var packageName = ""

        private fun openCustomTab(
            activity: Activity,
            url: String,
        ) {
            WebViewUtils.openCustomTab(activity, url)
        }

        internal fun openChromeTab(activity: Activity) {
            packageName = activity.packageName
            val redirectUri = "$basePathOIDC/android/$packageName/callback"
            state = StringUtils.getRandomString()
            verifier = StringUtils.getRandomString()
            val codeChallenge = StringUtils.sha256Hash(verifier)
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
            openCustomTab(activity, url)
        }

        internal suspend fun finishHostedAuth(
            code: String,
            state: String,
        ): Pair<AuthResult, String> {
            val redirectUri = "$basePathOIDC/android/$packageName/callback"
            if (HostedUtils.state != state) {
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
                    "code_verifier" to verifier,
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
                            throw ServerException(
                                "Server error : ${response.code} ${response.message}",
                                response.code,
                            )
                        }
                        throw ClientException(
                            "Client error : ${response.code} ${response.message}",
                            response.code,
                        )
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
            packageName = activity.packageName
            val redirectUri = "$basePathOIDC/android/$packageName/logout"
            val state = StringUtils.getRandomString()
            val url =
                Uri
                    .parse("$basePathOIDC/logout")
                    .buildUpon()
                    .appendQueryParameter("id_token_hint", idToken)
                    .appendQueryParameter("client_id", appId)
                    .appendQueryParameter("post_logout_redirect_uri", redirectUri)
                    .appendQueryParameter("state", state)
                    .build()

            openCustomTab(activity, url.toString())
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
