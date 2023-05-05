@file:Suppress("RedundantVisibilityModifier")

package id.passage.android.exceptions

import id.passage.android.model.Model400Code
import id.passage.client.infrastructure.ClientException
import id.passage.client.infrastructure.ServerException

public open class NewRegisterMagicLinkException(message: String): PassageException(message) {

    internal companion object {

        internal fun convert(e: Exception): NewRegisterMagicLinkException {
            val message = e.message ?: e.toString()
            return when (e) {
                is ClientException -> convertClientException(e)
                is ServerException -> NewRegisterMagicLinkServerException(message)
                else -> NewRegisterMagicLinkException(message)
            }
        }

        private fun convertClientException(e: ClientException): NewRegisterMagicLinkException {
            val error = parseClientException(e)
            val message = error?.error ?: ""
            return when (error?.code) {
                Model400Code.request.toString() -> NewRegisterMagicLinkInvalidIdentifierException(message)
                else -> NewRegisterMagicLinkException(message)
            }
        }

    }

}

public class NewRegisterMagicLinkInvalidIdentifierException(message: String): NewRegisterMagicLinkException(message)
public class NewRegisterMagicLinkServerException(message: String): NewRegisterMagicLinkException(message)
