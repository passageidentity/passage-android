@file:Suppress("RedundantVisibilityModifier")

package id.passage.android.exceptions

import id.passage.android.model.Model400Code
import id.passage.client.infrastructure.ClientException
import id.passage.client.infrastructure.ServerException

/**
 * Thrown when creating a new registration one time passcode fails.
 *
 * @see OneTimePasscodeInvalidIdentifierRegisterException
 * @see OneTimePasscodeServerRegisterException
 */
public open class OneTimePasscodeRegisterException(
    message: String,
) : PassageException(message) {
    internal companion object {
        internal fun convert(e: Exception): OneTimePasscodeRegisterException {
            val message = e.message ?: e.toString()
            return when (e) {
                is ClientException -> convertClientException(e)
                is ServerException -> OneTimePasscodeServerRegisterException(message)
                else -> OneTimePasscodeRegisterException(message)
            }
        }

        private fun convertClientException(e: ClientException): OneTimePasscodeRegisterException {
            val error = parseClientException(e)
            val message = error?.error ?: ""
            return when (error?.code) {
                Model400Code.request.toString() -> OneTimePasscodeInvalidIdentifierRegisterException(message)
                else -> OneTimePasscodeRegisterException(message)
            }
        }
    }
}

/**
 * Thrown when the provided identifier is invalid.
 */
public class OneTimePasscodeInvalidIdentifierRegisterException(
    message: String,
) : OneTimePasscodeRegisterException(message)

/**
 * Thrown when Passage internal server error occurs.
 */
public class OneTimePasscodeServerRegisterException(
    message: String,
) : OneTimePasscodeRegisterException(message)
