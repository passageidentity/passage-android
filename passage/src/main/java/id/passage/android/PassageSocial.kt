package id.passage.android

import android.app.Activity
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import id.passage.android.model.OAuth2ConnectionType
import java.lang.Exception
import java.security.MessageDigest
import java.security.SecureRandom
import java.util.Base64

internal class PassageSocial {

    internal companion object {

        internal var verifier = ""
        private const val CODE_CHALLENGE_METHOD = "S256"
        private const val SECRET_STRING_LENGTH = 32

        internal suspend fun signInWithGoogle(clientId: String, activity: Activity): GoogleIdTokenCredential {
            val credentialManager = CredentialManager.create(activity)
            val credOption = GetSignInWithGoogleOption.Builder(clientId).build()
            val credRequest = GetCredentialRequest.Builder().addCredentialOption(credOption).build()
            val credential = credentialManager.getCredential(activity, credRequest).credential
            if (
                credential is CustomCredential &&
                credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
            ) {
                return GoogleIdTokenCredential.createFrom(credential.data)
            } else {
                throw Exception() // TODO: Custom exception
            }
        }

        internal fun openChromeTab(
            connection: OAuth2ConnectionType,
            authOrigin: String,
            activity: Activity,
            authUrl: String
        ) {
            val redirectURI = "https://${authOrigin}"
            val state = getRandomString()
            val randomString = getRandomString()
            verifier = randomString
            val codeChallenge = sha256Hash(randomString)
            val tempParams = """
                ?redirect_uri=${redirectURI}
                &state=${state}
                &code_challenge=${codeChallenge}
                &code_challenge_method=${CODE_CHALLENGE_METHOD}
                &connection_type=${connection.value}
            """.trimIndent().replace("\n", "")

            val url = "${authUrl}${tempParams}"
            val intent = CustomTabsIntent.Builder().build()
            intent.launchUrl(activity, Uri.parse(url))
            // TODO: How to dismiss the chrome tab?
        }

        private fun getRandomString(): String {
            val characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
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