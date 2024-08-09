@file:Suppress("RedundantVisibilityModifier")

package id.passage.android.exceptions

import id.passage.android.model.Model400Code
import id.passage.client.infrastructure.ClientException
import id.passage.client.infrastructure.ServerException

/**
 * Thrown when creating a new registration magic link fails.
 *
 * @see MagicLinkInvalidIdentifierRegisterException
 * @see MagicLinkServerRegisterException
 */
public open class MagicLinkRegisterException(
    message: String,
) : PassageException(message) {
    internal companion object {
        internal fun convert(e: Exception): MagicLinkRegisterException {
            val message = e.message ?: e.toString()
            return when (e) {
                is ClientException -> convertClientException(e)
                is ServerException -> MagicLinkServerRegisterException(message)
                else -> MagicLinkRegisterException(message)
            }
        }

        private fun convertClientException(e: ClientException): MagicLinkRegisterException {
            val error = parseClientException(e)
            val message = error?.error ?: ""
            return when (error?.code) {
                Model400Code.request.toString() -> MagicLinkInvalidIdentifierRegisterException(message)
                else -> MagicLinkRegisterException(message)
            }
        }
    }
}

/**
 * Thrown when the provided identifier is invalid.
 */
public class MagicLinkInvalidIdentifierRegisterException(
    message: String,
) : MagicLinkRegisterException(message)

/**
 * Thrown when Passage internal server error occurs.
 */
public class MagicLinkServerRegisterException(
    message: String,
) : MagicLinkRegisterException(message)
