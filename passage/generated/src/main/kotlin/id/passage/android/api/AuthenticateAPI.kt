/**
 *
 * Please note:
 * This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * Do not edit this file manually.
 *
 */

@file:Suppress(
    "ArrayInDataClass",
    "EnumEntryName",
    "RemoveRedundantQualifierName",
    "UnusedImport"
)

package id.passage.android.api

import java.io.IOException
import okhttp3.OkHttpClient
import okhttp3.HttpUrl

import id.passage.android.model.AuthenticateWebAuthnFinishWithTransactionRequest
import id.passage.android.model.AuthenticateWebAuthnStartWithTransactionRequest
import id.passage.android.model.AuthenticateWebAuthnStartWithTransactionResponse
import id.passage.android.model.Model400Error
import id.passage.android.model.Model401Error
import id.passage.android.model.Model403Error
import id.passage.android.model.Model404Error
import id.passage.android.model.Model409Error
import id.passage.android.model.Model500Error
import id.passage.android.model.Nonce

import com.squareup.moshi.Json

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import id.passage.client.infrastructure.ApiClient
import id.passage.client.infrastructure.ApiResponse
import id.passage.client.infrastructure.ClientException
import id.passage.client.infrastructure.ClientError
import id.passage.client.infrastructure.ServerException
import id.passage.client.infrastructure.ServerError
import id.passage.client.infrastructure.MultiValueMap
import id.passage.client.infrastructure.PartConfig
import id.passage.client.infrastructure.RequestConfig
import id.passage.client.infrastructure.RequestMethod
import id.passage.client.infrastructure.ResponseType
import id.passage.client.infrastructure.Success
import id.passage.client.infrastructure.toMultiValue

class AuthenticateAPI(basePath: kotlin.String = defaultBasePath, client: OkHttpClient = ApiClient.defaultClient) : ApiClient(basePath, client) {
    companion object {
        @JvmStatic
        val defaultBasePath: String by lazy {
            System.getProperties().getProperty(ApiClient.baseUrlKey, "https://auth.passage.id/v1")
        }
    }

    /**
     * Finish WebAuthn authentication with an optional transaction
     * Complete a WebAuthn authentication and authenticate the user via a transaction. This endpoint accepts and verifies the response from &#x60;navigator.credential.get()&#x60; and returns a nonce meant to be exchanged for an authentication token for the user.
     * @param appId App ID
     * @param authenticateWebAuthnFinishWithTransactionRequest 
     * @return Nonce
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     * @throws UnsupportedOperationException If the API returns an informational or redirection response
     * @throws ClientException If the API returns a client error response
     * @throws ServerException If the API returns a server error response
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class, UnsupportedOperationException::class, ClientException::class, ServerException::class)
    suspend fun authenticateWebauthnFinishWithTransaction(appId: kotlin.String, authenticateWebAuthnFinishWithTransactionRequest: AuthenticateWebAuthnFinishWithTransactionRequest) : Nonce = withContext(Dispatchers.IO) {
        val localVarResponse = authenticateWebauthnFinishWithTransactionWithHttpInfo(appId = appId, authenticateWebAuthnFinishWithTransactionRequest = authenticateWebAuthnFinishWithTransactionRequest)

        return@withContext when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as Nonce
            ResponseType.Informational -> throw UnsupportedOperationException("Client does not support Informational responses.")
            ResponseType.Redirection -> throw UnsupportedOperationException("Client does not support Redirection responses.")
            ResponseType.ClientError -> {
                val localVarError = localVarResponse as ClientError<*>
                throw ClientException("Client error : ${localVarError.statusCode} ${localVarError.message.orEmpty()}", localVarError.statusCode, localVarResponse)
            }
            ResponseType.ServerError -> {
                val localVarError = localVarResponse as ServerError<*>
                throw ServerException("Server error : ${localVarError.statusCode} ${localVarError.message.orEmpty()}", localVarError.statusCode, localVarResponse)
            }
        }
    }

    /**
     * Finish WebAuthn authentication with an optional transaction
     * Complete a WebAuthn authentication and authenticate the user via a transaction. This endpoint accepts and verifies the response from &#x60;navigator.credential.get()&#x60; and returns a nonce meant to be exchanged for an authentication token for the user.
     * @param appId App ID
     * @param authenticateWebAuthnFinishWithTransactionRequest 
     * @return ApiResponse<Nonce?>
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class)
    suspend fun authenticateWebauthnFinishWithTransactionWithHttpInfo(appId: kotlin.String, authenticateWebAuthnFinishWithTransactionRequest: AuthenticateWebAuthnFinishWithTransactionRequest) : ApiResponse<Nonce?> = withContext(Dispatchers.IO) {
        val localVariableConfig = authenticateWebauthnFinishWithTransactionRequestConfig(appId = appId, authenticateWebAuthnFinishWithTransactionRequest = authenticateWebAuthnFinishWithTransactionRequest)

        return@withContext request<AuthenticateWebAuthnFinishWithTransactionRequest, Nonce>(
            localVariableConfig
        )
    }

    /**
     * To obtain the request config of the operation authenticateWebauthnFinishWithTransaction
     *
     * @param appId App ID
     * @param authenticateWebAuthnFinishWithTransactionRequest 
     * @return RequestConfig
     */
    fun authenticateWebauthnFinishWithTransactionRequestConfig(appId: kotlin.String, authenticateWebAuthnFinishWithTransactionRequest: AuthenticateWebAuthnFinishWithTransactionRequest) : RequestConfig<AuthenticateWebAuthnFinishWithTransactionRequest> {
        val localVariableBody = authenticateWebAuthnFinishWithTransactionRequest
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()
        localVariableHeaders["Content-Type"] = "application/json"
        localVariableHeaders["Accept"] = "application/json"

        return RequestConfig(
            method = RequestMethod.POST,
            path = "/apps/{app_id}/authenticate/transactions/webauthn/finish".replace("{"+"app_id"+"}", encodeURIComponent(appId.toString())),
            query = localVariableQuery,
            headers = localVariableHeaders,
            requiresAuthentication = false,
            body = localVariableBody
        )
    }

    /**
     * Start WebAuthn authentication with an optional transaction
     * Initiate a WebAuthn authentication for a user via a transaction. This endpoint creates a WebAuthn credential assertion challenge that is used to perform the authentication ceremony from the browser.
     * @param appId App ID
     * @param authenticateWebAuthnStartWithTransactionRequest  (optional)
     * @return AuthenticateWebAuthnStartWithTransactionResponse
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     * @throws UnsupportedOperationException If the API returns an informational or redirection response
     * @throws ClientException If the API returns a client error response
     * @throws ServerException If the API returns a server error response
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class, UnsupportedOperationException::class, ClientException::class, ServerException::class)
    suspend fun authenticateWebauthnStartWithTransaction(appId: kotlin.String, authenticateWebAuthnStartWithTransactionRequest: AuthenticateWebAuthnStartWithTransactionRequest? = null) : AuthenticateWebAuthnStartWithTransactionResponse = withContext(Dispatchers.IO) {
        val localVarResponse = authenticateWebauthnStartWithTransactionWithHttpInfo(appId = appId, authenticateWebAuthnStartWithTransactionRequest = authenticateWebAuthnStartWithTransactionRequest)

        return@withContext when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as AuthenticateWebAuthnStartWithTransactionResponse
            ResponseType.Informational -> throw UnsupportedOperationException("Client does not support Informational responses.")
            ResponseType.Redirection -> throw UnsupportedOperationException("Client does not support Redirection responses.")
            ResponseType.ClientError -> {
                val localVarError = localVarResponse as ClientError<*>
                throw ClientException("Client error : ${localVarError.statusCode} ${localVarError.message.orEmpty()}", localVarError.statusCode, localVarResponse)
            }
            ResponseType.ServerError -> {
                val localVarError = localVarResponse as ServerError<*>
                throw ServerException("Server error : ${localVarError.statusCode} ${localVarError.message.orEmpty()}", localVarError.statusCode, localVarResponse)
            }
        }
    }

    /**
     * Start WebAuthn authentication with an optional transaction
     * Initiate a WebAuthn authentication for a user via a transaction. This endpoint creates a WebAuthn credential assertion challenge that is used to perform the authentication ceremony from the browser.
     * @param appId App ID
     * @param authenticateWebAuthnStartWithTransactionRequest  (optional)
     * @return ApiResponse<AuthenticateWebAuthnStartWithTransactionResponse?>
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class)
    suspend fun authenticateWebauthnStartWithTransactionWithHttpInfo(appId: kotlin.String, authenticateWebAuthnStartWithTransactionRequest: AuthenticateWebAuthnStartWithTransactionRequest?) : ApiResponse<AuthenticateWebAuthnStartWithTransactionResponse?> = withContext(Dispatchers.IO) {
        val localVariableConfig = authenticateWebauthnStartWithTransactionRequestConfig(appId = appId, authenticateWebAuthnStartWithTransactionRequest = authenticateWebAuthnStartWithTransactionRequest)

        return@withContext request<AuthenticateWebAuthnStartWithTransactionRequest, AuthenticateWebAuthnStartWithTransactionResponse>(
            localVariableConfig
        )
    }

    /**
     * To obtain the request config of the operation authenticateWebauthnStartWithTransaction
     *
     * @param appId App ID
     * @param authenticateWebAuthnStartWithTransactionRequest  (optional)
     * @return RequestConfig
     */
    fun authenticateWebauthnStartWithTransactionRequestConfig(appId: kotlin.String, authenticateWebAuthnStartWithTransactionRequest: AuthenticateWebAuthnStartWithTransactionRequest?) : RequestConfig<AuthenticateWebAuthnStartWithTransactionRequest> {
        val localVariableBody = authenticateWebAuthnStartWithTransactionRequest
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()
        localVariableHeaders["Accept"] = "application/json"

        return RequestConfig(
            method = RequestMethod.POST,
            path = "/apps/{app_id}/authenticate/transactions/webauthn/start".replace("{"+"app_id"+"}", encodeURIComponent(appId.toString())),
            query = localVariableQuery,
            headers = localVariableHeaders,
            requiresAuthentication = false,
            body = localVariableBody
        )
    }


    private fun encodeURIComponent(uriComponent: kotlin.String): kotlin.String =
        HttpUrl.Builder().scheme("http").host("localhost").addPathSegment(uriComponent).build().encodedPathSegments[0]
}