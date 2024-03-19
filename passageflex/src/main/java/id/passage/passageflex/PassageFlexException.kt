@file:Suppress("RedundantVisibilityModifier", "RedundantModalityModifier")

package id.passage.passageflex

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import id.passage.passageflex.client.infrastructure.ClientError
import id.passage.passageflex.client.infrastructure.ClientException

public open class PassageFlexException(message: String) : RuntimeException(message) {
    companion object {
        @OptIn(ExperimentalStdlibApi::class)
        fun parseClientException(e: ClientException): PassageFlexClientError? {
            val errorBody = (e.response as? ClientError<*>)?.body?.toString() ?: return null
            if (errorBody.isEmpty()) return null
            val moshi = Moshi.Builder().build()
            val jsonAdapter = moshi.adapter<PassageFlexClientError?>().lenient() ?: return null
            return jsonAdapter.fromJson(errorBody)
        }
    }
}

@JsonClass(generateAdapter = true)
final class PassageFlexClientError(val status: String?, val error: String?, val code: String?)
