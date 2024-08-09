package id.passage.android.exceptions

import id.passage.android.model.Model400Code
import id.passage.android.model.Model404Code
import id.passage.client.infrastructure.ClientException
import id.passage.client.infrastructure.ServerException

/**
 * Thrown when there's an issue with creating a user
 *
 * @see CreateUserAppNotFoundException
 * @see CreateUserInvalidRequestException
 * @see CreateUserServerException
 */

open class CreateUserException(
    message: String,
) : PassageException(message) {
    internal companion object {
        internal fun convert(e: Exception): CreateUserException {
            val message = e.message ?: e.toString()
            return when (e) {
                is ClientException -> convertClientException(e)
                is ServerException -> CreateUserServerException(message)
                else -> CreateUserException(message)
            }
        }

        private fun convertClientException(e: ClientException): CreateUserException {
            val error = parseClientException(e)
            val message = error?.error ?: ""
            return when (error?.code) {
                Model400Code.request.toString() -> {
                    CreateUserInvalidRequestException(message)
                }
                Model404Code.appNotFound.toString() -> {
                    CreateUserAppNotFoundException(message)
                }
                else -> {
                    CreateUserServerException(message)
                }
            }
        }
    }
}

/**
 * Thrown when no Passage app is found with the provided app id.
 */
class CreateUserAppNotFoundException(
    message: String,
) : CreateUserException(message)

/**
 * Thrown when identifier is not valid
 */
class CreateUserInvalidRequestException(
    message: String,
) : CreateUserException(message)

/**
 * Thrown when Passage internal server error occurs.
 */
class CreateUserServerException(
    message: String,
) : CreateUserException(message)
