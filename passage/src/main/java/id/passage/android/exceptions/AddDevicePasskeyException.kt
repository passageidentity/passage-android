@file:Suppress("RedundantVisibilityModifier")

package id.passage.android.exceptions

import androidx.credentials.exceptions.CreateCredentialCancellationException
import androidx.credentials.exceptions.CreateCredentialException
import androidx.credentials.exceptions.CreateCredentialInterruptedException
import androidx.credentials.exceptions.CreateCredentialProviderConfigurationException
import id.passage.android.model.Model400Code
import id.passage.android.model.Model403Code
import id.passage.client.infrastructure.ClientException
import id.passage.client.infrastructure.ServerException

/**
 * Thrown when adding device passkey fails.
 *
 * @see AddDevicePasskeyCancellationException
 * @see AddDevicePasskeyInterruptedException
 * @see AddDevicePasskeyConfigurationException
 * @see AddDevicePasskeyInvalidRequestException
 * @see AddDevicePasskeyUnauthorizedException
 * @see AddDevicePasskeyInactiveUserException
 * @see AddDevicePasskeyServerException
 */
public open class AddDevicePasskeyException(message: String) : PassageException(message) {
    internal companion object {
        internal fun convert(e: Exception): AddDevicePasskeyException {
            val message = e.message ?: e.toString()
            return when (e) {
                is CreateCredentialException -> convertCreateCredentialException(e)
                is ClientException -> convertClientException(e)
                is ServerException -> AddDevicePasskeyServerException(message)
                else -> AddDevicePasskeyException(message)
            }
        }

        private fun convertClientException(e: ClientException): AddDevicePasskeyException {
            val error = parseClientException(e)
            val message = error?.error ?: ""
            when (error?.code) {
                Model400Code.request.toString() -> {
                    return AddDevicePasskeyInvalidRequestException(message)
                }
                Model403Code.userNotActive.toString() -> {
                    return AddDevicePasskeyInactiveUserException(message)
                }
            }
            if (e.statusCode == 401) {
                return AddDevicePasskeyUnauthorizedException(message)
            }
            return AddDevicePasskeyException(message)
        }

        private fun convertCreateCredentialException(e: CreateCredentialException): AddDevicePasskeyException {
            val message = e.message ?: e.toString()
            return when (e) {
                is CreateCredentialCancellationException -> {
                    AddDevicePasskeyCancellationException(message)
                }
                is CreateCredentialInterruptedException -> {
                    AddDevicePasskeyInterruptedException(message)
                }
                is CreateCredentialProviderConfigurationException -> {
                    AddDevicePasskeyConfigurationException(message)
                }
                else -> AddDevicePasskeyException(message)
            }
        }
    }
}

/**
 * The user intentionally canceled the operation and chose not to create the credential.
 */
public class AddDevicePasskeyCancellationException(message: String) : AddDevicePasskeyException(message)

/**
 * Credential creation was interrupted. Consider retrying the call.
 */
public class AddDevicePasskeyInterruptedException(message: String) : AddDevicePasskeyException(message)

/**
 * Your app is missing the provider configuration dependency.
 * Most likely, your app has not been properly configured for Passage.
 */
public class AddDevicePasskeyConfigurationException(message: String) : AddDevicePasskeyException(message)

/**
 * Thrown because of a bad request, typically when an invalid identifier is provided.
 */
public class AddDevicePasskeyInvalidRequestException(message: String) : AddDevicePasskeyException(message)

/**
 * Thrown when the request is not authorized, typically when the token has expired or not been set.
 */
public class AddDevicePasskeyUnauthorizedException(message: String) : AddDevicePasskeyException(message)

/**
 * Thrown when the user is not active.
 */
public class AddDevicePasskeyInactiveUserException(message: String) : AddDevicePasskeyException(message)

/**
 * Thrown when Passage internal server error occurs.
 */
public class AddDevicePasskeyServerException(message: String) : AddDevicePasskeyException(message)
