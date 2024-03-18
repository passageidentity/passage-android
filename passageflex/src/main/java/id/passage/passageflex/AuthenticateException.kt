package id.passage.passageflex

import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.GetCredentialInterruptedException
import androidx.credentials.exceptions.GetCredentialProviderConfigurationException
import androidx.credentials.exceptions.GetCredentialUnsupportedException
import androidx.credentials.exceptions.NoCredentialException
import id.passage.android.passageflex.model.Model400Code
import id.passage.android.passageflex.model.Model401Code
import id.passage.passageflex.client.infrastructure.ClientException
import id.passage.passageflex.client.infrastructure.ServerException

/**
 * Thrown when there's an error logging in a user with a passkey.
 *
 * @see AuthenticateCancellationException
 * @see AuthenticateInterruptedException
 * @see AuthenticateConfigurationException
 * @see AuthenticateUnsupportedException
 * @see AuthenticateNoCredentialException
 * @see AuthenticateInvalidRequestException
 * @see AuthenticateDiscoverableLoginException
 * @see AuthenticateCredentialException
 * @see AuthenticateServerException
 */
public open class AuthenticateException(message: String) : PassageFlexException(message) {
    internal companion object {
        internal fun convert(e: Exception): AuthenticateException {
            val message = e.message ?: e.toString()
            return when (e) {
                is GetCredentialException -> convertCreateCredentialException(e)
                is ClientException -> convertClientException(e)
                is ServerException -> AuthenticateServerException(message)
                else -> AuthenticateException(message)
            }
        }

        private fun convertClientException(e: ClientException): AuthenticateException {
            val error = parseClientException(e)
            val message = error?.error ?: ""
            return when (error?.code) {
                Model400Code.request.toString() -> {
                    AuthenticateInvalidRequestException(message)
                }
                Model401Code.discoverableLoginFailed.toString() -> {
                    AuthenticateDiscoverableLoginException(message)
                }
                Model401Code.webauthnLoginFailed.toString() -> {
                    AuthenticateCredentialException(message)
                }
                else -> {
                    AuthenticateException(message)
                }
            }
        }

        private fun convertCreateCredentialException(e: GetCredentialException): AuthenticateException {
            val message = e.message ?: e.toString()
            return when (e) {
                is GetCredentialCancellationException -> {
                    AuthenticateCancellationException(message)
                }
                is GetCredentialInterruptedException -> {
                    AuthenticateInterruptedException(message)
                }
                is GetCredentialProviderConfigurationException -> {
                    AuthenticateConfigurationException(message)
                }
                is GetCredentialUnsupportedException -> {
                    AuthenticateUnsupportedException(message)
                }
                is NoCredentialException -> {
                    AuthenticateNoCredentialException(message)
                }
                else -> AuthenticateException(message)
            }
        }
    }
}

/**
 * The user intentionally canceled the operation.
 */
public class AuthenticateCancellationException(message: String) : AuthenticateException(message)

/**
 * Credential assertion was interrupted. Consider retrying the call.
 */
public class AuthenticateInterruptedException(message: String) : AuthenticateException(message)

/**
 * Your app is missing the provider configuration dependency.
 * Most likely, your app has not been properly configured for Passage.
 */
public class AuthenticateConfigurationException(message: String) : AuthenticateException(message)

/**
 * During the get credential flow, this is thrown when credential manager is unsupported, typically
 * because the device has disabled it or did not ship with this feature enabled.
 */
public class AuthenticateUnsupportedException(message: String) : AuthenticateException(message)

/**
 * During the get credential flow, this is returned when no viable credential is available for the
 * the user.
 */
public class AuthenticateNoCredentialException(message: String) : AuthenticateException(message)

/**
 * Thrown because of a bad request, typically when an invalid transaction id is provided.
 */
public class AuthenticateInvalidRequestException(message: String) : AuthenticateException(message)

/**
 * Thrown when no transaction id is provided and login fails.
 */
public class AuthenticateDiscoverableLoginException(message: String) : AuthenticateException(message)

/**
 * Thrown because of an issue with the passkey credential.
 */
public class AuthenticateCredentialException(message: String) : AuthenticateException(message)

/**
 * Thrown when Passage internal server error occurs.
 */
public class AuthenticateServerException(message: String) : AuthenticateException(message)
