@file:Suppress("RedundantVisibilityModifier")

package id.passage.android.exceptions

import id.passage.android.model.Model401Code
import id.passage.android.model.Model403Code
import id.passage.client.infrastructure.ClientException
import id.passage.client.infrastructure.ServerException

/**
 * Thrown when there's an issue with the magic link activation.
 *
 * @see MagicLinkActivateInvalidException
 * @see MagicLinkActivateUserNotActiveException
 * @see MagicLinkActivateServerException
 */
public open class MagicLinkActivateException(message: String): PassageException(message) {

    internal companion object {

        internal fun convert(e: Exception): MagicLinkActivateException {
            val message = e.message ?: e.toString()
            return when (e) {
                is ClientException -> convertClientException(e)
                is ServerException -> MagicLinkActivateServerException(message)
                else -> MagicLinkActivateException(e.message ?: e.toString())
            }
        }

        private fun convertClientException(e: ClientException): MagicLinkActivateException {
            val error = parseClientException(e)
            val message = error?.error ?: ""
            return when (error?.code) {
                Model401Code.invalidMagicLink.toString() -> {
                    MagicLinkActivateInvalidException(message)
                }
                Model403Code.userNotActive.toString() -> {
                    MagicLinkActivateUserNotActiveException(message)
                }
                else -> {
                    MagicLinkActivateException(message)
                }
            }
        }

    }

}

/**
 * Thrown when the magic link is invalid
 */
public class MagicLinkActivateInvalidException(message: String): MagicLinkActivateException(message)

/**
 * Thrown when the user is not active.
 */
public class MagicLinkActivateUserNotActiveException(message: String): MagicLinkActivateException(message)

/**
 * Thrown when Passage internal server error occurs.
 */
public class MagicLinkActivateServerException(message: String): MagicLinkActivateException(message)
