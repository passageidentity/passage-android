@file:Suppress("RedundantVisibilityModifier")

package id.passage.android.exceptions

import id.passage.android.model.Model400Code
import id.passage.client.infrastructure.ClientException
import id.passage.client.infrastructure.ServerException

/**
 * Thrown when creating a new login one time passcode fails.
 *
 * @see OneTimePasscodeInvalidIdentifierLoginException
 * @see OneTimePasscodeServerLoginException
 */
public open class OneTimePasscodeLoginException(
    message: String,
) : PassageException(message) {
    internal companion object {
        internal fun convert(e: Exception): OneTimePasscodeLoginException {
            val message = e.message ?: e.toString()
            return when (e) {
                is ClientException -> convertClientException(e)
                is ServerException -> OneTimePasscodeServerLoginException(message)
                else -> OneTimePasscodeLoginException(message)
            }
        }

        private fun convertClientException(e: ClientException): OneTimePasscodeLoginException {
            val error = parseClientException(e)
            val message = error?.error ?: ""
            return when (error?.code) {
                Model400Code.request.toString() -> OneTimePasscodeInvalidIdentifierLoginException(message)
                else -> OneTimePasscodeLoginException(message)
            }
        }
    }
}

/**
 * Thrown when the provided identifier is invalid.
 */
public class OneTimePasscodeInvalidIdentifierLoginException(
    message: String,
) : OneTimePasscodeLoginException(message)

/**
 * Thrown when Passage internal server error occurs.
 */
public class OneTimePasscodeServerLoginException(
    message: String,
) : OneTimePasscodeLoginException(message)
