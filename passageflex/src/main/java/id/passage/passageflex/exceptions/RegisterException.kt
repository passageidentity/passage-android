package id.passage.passageflex.exceptions

import androidx.credentials.exceptions.CreateCredentialCancellationException
import androidx.credentials.exceptions.CreateCredentialException
import androidx.credentials.exceptions.CreateCredentialInterruptedException
import androidx.credentials.exceptions.CreateCredentialNoCreateOptionException
import androidx.credentials.exceptions.CreateCredentialProviderConfigurationException
import androidx.credentials.exceptions.CreateCredentialUnsupportedException
import id.passage.android.passageflex.model.Model400Code
import id.passage.android.passageflex.model.Model401Code
import id.passage.passageflex.client.infrastructure.ClientException
import id.passage.passageflex.client.infrastructure.ServerException

/**
 * Thrown when there's an error registering a user with a passkey.
 *
 * @see RegisterCancellationException
 * @see RegisterInterruptedException
 * @see RegisterConfigurationException
 * @see RegisterNoCreateOptionException
 * @see RegisterUnsupportedException
 * @see RegisterInvalidRequestException
 * @see RegisterCredentialException
 * @see RegisterServerException
 */
public open class RegisterException(message: String) : PassageFlexException(message) {
    internal companion object {
        internal fun convert(e: Exception): RegisterException {
            val message = e.message ?: e.toString()
            return when (e) {
                is CreateCredentialException -> convertCreateCredentialException(e)
                is ClientException -> convertClientException(e)
                is ServerException -> RegisterServerException(message)
                else -> RegisterException(message)
            }
        }

        private fun convertClientException(e: ClientException): RegisterException {
            val error = parseClientException(e)
            val message = error?.error ?: ""
            return when (error?.code) {
                Model400Code.request.toString() -> {
                    RegisterInvalidRequestException(message)
                }
                Model401Code.webauthnLoginFailed.toString() -> {
                    RegisterCredentialException(message)
                }
                else -> {
                    RegisterException(message)
                }
            }
        }

        private fun convertCreateCredentialException(e: CreateCredentialException): RegisterException {
            val message = e.message ?: e.toString()
            return when (e) {
                is CreateCredentialCancellationException -> {
                    RegisterCancellationException(message)
                }
                is CreateCredentialInterruptedException -> {
                    RegisterInterruptedException(message)
                }
                is CreateCredentialProviderConfigurationException -> {
                    RegisterConfigurationException(message)
                }
                is CreateCredentialNoCreateOptionException -> {
                    RegisterNoCreateOptionException(message)
                }
                is CreateCredentialUnsupportedException -> {
                    RegisterUnsupportedException(message)
                }
                else -> RegisterException(message)
            }
        }
    }
}

/**
 * The user intentionally canceled the operation and chose not to register the credential.
 */
public class RegisterCancellationException(message: String) : RegisterException(message)

/**
 * Credential creation was interrupted. Consider retrying the call.
 */
public class RegisterInterruptedException(message: String) : RegisterException(message)

/**
 * Your app is missing the provider configuration dependency.
 * Most likely, your app has not been properly configured for Passage.
 */
public class RegisterConfigurationException(message: String) : RegisterException(message)

/**
 * During the create credential flow, this is thrown when no viable creation options were found for
 * the given CreateCredentialRequest.
 */
public class RegisterNoCreateOptionException(message: String) : RegisterException(message)

/**
 * During the create credential flow, this is thrown when credential manager is unsupported,
 * typically because the device has disabled it or did not ship with this feature enabled.
 */
public class RegisterUnsupportedException(message: String) : RegisterException(message)

/**
 * Thrown because of a bad request, typically when an invalid transaction idz is provided.
 */
public class RegisterInvalidRequestException(message: String) : RegisterException(message)

/**
 * Thrown because of an issue with the passkey credential.
 */
public class RegisterCredentialException(message: String) : RegisterException(message)

/**
 * Thrown when Passage internal server error occurs.
 */
public class RegisterServerException(message: String) : RegisterException(message)
