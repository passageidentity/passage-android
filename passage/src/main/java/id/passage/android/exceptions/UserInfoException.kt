package id.passage.android.exceptions

import id.passage.android.model.Model401Code
import id.passage.android.model.Model403Code
import id.passage.android.model.Model404Code
import id.passage.client.infrastructure.ClientException
import id.passage.client.infrastructure.ServerException

/**
 * Thrown when there's an issue with getting user information
 *
 * @see UserInfoAppNotFoundException
 * @see UserInfoForbiddenException
 * @see OneTimePasscodeActivateExceededAttemptsException
 * @see UserInfoServerException
 */
public open class UserInfoException(
    message: String,
) : PassageException(message) {
    internal companion object {
        internal fun convert(e: Exception): UserInfoException {
            val message = e.message ?: e.toString()
            return when (e) {
                is ClientException -> convertClientException(e)
                is ServerException -> UserInfoServerException(message)
                else -> UserInfoException(message)
            }
        }

        private fun convertClientException(e: ClientException): UserInfoException {
            val error = parseClientException(e)
            val message = error?.error ?: ""
            return when (error?.code) {
                Model401Code.invalidAccessToken.toString() -> {
                    UserInfoUnauthorizedException(message)
                }
                Model403Code.identifierNotVerified.toString() -> {
                    UserInfoForbiddenException(message)
                }
                Model404Code.appNotFound.toString() -> {
                    UserInfoAppNotFoundException(message)
                }
                else -> {
                    UserInfoServerException(message)
                }
            }
        }
    }
}

/**
 * Thrown when no Passage app is found with the provided app id.
 */
public class UserInfoAppNotFoundException(
    message: String,
) : UserInfoException(message)

/**
 * Thrown when identifier is not verified
 */

public class UserInfoForbiddenException(
    message: String,
) : UserInfoException(message)

/**
 * Thrown when identifier is unauthorized
 */
public class UserInfoUnauthorizedException(
    message: String,
) : UserInfoException(message)

/**
 * Thrown when Passage internal server error occurs.
 */
public class UserInfoServerException(
    message: String,
) : UserInfoException(message)
