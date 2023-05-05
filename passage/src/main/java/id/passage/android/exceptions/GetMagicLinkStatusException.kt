@file:Suppress("RedundantVisibilityModifier")

package id.passage.android.exceptions

import id.passage.android.model.Model400Code
import id.passage.android.model.Model404Code
import id.passage.client.infrastructure.ClientException
import id.passage.client.infrastructure.ServerException

/**
 * Thrown when there's an error getting the magic link status.
 *
 * @see GetMagicLinkStatusInvalidException
 * @see GetMagicLinkStatusNotFoundException
 * @see GetMagicLinkStatusServerException
 */
public open class GetMagicLinkStatusException(message: String): PassageException(message) {

    internal companion object {

        internal fun convert(e: Exception): GetMagicLinkStatusException {
            val message = e.message ?: e.toString()
            return when (e) {
                is ClientException -> convertClientException(e)
                is ServerException -> GetMagicLinkStatusServerException(message)
                else -> GetMagicLinkStatusException(message)
            }
        }

        private fun convertClientException(e: ClientException): GetMagicLinkStatusException {
            val error = parseClientException(e)
            val message = error?.error ?: ""
            return when (error?.code) {
                Model400Code.request.toString() -> {
                    GetMagicLinkStatusInvalidException(message)
                }
                Model404Code.magicLinkNotFound.toString() -> {
                    GetMagicLinkStatusNotFoundException(message)
                }
                else -> {
                    GetMagicLinkStatusException(message)
                }
            }
        }

    }

}

/**
 * Thrown when provided magic link id is invalid.
 */
public class GetMagicLinkStatusInvalidException(message: String): GetMagicLinkStatusException(message)

/**
 * Thrown when provided magic link id is not found.
 */
public class GetMagicLinkStatusNotFoundException(message: String): GetMagicLinkStatusException(message)

/**
 * Thrown when Passage internal server error occurs.
 */
public class GetMagicLinkStatusServerException(message: String): GetMagicLinkStatusException(message)
