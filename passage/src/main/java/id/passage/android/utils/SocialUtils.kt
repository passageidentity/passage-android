package id.passage.android.utils

import android.app.Activity
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import id.passage.android.model.OAuth2ConnectionType
import java.net.URLEncoder

class SocialUtils {
    internal companion object {
        internal var verifier = ""
        private const val CODE_CHALLENGE_METHOD = "S256"

        internal fun openChromeTab(
            connection: OAuth2ConnectionType,
            authOrigin: String,
            activity: Activity,
            authUrl: String,
        ) {
            val redirectURI = "https://$authOrigin"
            val state = StringUtils.getRandomString()
            val randomString = StringUtils.getRandomString()
            verifier = randomString
            val codeChallenge = StringUtils.sha256Hash(randomString)
            val params =
                listOf(
                    "redirect_uri" to redirectURI,
                    "state" to state,
                    "code_challenge" to codeChallenge,
                    "code_challenge_method" to CODE_CHALLENGE_METHOD,
                    "connection_type" to connection.value,
                ).joinToString("&") { (key, value) ->
                    "$key=${URLEncoder.encode(value, "UTF-8")}"
                }
            val url = "$authUrl?$params"
            val intent = CustomTabsIntent.Builder().build()
            intent.launchUrl(activity, Uri.parse(url))
        }
    }
}
