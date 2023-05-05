@file:Suppress("RedundantVisibilityModifier")

package id.passage.android.exceptions

import id.passage.android.model.Model400Code
import id.passage.client.infrastructure.ClientException
import id.passage.client.infrastructure.ServerException

public open class NewLoginOneTimePasscodeException(message: String): PassageException(message) {

    internal companion object {

        internal fun convert(e: Exception): NewLoginOneTimePasscodeException {
            return when (e) {
                is ClientException -> convertClientException(e)
                is ServerException -> convertServerException(e)
                else -> NewLoginOneTimePasscodeException(e.message ?: e.toString())
            }
        }

        private fun convertClientException(e: ClientException): NewLoginOneTimePasscodeException {
            val error = parseClientException(e)
            val message = error?.error ?: ""
            return when (error?.code) {
                Model400Code.request.toString() -> NewLoginOneTimePasscodeInvalidIdentifierException(message)
                else -> NewLoginOneTimePasscodeException(message)
            }
        }

        private fun convertServerException(e: ServerException): NewLoginOneTimePasscodeException {
            return NewLoginOneTimePasscodeServerException(e.message ?: e.toString())
        }

    }

}

public class NewLoginOneTimePasscodeInvalidIdentifierException(message: String): NewLoginOneTimePasscodeException(message)
public class NewLoginOneTimePasscodeServerException(message: String): NewLoginOneTimePasscodeException(message)
