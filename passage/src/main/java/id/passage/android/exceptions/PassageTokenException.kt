@file:Suppress("RedundantVisibilityModifier")

package id.passage.android.exceptions

import id.passage.client.infrastructure.ClientException
import id.passage.client.infrastructure.ServerException

/**
 * Thrown when token requests fail.
 *
 * @see PassageTokenUnauthorizedException
 * @see PassageTokenServerException
 */
public open class PassageTokenException(message: String): PassageException(message) {

    internal companion object {

        internal fun convert(e: Exception): PassageTokenException {
            val message = e.message ?: e.toString()
            return when (e) {
                is ClientException -> convertClientException(e)
                is ServerException -> PassageTokenServerException(message)
                else -> PassageTokenException(message)
            }
        }

        private fun convertClientException(e: ClientException): PassageTokenException {
            val message = e.message ?: e.toString()
            return when (e.statusCode) {
                401 -> PassageTokenUnauthorizedException(message)
                else -> PassageTokenException(message)
            }
        }

    }

}

/**
 * Thrown when the request is not authorized, typically when the token has expired or not been set.
 */
public class PassageTokenUnauthorizedException(message: String): PassageTokenException(message)

/**
 * Thrown when Passage internal server error occurs.
 */
public class PassageTokenServerException(message: String): PassageTokenException(message)
