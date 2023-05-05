@file:Suppress("unused", "RedundantVisibilityModifier", "RedundantModalityModifier")

package id.passage.android.exceptions

import android.util.Log
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import id.passage.android.Passage401Code
import id.passage.android.Passage403Code
import id.passage.android.model.Model401Code
import id.passage.android.model.Model401Error
import id.passage.android.model.Model403Code
import id.passage.client.infrastructure.ClientError
import id.passage.client.infrastructure.ClientException
import id.passage.client.infrastructure.ServerError
import id.passage.client.infrastructure.ServerException

public open class PassageClientException(message: String): RuntimeException(message) {

}

public class Passage401Exception(
    message: String,
    val code: Passage401Code
): PassageClientException(message)

public class Passage403Exception(
    message: String,
    val code: Passage403Code
): PassageClientException(message)

public open class PassageException(message: String): RuntimeException(message) {
    companion object {

        fun getErrorMessage(e: ClientException): String {
            val errorBody = (e.response as? ClientError<*>)?.body?.toString()
            return errorBody?.let {
                PassageErrorBody.getMessageString(it)
            } ?: ""
        }

        fun getErrorMessage(e: ServerException): String {
            val errorBody = (e.response as? ServerError<*>)?.body?.toString()
            return errorBody?.let {
                PassageErrorBody.getMessageString(it)
            } ?: ""
        }

        fun checkException(e: Exception): Exception {
            return when (e) {
                is TempPassageClientException -> {
                    val errorBody = (e.response as? ClientError<*>)?.body?.toString()
                    errorBody?.let {
                        val message = PassageErrorBody.getMessageString(it)
                        return TempPassageClientException(message, e.statusCode, e.response)
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

//interface CombinedEnumValue {
//    val code: String
////    val error: String
//}
//
//enum class CombinedEnum(private val combinedValue: CombinedEnumValue) : CombinedEnumValue by combinedValue {
//    discoverableLoginFailed(CombinedEnumValue(code: Model401Code.discoverableLoginFailed.toString())),
//    ENUM1_VALUE2(Enum1.VALUE2),
//    ENUM2_VALUE3(Enum2.VALUE3),
//    ENUM2_VALUE4(Enum2.VALUE4);
//}
//class TempClientException(
//    status: Int, // ex: 401
//    code: TempCode, // ex: userNotActive
//    message: String // ex: "This is not an active user"
//):  RuntimeException(message)

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
internal final class PassageErrorBody(val status: String?, val error: String?, val code: String?) {
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

public typealias PassageServerException = ServerException

public typealias TempPassageClientException = ClientException





