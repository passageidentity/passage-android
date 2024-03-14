@file:Suppress("RedundantVisibilityModifier")

package id.passage.android.exceptions

import id.passage.android.model.Model404Code
import id.passage.client.infrastructure.ClientException
import id.passage.client.infrastructure.ServerException

/**
 * Thrown when there's an error getting Passage app information.
 *
 * @see AppInfoNotFoundException
 * @see AppInfoServerException
 */
public open class AppInfoException(message: String) : PassageException(message) {
    internal companion object {
        internal fun convert(e: Exception): AppInfoException {
            val message = e.message ?: e.toString()
            return when (e) {
                is ClientException -> convertClientException(e)
                is ServerException -> AppInfoServerException(message)
                else -> AppInfoException(message)
            }
        }

        private fun convertClientException(e: ClientException): AppInfoException {
            val error = parseClientException(e)
            val message = error?.error ?: ""
            return when (error?.code) {
                Model404Code.appNotFound.toString() -> {
                    AppInfoNotFoundException(message)
                }
                else -> {
                    AppInfoException(message)
                }
            }
        }
    }
}

/**
 * Thrown when no Passage app is found with the provided app id.
 */
public class AppInfoNotFoundException(message: String) : AppInfoException(message)

/**
 * Thrown when Passage internal server error occurs.
 */
public class AppInfoServerException(message: String) : AppInfoException(message)
