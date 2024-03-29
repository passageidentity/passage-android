@file:Suppress("RedundantVisibilityModifier")

package id.passage.android.exceptions

import id.passage.android.model.Model400Code
import id.passage.client.infrastructure.ClientException
import id.passage.client.infrastructure.ServerException

/**
 * Thrown when creating a new registration one time passcode fails.
 *
 * @see NewRegisterOneTimePasscodeInvalidIdentifierException
 * @see NewRegisterOneTimePasscodeServerException
 */
public open class NewRegisterOneTimePasscodeException(message: String) : PassageException(message) {
    internal companion object {
        internal fun convert(e: Exception): NewRegisterOneTimePasscodeException {
            val message = e.message ?: e.toString()
            return when (e) {
                is ClientException -> convertClientException(e)
                is ServerException -> NewRegisterOneTimePasscodeServerException(message)
                else -> NewRegisterOneTimePasscodeException(message)
            }
        }

        private fun convertClientException(e: ClientException): NewRegisterOneTimePasscodeException {
            val error = parseClientException(e)
            val message = error?.error ?: ""
            return when (error?.code) {
                Model400Code.request.toString() -> NewRegisterOneTimePasscodeInvalidIdentifierException(message)
                else -> NewRegisterOneTimePasscodeException(message)
            }
        }
    }
}

/**
 * Thrown when the provided identifier is invalid.
 */
public class NewRegisterOneTimePasscodeInvalidIdentifierException(message: String) : NewRegisterOneTimePasscodeException(message)

/**
 * Thrown when Passage internal server error occurs.
 */
public class NewRegisterOneTimePasscodeServerException(message: String) : NewRegisterOneTimePasscodeException(message)
