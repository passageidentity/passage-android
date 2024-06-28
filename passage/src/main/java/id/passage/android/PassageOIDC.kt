package id.passage.android

import android.app.Activity
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
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
import java.security.MessageDigest
import java.security.SecureRandom
import java.util.Base64

internal class PassageOIDC {
    internal companion object {
        internal var verifier = ""
        private const val CODE_CHALLENGE_METHOD = "S256"
        private const val SECRET_STRING_LENGTH = 32

        internal fun openChromeTab(
            appId:  String,
            activity: Activity,
            authUrl: String,
        ) {
            val redirectUri = "${Passage.BASE_PATH_OIDC}/android/${Passage.Package_NAME}/callback"
            val state = getRandomString()
            val randomString = getRandomString()
            verifier = randomString
            val codeChallenge = sha256Hash(randomString)
            val newParams = listOf(
                "client_id" to appId,
                "redirect_uri" to redirectUri,
                "state" to state,
                "code_challenge" to codeChallenge,
                "code_challenge_method" to CODE_CHALLENGE_METHOD,
                "scope" to "openid",
                "response_type" to "code",
            ).joinToString("&") {
                    (key, value) ->
                "$key=${URLEncoder.encode(value, "UTF-8")}"
            }
            val url = "${authUrl}?${newParams}"
            val intent = CustomTabsIntent.Builder().build()
            intent.launchUrl(activity, Uri.parse(url))
        }

        private fun getRandomString(): String {
            val digits = '0'..'9'
            val upperCaseLetters = 'A'..'Z'
            val lowerCaseLetters = 'a'..'z'
            val characters =
                (digits + upperCaseLetters + lowerCaseLetters)
                    .joinToString("")
            val random = SecureRandom()
            val stringBuilder = StringBuilder(SECRET_STRING_LENGTH)
            for (i in 0 until SECRET_STRING_LENGTH) {
                val randomIndex = random.nextInt(characters.length)
                stringBuilder.append(characters[randomIndex])
            }
            return stringBuilder.toString()
        }

        private fun sha256Hash(randomString: String): String {
            val bytes = randomString.toByteArray()
            val md = MessageDigest.getInstance("SHA-256")
            val digest = md.digest(bytes)
            return Base64.getUrlEncoder().withoutPadding().encodeToString(digest)
        }


        internal suspend fun finishOIDC(code: String) : AuthResult? {
            val redirectUri = "${Passage.BASE_PATH_OIDC}/android/${Passage.Package_NAME}/callback"
            var authResult : AuthResult?
            val client = OkHttpClient()
            val moshi = Moshi.Builder()
                .build()
            val jsonAdapter = moshi.adapter(OIDCResponse::class.java)
            val mediaType = "application/json; charset=utf-8".toMediaType()
            val requestBody = "{\"code\":\"$code\"}".toRequestBody(mediaType)

            val params = listOf(
                "grant_type" to "authorization_code",
                "code" to code,
                "client_id" to Passage.appId,
                "verifier" to verifier,
                "client_secret" to Passage.clientID,
                "redirect_uri" to redirectUri
            ).joinToString("&") { (key, value) ->
                "$key=${URLEncoder.encode(value, "UTF-8")}"
            }

            val url = "${Passage.BASE_PATH_OIDC}/token?$params"
            val request = Request.Builder()
                .url(url)
                .post(requestBody)
                .build()

            withContext(Dispatchers.IO) {
                client.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) {
                        if (response.code == 500)
                            throw ServerException("Server error : ${response.code} ${response.message}", response.code)
                        throw ClientException("Client error : ${response.code} ${response.message}", response.code)
                    }
                    val responseBody = response.body?.string()
                    if (responseBody != null) {
                        val apiResponse = jsonAdapter.fromJson(responseBody)!!
                        authResult = AuthResult(
                            authToken = apiResponse.access_token,
                            redirectUrl = "",
                            refreshToken = apiResponse.refresh_token,
                            refreshTokenExpiration = null
                        )

                    }
                    else
                        throw Exception("Response body is null : ${response.code} ${response.message}")
                }
            }
            return authResult
        }
    }
}

@JsonClass(generateAdapter = true)
data class OIDCResponse(val access_token: String, val refresh_token: String?)
