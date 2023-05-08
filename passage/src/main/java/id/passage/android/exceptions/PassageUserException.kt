@file:Suppress("RedundantVisibilityModifier")

package id.passage.android.exceptions

import id.passage.android.model.Model400Code
import id.passage.android.model.Model401Code
import id.passage.android.model.Model404Code
import id.passage.client.infrastructure.ClientException
import id.passage.client.infrastructure.ServerException

/**
 * Thrown when user requests fail.
 *
 * @see PassageUserRequestException
 * @see PassageUserUnauthorizedException
 * @see PassageUserNotFoundException
 * @see PassageUserInactiveUserException
 * @see PassageUserServerException
 */
public open class PassageUserException(message: String): PassageException(message) {

    internal companion object {

        internal fun convert(e: Exception): PassageUserException {
            val message = e.message ?: e.toString()
            return when (e) {
                is ClientException -> convertClientException(e)
                is ServerException -> PassageUserServerException(message)
                else -> PassageUserException(message)
            }
        }

        private fun convertClientException(e: ClientException): PassageUserException {
            val error = parseClientException(e)
            val message = error?.error ?: ""
            when (error?.code) {
                Model400Code.request.toString() -> {
                    return PassageUserRequestException(message)
                }
                Model401Code.userNotActive.toString() -> {
                    return PassageUserInactiveUserException(message)
                }
                Model404Code.userNotFound.toString() -> {
                    return PassageUserNotFoundException(message)
                }
            }
            if (e.statusCode == 401) {
                return PassageUserUnauthorizedException(message)
            }
            return PassageUserException(message)
        }

    }

}

/**
 * Thrown when invalid values are provided to the request.
 */
public class PassageUserRequestException(message: String): PassageUserException(message)

/**
 * Thrown when the request is not authorized, typically when the token has expired or not been set.
 */
public class PassageUserUnauthorizedException(message: String): PassageUserException(message)

/**
 * Thrown when the user is not found.
 */
public class PassageUserNotFoundException(message: String): PassageUserException(message)

/**
 * Thrown when the user is not active.
 */
public class PassageUserInactiveUserException(message: String): PassageUserException(message)

/**
 * Thrown when Passage internal server error occurs.
 */
public class PassageUserServerException(message: String): PassageUserException(message)
