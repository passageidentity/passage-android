@file:Suppress("RedundantVisibilityModifier")

package id.passage.android.exceptions

import id.passage.android.model.Model400Code
import id.passage.android.model.Model401Code
import id.passage.client.infrastructure.ClientException
import id.passage.client.infrastructure.ServerException

/**
 * Thrown when there's an issue with the passcode activation, typically when the user inputs an
 * invalid one time passcode.
 *
 * @see OneTimePasscodeActivateInvalidRequestException
 * @see OneTimePasscodeActivateInactiveUserException
 * @see OneTimePasscodeActivateServerException
 */
public open class OneTimePasscodeActivateException(message: String): PassageException(message) {

    internal companion object {

        internal fun convert(e: Exception): OneTimePasscodeActivateException {
            val message = e.message ?: e.toString()
            return when (e) {
                is ClientException -> convertClientException(e)
                is ServerException -> OneTimePasscodeActivateServerException(message)
                else -> OneTimePasscodeActivateException(message)
            }
        }

        private fun convertClientException(e: ClientException): OneTimePasscodeActivateException {
            val error = parseClientException(e)
            val message = error?.error ?: ""
            return when (error?.code) {
                Model400Code.request.toString() -> {
                    OneTimePasscodeActivateInvalidRequestException(message)
                }
                Model401Code.userNotActive.toString() -> {
                    OneTimePasscodeActivateInactiveUserException(message)
                }
                "exceeded_attempts" -> {
                    OneTimePasscodeActivateExceededAttemptsException(message)
                }
                else -> {
                    OneTimePasscodeActivateException(message)
                }
            }
        }

    }

}

/**
 * Thrown when either the otp or the otpId is invalid.
 */
public class OneTimePasscodeActivateInvalidRequestException(message: String): OneTimePasscodeActivateException(message)

/**
 * Thrown when the user is not active.
 */
public class OneTimePasscodeActivateInactiveUserException(message: String): OneTimePasscodeActivateException(message)

/**
 * Thrown when the user has had too many failed activation attempts.
 */
public class OneTimePasscodeActivateExceededAttemptsException(message: String): OneTimePasscodeActivateException(message)

/**
 * Thrown when Passage internal server error occurs.
 */
public class OneTimePasscodeActivateServerException(message: String): OneTimePasscodeActivateException(message)
