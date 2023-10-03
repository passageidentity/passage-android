@file:Suppress("RedundantVisibilityModifier")

package id.passage.android.exceptions

import androidx.credentials.exceptions.*
import id.passage.android.model.Model400Code
import id.passage.android.model.Model401Code
import id.passage.client.infrastructure.ClientException
import id.passage.client.infrastructure.ServerException

/**
 * Thrown when there's an error logging in a user with a passkey.
 *
 * @see LoginWithPasskeyCancellationException
 * @see LoginWithPasskeyInterruptedException
 * @see LoginWithPasskeyConfigurationException
 * @see LoginWithPasskeyUnsupportedException
 * @see LoginWithPasskeyNoCredentialException
 * @see LoginWithPasskeyInvalidRequestException
 * @see LoginWithPasskeyDiscoverableLoginException
 * @see LoginWithPasskeyInactiveUserException
 * @see LoginWithPasskeyCredentialException
 * @see LoginWithPasskeyServerException
 */
public open class LoginWithPasskeyException(message: String): PassageException(message) {

    internal companion object {

        internal fun convert(e: Exception): LoginWithPasskeyException {
            val message = e.message ?: e.toString()
            return when (e) {
                is GetCredentialException -> convertCreateCredentialException(e)
                is ClientException -> convertClientException(e)
                is ServerException -> LoginWithPasskeyServerException(message)
                else -> LoginWithPasskeyException(message)
            }
        }

        private fun convertClientException(e: ClientException): LoginWithPasskeyException {
            val error = parseClientException(e)
            val message = error?.error ?: ""
            return when (error?.code) {
                Model400Code.request.toString() -> {
                    LoginWithPasskeyInvalidRequestException(message)
                }
                Model401Code.discoverableLoginFailed.toString() -> {
                    LoginWithPasskeyDiscoverableLoginException(message)
                }
                Model401Code.userNotActive.toString() -> {
                    LoginWithPasskeyInactiveUserException(message)
                }
                Model401Code.webauthnLoginFailed.toString() -> {
                    LoginWithPasskeyCredentialException(message)
                }
                else -> {
                    LoginWithPasskeyException(message)
                }
            }
        }

        private fun convertCreateCredentialException(e: GetCredentialException): LoginWithPasskeyException {
            val message = e.message ?: e.toString()
            return when (e) {
                is GetCredentialCancellationException -> {
                    LoginWithPasskeyCancellationException(message)
                }
                is GetCredentialInterruptedException -> {
                    LoginWithPasskeyInterruptedException(message)
                }
                is GetCredentialProviderConfigurationException -> {
                    LoginWithPasskeyConfigurationException(message)
                }
                is GetCredentialUnsupportedException -> {
                    LoginWithPasskeyUnsupportedException(message)
                }
                is NoCredentialException -> {
                    LoginWithPasskeyNoCredentialException(message)
                }
                else -> LoginWithPasskeyException(message)
            }
        }

    }

}

/**
 * The user intentionally canceled the operation and chose not to register the credential.
 */
public class LoginWithPasskeyCancellationException(message: String): LoginWithPasskeyException(message)

/**
 * Credential creation was interrupted. Consider retrying the call.
 */
public class LoginWithPasskeyInterruptedException(message: String): LoginWithPasskeyException(message)

/**
 * Your app is missing the provider configuration dependency.
 * Most likely, your app has not been properly configured for Passage.
 */
public class LoginWithPasskeyConfigurationException(message: String): LoginWithPasskeyException(message)

/**
 * During the get credential flow, this is thrown when credential manager is unsupported, typically
 * because the device has disabled it or did not ship with this feature enabled.
 */
public class LoginWithPasskeyUnsupportedException(message: String): LoginWithPasskeyException(message)

/**
 * During the get credential flow, this is returned when no viable credential is available for the
 * the user.
 */
public class LoginWithPasskeyNoCredentialException(message: String): LoginWithPasskeyException(message)

/**
 * Thrown because of a bad request, typically when an invalid identifier is provided.
 */
public class LoginWithPasskeyInvalidRequestException(message: String): LoginWithPasskeyException(message)

/**
 * Thrown when no identifier is provided and login fails.
 */
public class LoginWithPasskeyDiscoverableLoginException(message: String): LoginWithPasskeyException(message)

/**
 * Thrown when the user is not active.
 */
public class LoginWithPasskeyInactiveUserException(message: String): LoginWithPasskeyException(message)

/**
 * Thrown because of an issue with the passkey credential.
 */
public class LoginWithPasskeyCredentialException(message: String): LoginWithPasskeyException(message)

/**
 * Thrown when Passage internal server error occurs.
 */
public class LoginWithPasskeyServerException(message: String): LoginWithPasskeyException(message)
