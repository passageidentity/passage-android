@file:Suppress("RedundantVisibilityModifier")

package id.passage.android.exceptions

import id.passage.android.model.Model400Code
import id.passage.android.model.Model403Code
import id.passage.client.infrastructure.ClientException
import id.passage.client.infrastructure.ServerException

/**
 * Thrown when there's an error exchanging a Social auth code for a PassageAuthResult.
 *
 * @see FinishSocialAuthenticationInvalidRequestException
 * @see FinishSocialAuthenticationNotVerifiedException
 * @see FinishSocialAuthenticationServerException
 */
public open class FinishSocialAuthenticationException(message: String):
    PassageException(message)
{

    internal companion object {
        internal fun convert(e: Exception): FinishSocialAuthenticationException {
            val message = e.message ?: e.toString()
            return when (e) {
                is ClientException -> convertClientException(e)
                is ServerException -> FinishSocialAuthenticationServerException(message)
                else -> FinishSocialAuthenticationException(message)
            }
        }

        private fun convertClientException(e: ClientException):
            FinishSocialAuthenticationException
        {
            val error = parseClientException(e)
            val message = error?.error ?: ""
            return when (error?.code) {
                Model400Code.request.toString() -> {
                    FinishSocialAuthenticationInvalidRequestException(message)
                }
                Model403Code.identifierNotVerified.toString() -> {
                    FinishSocialAuthenticationNotVerifiedException(message)
                }
                else -> {
                    FinishSocialAuthenticationException(message)
                }
            }
        }
    }

}


/**
 * Thrown when the auth code is invalid.
 */
public class FinishSocialAuthenticationInvalidRequestException(message: String):
    FinishSocialAuthenticationException(message)

/**
 * Thrown when the user's identifier has not been verified.
 */
public class FinishSocialAuthenticationNotVerifiedException(message: String):
    FinishSocialAuthenticationException(message)

/**
 * Thrown when Passage internal server error occurs.
 */
public class FinishSocialAuthenticationServerException(message: String):
    FinishSocialAuthenticationException(message)
