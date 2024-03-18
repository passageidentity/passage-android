package id.passage.android

import android.app.Activity
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import id.passage.android.model.OAuth2ConnectionType
import java.net.URLEncoder
import java.security.MessageDigest
import java.security.SecureRandom
import java.util.Base64

internal class PassageSocial {
    internal companion object {
        internal var verifier = ""
        private const val CODE_CHALLENGE_METHOD = "S256"
        private const val SECRET_STRING_LENGTH = 32

        internal fun openChromeTab(
            connection: OAuth2ConnectionType,
            authOrigin: String,
            activity: Activity,
            authUrl: String,
        ) {
            val redirectURI = "https://$authOrigin"
            val state = getRandomString()
            val randomString = getRandomString()
            verifier = randomString
            val codeChallenge = sha256Hash(randomString)
            val params =
                listOf(
                    "redirect_uri" to redirectURI,
                    "state" to state,
                    "code_challenge" to codeChallenge,
                    "code_challenge_method" to CODE_CHALLENGE_METHOD,
                    "connection_type" to connection.value,
                ).joinToString("&") {
                        (key, value) ->
                    "$key=${URLEncoder.encode(value, "UTF-8")}"
                }
            val url = "$authUrl?$params"
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
    }
}
