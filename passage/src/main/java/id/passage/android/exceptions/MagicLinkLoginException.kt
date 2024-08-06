@file:Suppress("RedundantVisibilityModifier")

package id.passage.android.exceptions

import id.passage.android.model.Model400Code
import id.passage.client.infrastructure.ClientException
import id.passage.client.infrastructure.ServerException

/**
 * Thrown when creating a new login magic link fails.
 *
 * @see MagicLinkInvalidIdentifierLoginException
 * @see MagicLinkServerLoginException
 */
public open class MagicLinkLoginException(
    message: String,
) : PassageException(message) {
    internal companion object {
        internal fun convert(e: Exception): MagicLinkLoginException {
            val message = e.message ?: e.toString()
            return when (e) {
                is ClientException -> convertClientException(e)
                is ServerException -> MagicLinkServerLoginException(message)
                else -> MagicLinkLoginException(message)
            }
        }

        private fun convertClientException(e: ClientException): MagicLinkLoginException {
            val error = parseClientException(e)
            val message = error?.error ?: ""
            return when (error?.code) {
                Model400Code.request.toString() -> MagicLinkInvalidIdentifierLoginException(message)
                else -> MagicLinkLoginException(message)
            }
        }
    }
}

/**
 * Thrown when the provided identifier is invalid.
 */
public class MagicLinkInvalidIdentifierLoginException(
    message: String,
) : MagicLinkLoginException(message)

/**
 * Thrown when Passage internal server error occurs.
 */
public class MagicLinkServerLoginException(
    message: String,
) : MagicLinkLoginException(message)
