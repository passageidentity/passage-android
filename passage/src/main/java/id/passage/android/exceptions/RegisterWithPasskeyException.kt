@file:Suppress("RedundantVisibilityModifier")

package id.passage.android.exceptions

import androidx.credentials.exceptions.*
import id.passage.android.model.Model400Code
import id.passage.android.model.Model401Code
import id.passage.client.infrastructure.ClientException
import id.passage.client.infrastructure.ServerException

/**
 * Thrown when there's an error registering a user with a passkey.
 *
 * @see RegisterWithPasskeyCancellationException
 * @see RegisterWithPasskeyInterruptedException
 * @see RegisterWithPasskeyConfigurationException
 * @see RegisterWithPasskeyNoCreateOptionException
 * @see RegisterWithPasskeyUnsupportedException
 * @see RegisterWithPasskeyInvalidRequestException
 * @see RegisterWithPasskeyCredentialException
 * @see RegisterWithPasskeyServerException
 */
public open class RegisterWithPasskeyException(message: String) : PassageException(message) {
    internal companion object {
        internal fun convert(e: Exception): RegisterWithPasskeyException {
            val message = e.message ?: e.toString()
            return when (e) {
                is CreateCredentialException -> convertCreateCredentialException(e)
                is ClientException -> convertClientException(e)
                is ServerException -> RegisterWithPasskeyServerException(message)
                else -> RegisterWithPasskeyException(message)
            }
        }

        private fun convertClientException(e: ClientException): RegisterWithPasskeyException {
            val error = parseClientException(e)
            val message = error?.error ?: ""
            return when (error?.code) {
                Model400Code.request.toString() -> {
                    RegisterWithPasskeyInvalidRequestException(message)
                }
                Model401Code.webauthnLoginFailed.toString() -> {
                    RegisterWithPasskeyCredentialException(message)
                }
                else -> {
                    RegisterWithPasskeyException(message)
                }
            }
        }

        private fun convertCreateCredentialException(e: CreateCredentialException): RegisterWithPasskeyException {
            val message = e.message ?: e.toString()
            return when (e) {
                is CreateCredentialCancellationException -> {
                    RegisterWithPasskeyCancellationException(message)
                }
                is CreateCredentialInterruptedException -> {
                    RegisterWithPasskeyInterruptedException(message)
                }
                is CreateCredentialProviderConfigurationException -> {
                    RegisterWithPasskeyConfigurationException(message)
                }
                is CreateCredentialNoCreateOptionException -> {
                    RegisterWithPasskeyNoCreateOptionException(message)
                }
                is CreateCredentialUnsupportedException -> {
                    RegisterWithPasskeyUnsupportedException(message)
                }
                else -> RegisterWithPasskeyException(message)
            }
        }
    }
}

/**
 * The user intentionally canceled the operation and chose not to register the credential.
 */
public class RegisterWithPasskeyCancellationException(message: String) : RegisterWithPasskeyException(message)

/**
 * Credential creation was interrupted. Consider retrying the call.
 */
public class RegisterWithPasskeyInterruptedException(message: String) : RegisterWithPasskeyException(message)

/**
 * Your app is missing the provider configuration dependency.
 * Most likely, your app has not been properly configured for Passage.
 */
public class RegisterWithPasskeyConfigurationException(message: String) : RegisterWithPasskeyException(message)

/**
 * During the create credential flow, this is thrown when no viable creation options were found for
 * the given CreateCredentialRequest.
 */
public class RegisterWithPasskeyNoCreateOptionException(message: String) : RegisterWithPasskeyException(message)

/**
 * During the create credential flow, this is thrown when credential manager is unsupported,
 * typically because the device has disabled it or did not ship with this feature enabled.
 */
public class RegisterWithPasskeyUnsupportedException(message: String) : RegisterWithPasskeyException(message)

/**
 * Thrown because of a bad request, typically when an invalid identifier is provided.
 */
public class RegisterWithPasskeyInvalidRequestException(message: String) : RegisterWithPasskeyException(message)

/**
 * Thrown because of an issue with the passkey credential.
 */
public class RegisterWithPasskeyCredentialException(message: String) : RegisterWithPasskeyException(message)

/**
 * Thrown when Passage internal server error occurs.
 */
public class RegisterWithPasskeyServerException(message: String) : RegisterWithPasskeyException(message)
