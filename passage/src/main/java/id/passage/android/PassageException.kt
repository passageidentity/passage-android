package id.passage.android

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import id.passage.client.infrastructure.ClientError
import id.passage.client.infrastructure.ClientException
import id.passage.client.infrastructure.ServerError
import id.passage.client.infrastructure.ServerException

class PassageException(message: String): Exception(message) {
    companion object {
        fun checkException(e: Exception): Exception {
            return when (e) {
                is PassageClientException -> {
                    val errorBody = (e.response as? ClientError<*>)?.body?.toString()
                    errorBody?.let {
                        val message = PassageErrorBody.getMessageString(it)
                        return PassageClientException(message, e.statusCode, e.response)
                    }
                    e
                }
                is PassageServerException -> {
                    val errorBody = (e.response as? ServerError<*>)?.body?.toString()
                    errorBody?.let {
                        val message = PassageErrorBody.getMessageString(it)
                        return PassageServerException(message, e.statusCode, e.response)
                    }
                    e
                }
                else -> {
                    e
                }
            }
        }
    }

}

class PassageWebAuthnException(message: String): Exception(message) {
    companion object {
        const val CHALLENGE_MISSING = "WebAuthn credential assertion challenge missing."
        const val PARSING_FAILED = "WebAuthn credential assertion challenge parsing failed."
    }
}

class PassageCredentialException(message: String): Exception(message) {
    companion object {
        const val PARSING_FAILED = "Credential Manager handshake response parsing failed."
    }
}

class PassageAppException(message: String): Exception(message) {
    companion object {
        const val NO_APP = "Could not get Passage App information."
        const val NO_PUBLIC_SIGNUP = "Public registration disabled for this Passage App."
    }
}

@JsonClass(generateAdapter = true)
class PassageErrorBody(val status: String?, val error: String?) {
    companion object {
        @OptIn(ExperimentalStdlibApi::class)
        fun getMessageString(errorBody: String): String? {
            val moshi = Moshi.Builder().build()
            val jsonAdapter = moshi.adapter<PassageErrorBody>()
            val error = jsonAdapter.fromJson(errorBody)
            return error?.error
        }
    }
}

typealias PassageServerException = ServerException

typealias PassageClientException = ClientException
