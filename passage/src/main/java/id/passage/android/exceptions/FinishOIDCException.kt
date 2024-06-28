@file:Suppress("RedundantVisibilityModifier")

package id.passage.android.exceptions

import id.passage.client.infrastructure.ClientException
import id.passage.client.infrastructure.ServerException

/**
 * Thrown when there's an error finishing OIDC login/sign up
 *
 * @see FinishOIDCException
 */
public open class FinishOIDCException(
    message: String,
) : PassageException(message) {
    // Class body
    internal companion object {
        internal fun convert(e: Exception): FinishOIDCException {
            val message = e.message ?: e.toString()
            return when (e) {
                is ClientException -> convertClientException(e)
                is ServerException -> FinishOIDCServerException(message)
                else -> FinishOIDCException(message)
            }
        }

        private fun convertClientException(e: ClientException): FinishOIDCException =
            when (e.statusCode.toString()) {
                "400" -> {
                    FinishOIDCBadRequestException(e.message.toString())
                }
                else -> {
                    FinishOIDCException(e.message.toString())
                }
            }
    }
}

/**
 * Thrown when server returns bad request due to the invalid info.
 */
public class FinishOIDCBadRequestException(
    message: String,
) : FinishOIDCException(message)

/**
 * Thrown when Passage internal server error occurs.
 */
public class FinishOIDCServerException(
    message: String,
) : FinishOIDCException(message)
