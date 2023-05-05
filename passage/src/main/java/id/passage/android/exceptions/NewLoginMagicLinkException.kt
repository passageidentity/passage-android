@file:Suppress("RedundantVisibilityModifier")

package id.passage.android.exceptions

import id.passage.android.model.Model400Code
import id.passage.client.infrastructure.ClientException
import id.passage.client.infrastructure.ServerException

public open class NewLoginMagicLinkException(message: String): PassageException(message) {

    internal companion object {

        internal fun convert(e: Exception): NewLoginMagicLinkException {
            val message = e.message ?: e.toString()
            return when (e) {
                is ClientException -> convertClientException(e)
                is ServerException ->NewLoginMagicLinkServerException(message)
                else -> NewLoginMagicLinkException(message)
            }
        }

        private fun convertClientException(e: ClientException): NewLoginMagicLinkException {
            val error = parseClientException(e)
            val message = error?.error ?: ""
            return when (error?.code) {
                Model400Code.request.toString() -> NewLoginMagicLinkInvalidIdentifierException(message)
                else -> NewLoginMagicLinkException(message)
            }
        }

    }

}

public class NewLoginMagicLinkInvalidIdentifierException(message: String): NewLoginMagicLinkException(message)
public class NewLoginMagicLinkServerException(message: String): NewLoginMagicLinkException(message)
