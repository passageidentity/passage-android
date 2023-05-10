@file:Suppress("RedundantVisibilityModifier")

package id.passage.android.exceptions

import id.passage.android.model.Model400Code
import id.passage.client.infrastructure.ClientException
import id.passage.client.infrastructure.ServerException

/**
 * Thrown when creating a new login one time passcode fails.
 *
 * @see NewLoginOneTimePasscodeInvalidIdentifierException
 * @see NewLoginOneTimePasscodeServerException
 */
public open class NewLoginOneTimePasscodeException(message: String): PassageException(message) {

    internal companion object {

        internal fun convert(e: Exception): NewLoginOneTimePasscodeException {
            val message = e.message ?: e.toString()
            return when (e) {
                is ClientException -> convertClientException(e)
                is ServerException -> NewLoginOneTimePasscodeServerException(message)
                else -> NewLoginOneTimePasscodeException(message)
            }
        }

        private fun convertClientException(e: ClientException): NewLoginOneTimePasscodeException {
            val error = parseClientException(e)
            val message = error?.error ?: ""
            return when (error?.code) {
                Model400Code.request.toString() -> NewLoginOneTimePasscodeInvalidIdentifierException(message)
                else -> NewLoginOneTimePasscodeException(message)
            }
        }

    }

}

/**
 * Thrown when the provided identifier is invalid.
 */
public class NewLoginOneTimePasscodeInvalidIdentifierException(message: String): NewLoginOneTimePasscodeException(message)

/**
 * Thrown when Passage internal server error occurs.
 */
public class NewLoginOneTimePasscodeServerException(message: String): NewLoginOneTimePasscodeException(message)
