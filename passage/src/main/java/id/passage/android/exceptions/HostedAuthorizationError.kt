@file:Suppress("RedundantVisibilityModifier")

package id.passage.android.exceptions

import id.passage.client.infrastructure.ClientException
import id.passage.client.infrastructure.ServerException

/**
 * Thrown when there's an error with Hosted Auth
 *
 * @see HostedAuthorizationError
 */
public open class HostedAuthorizationError(
    message: String,
) : PassageException(message) {
    // Class body
    internal companion object {
        internal fun convert(e: Exception): HostedAuthorizationError {
            val message = e.message ?: e.toString()
            return when (e) {
                is ClientException -> FinishHostedServerException(message)
                is ServerException -> FinishHostedBadRequestException(message)
                else -> HostedAuthorizationError(message)
            }
        }
    }
}

/**
 * Thrown when server returns bad request due to the invalid info.
 */
public class FinishHostedBadRequestException(
    message: String,
) : HostedAuthorizationError(message)

/**
 * Thrown when Passage internal server error occurs.
 */
public class FinishHostedServerException(
    message: String,
) : HostedAuthorizationError(message)

/**
 * Thrown when a error occurs During Hosted Logout.
 */
public class HostedLogoutException(
    message: String
) : HostedAuthorizationError(message)
