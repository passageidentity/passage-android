@file:Suppress("unused", "RedundantVisibilityModifier", "RedundantModalityModifier")

package id.passage.android.exceptions

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import id.passage.client.infrastructure.ClientError
import id.passage.client.infrastructure.ClientException


public open class PassageException(message: String): RuntimeException(message) {
    companion object {

        @OptIn(ExperimentalStdlibApi::class)
        fun parseClientException(e: ClientException): PassageClientError? {
            val errorBody = (e.response as? ClientError<*>)?.body?.toString() ?: return null
            val moshi = Moshi.Builder().build()
            val jsonAdapter = moshi.adapter<PassageClientError?>().lenient()
            return jsonAdapter.fromJson(errorBody)
        }
    }

}

public final class PassageWebAuthnException(message: String): PassageException(message) {
    companion object {
        const val CHALLENGE_MISSING = "WebAuthn credential assertion challenge missing."
        const val PARSING_FAILED = "WebAuthn credential assertion challenge parsing failed."
    }
}

public final class PassageCredentialException(message: String): PassageException(message) {
    companion object {
        const val PARSING_FAILED = "Credential Manager handshake response parsing failed."
    }
}

public final class PassageAppException(message: String): PassageException(message) {
    companion object {
        const val NO_APP = "Could not get Passage App information."
        const val NO_PUBLIC_SIGNUP = "Public registration disabled for this Passage App."
    }
}

public final class PassageTokenException(message: String): PassageException(message) {
    companion object {
        const val REFRESH_FAILED = "Auth token refresh failed."
    }
}

@JsonClass(generateAdapter = true)
final class PassageClientError(val status: String?, val error: String?, val code: String?)
