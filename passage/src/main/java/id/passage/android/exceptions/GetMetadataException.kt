package id.passage.android.exceptions

import id.passage.android.model.Model400Code
import id.passage.android.model.Model403Code
import id.passage.android.model.Model404Code
import id.passage.client.infrastructure.ClientException
import id.passage.client.infrastructure.ServerException

/**
 * Thrown when there's an issue with getting metadata
 *
 * @see GetMetadataAppNotFoundException
 * @see GetMetadataInvalidException
 * @see GetMetadataForbiddenException
 * @see GetMetadataServerException
 */

open class GetMetadataException(
    message: String,
) : PassageException(message) {
    internal companion object {
        internal fun convert(e: Exception): GetMetadataException {
            val message = e.message ?: e.toString()
            return when (e) {
                is ClientException -> convertClientException(e)
                is ServerException -> GetMetadataServerException(message)
                else -> GetMetadataException(message)
            }
        }

        private fun convertClientException(e: ClientException): GetMetadataException {
            val error = parseClientException(e)
            val message = error?.error ?: ""
            return when (error?.code) {
                Model400Code.request.toString() -> {
                    GetMetadataInvalidException(message)
                }
                Model403Code.identifierNotVerified.toString() -> {
                    GetMetadataForbiddenException(message)
                }
                Model404Code.appNotFound.toString() -> {
                    GetMetadataAppNotFoundException(message)
                }
                else -> {
                    GetMetadataServerException(message)
                }
            }
        }
    }
}

/**
 * Thrown when no Passage app is found with the provided app id.
 */
class GetMetadataAppNotFoundException(
    message: String,
) : GetMetadataException(message)

/**
 * Thrown when provided meta data is invalid.
 */
class GetMetadataInvalidException(
    message: String,
) : GetMetadataException(message)

/**
 * Thrown when identifier is not verified"
 */
class GetMetadataForbiddenException(
    message: String,
) : GetMetadataException(message)

/**
 * Thrown when Passage internal server error occurs.
 */
class GetMetadataServerException(
    message: String,
) : GetMetadataException(message)
