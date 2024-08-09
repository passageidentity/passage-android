package id.passage.android.exceptions

import id.passage.android.model.Model400Code
import id.passage.android.model.Model403Code
import id.passage.android.model.Model404Code
import id.passage.client.infrastructure.ClientException
import id.passage.client.infrastructure.ServerException

/**
 * Thrown when there's an issue with updating metadata
 *
 * @see UpdateMetadataAppNotFoundException
 * @see UpdateMetadataInvalidException
 * @see UpdateMetadataForbiddenException
 * @see UpdateMetadataServerException
 */
open class UpdateMetadataException(
    message: String,
) : PassageException(message) {
    internal companion object {
        internal fun convert(e: Exception): UpdateMetadataException {
            val message = e.message ?: e.toString()
            return when (e) {
                is ClientException -> convertClientException(e)
                is ServerException -> UpdateMetadataServerException(message)
                else -> UpdateMetadataException(message)
            }
        }

        private fun convertClientException(e: ClientException): UpdateMetadataException {
            val error = parseClientException(e)
            val message = error?.error ?: ""
            return when (error?.code) {
                Model400Code.request.toString() -> {
                    UpdateMetadataInvalidException(message)
                }
                Model403Code.identifierNotVerified.toString() -> {
                    UpdateMetadataForbiddenException(message)
                }
                Model404Code.appNotFound.toString() -> {
                    UpdateMetadataAppNotFoundException(message)
                }
                else -> {
                    UpdateMetadataServerException(message)
                }
            }
        }
    }
}

/**
 * Thrown when no Passage app is found with the provided app id.
 */
class UpdateMetadataAppNotFoundException(
    message: String,
) : UpdateMetadataException(message)

/**
 * Thrown when provided meta data is invalid.
 */
class UpdateMetadataInvalidException(
    message: String,
) : UpdateMetadataException(message)

/**
 * Thrown when identifier is not verified"
 */
public class UpdateMetadataForbiddenException(
    message: String,
) : UpdateMetadataException(message)

/**
 * Thrown when Passage internal server error occurs.
 */
public class UpdateMetadataServerException(
    message: String,
) : UpdateMetadataException(message)
