package id.passage.android.exceptions

import id.passage.android.model.Model400Code
import id.passage.android.model.Model404Code
import id.passage.client.infrastructure.ClientException
import id.passage.client.infrastructure.ServerException

/**
 * Thrown when there's an issue to know if user exists.
 *
 * @see UserExistsAppNotFoundException
 * @see UserExistsInvalidRequestException
 * @see UserExistsServerException
 */
public open class UserExistsException(
    message: String,
) : PassageException(message) {
    internal companion object {
        internal fun convert(e: Exception): UserExistsException {
            val message = e.message ?: e.toString()
            return when (e) {
                is ClientException -> convertClientException(e)
                is ServerException -> UserExistsServerException(message)
                else -> UserExistsException(message)
            }
        }

        private fun convertClientException(e: ClientException): UserExistsException {
            val error = parseClientException(e)
            val message = error?.error ?: ""
            return when (error?.code) {
                Model400Code.request.toString() -> {
                    UserExistsInvalidRequestException(message)
                }
                Model404Code.appNotFound.toString() -> {
                    UserExistsAppNotFoundException(message)
                }
                else -> {
                    UserExistsServerException(message)
                }
            }
        }
    }
}

/**
 * Thrown when no Passage app is found with the provided app id.
 */
public class UserExistsAppNotFoundException(
    message: String,
) : UserExistsException(message)

/**
 * Thrown when identifier is not verified"
 */

public class UserExistsInvalidRequestException(
    message: String,
) : UserExistsException(message)

/**
 * Thrown when Passage internal server error occurs.
 */
public class UserExistsServerException(
    message: String,
) : UserExistsException(message)
