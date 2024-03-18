@file:Suppress("RedundantVisibilityModifier", "RedundantModalityModifier")

package id.passage.android.exceptions

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import id.passage.client.infrastructure.ClientError
import id.passage.client.infrastructure.ClientException

public open class PassageException(message: String) : RuntimeException(message) {
    companion object {
        @OptIn(ExperimentalStdlibApi::class)
        fun parseClientException(e: ClientException): PassageClientError? {
            val errorBody = (e.response as? ClientError<*>)?.body?.toString() ?: return null
            if (errorBody.isEmpty()) return null
            val moshi = Moshi.Builder().build()
            val jsonAdapter = moshi.adapter<PassageClientError?>().lenient() ?: return null
            return jsonAdapter.fromJson(errorBody)
        }
    }
}

@JsonClass(generateAdapter = true)
final class PassageClientError(val status: String?, val error: String?, val code: String?)
