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

import id.passage.android.model.ActivateMagicLinkRequest
import id.passage.android.model.AuthResponse
import id.passage.android.model.GetMagicLinkStatusRequest
import id.passage.android.model.Model400Error
import id.passage.android.model.Model401Error
import id.passage.android.model.Model403Error
import id.passage.android.model.Model404Error
import id.passage.android.model.Model500Error

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

class MagicLinkAPI(basePath: kotlin.String = defaultBasePath, client: OkHttpClient = ApiClient.defaultClient) : ApiClient(basePath, client) {
    companion object {
        @JvmStatic
        val defaultBasePath: String by lazy {
            System.getProperties().getProperty(ApiClient.baseUrlKey, "https://auth.passage.id/v1")
        }
    }

    /**
     * Authenticate Magic Link
     * Authenticate a magic link for a user. This endpoint checks that the magic link is valid, then returns an authentication token for the user.
     * @param appId App ID
     * @param activateMagicLinkRequest User Data
     * @return AuthResponse
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     * @throws UnsupportedOperationException If the API returns an informational or redirection response
     * @throws ClientException If the API returns a client error response
     * @throws ServerException If the API returns a server error response
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class, UnsupportedOperationException::class, ClientException::class, ServerException::class)
    suspend fun activateMagicLink(appId: kotlin.String, activateMagicLinkRequest: ActivateMagicLinkRequest) : AuthResponse = withContext(Dispatchers.IO) {
        val localVarResponse = activateMagicLinkWithHttpInfo(appId = appId, activateMagicLinkRequest = activateMagicLinkRequest)

        return@withContext when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as AuthResponse
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
     * Authenticate Magic Link
     * Authenticate a magic link for a user. This endpoint checks that the magic link is valid, then returns an authentication token for the user.
     * @param appId App ID
     * @param activateMagicLinkRequest User Data
     * @return ApiResponse<AuthResponse?>
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class)
    suspend fun activateMagicLinkWithHttpInfo(appId: kotlin.String, activateMagicLinkRequest: ActivateMagicLinkRequest) : ApiResponse<AuthResponse?> = withContext(Dispatchers.IO) {
        val localVariableConfig = activateMagicLinkRequestConfig(appId = appId, activateMagicLinkRequest = activateMagicLinkRequest)

        return@withContext request<ActivateMagicLinkRequest, AuthResponse>(
            localVariableConfig
        )
    }

    /**
     * To obtain the request config of the operation activateMagicLink
     *
     * @param appId App ID
     * @param activateMagicLinkRequest User Data
     * @return RequestConfig
     */
    fun activateMagicLinkRequestConfig(appId: kotlin.String, activateMagicLinkRequest: ActivateMagicLinkRequest) : RequestConfig<ActivateMagicLinkRequest> {
        val localVariableBody = activateMagicLinkRequest
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()
        localVariableHeaders["Content-Type"] = "application/json"
        localVariableHeaders["Accept"] = "application/json"

        return RequestConfig(
            method = RequestMethod.PATCH,
            path = "/apps/{app_id}/magic-link/activate".replace("{"+"app_id"+"}", encodeURIComponent(appId.toString())),
            query = localVariableQuery,
            headers = localVariableHeaders,
            requiresAuthentication = false,
            body = localVariableBody
        )
    }

    /**
     * Magic Link Status
     * Check if a magic link has been activated yet or not. Once the magic link has been activated, this endpoint will return an authentication token for the user. This endpoint can be used to initiate a login on one device and then poll and wait for the login to complete on another device.
     * @param appId App ID
     * @param getMagicLinkStatusRequest Magic Link ID
     * @return AuthResponse
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     * @throws UnsupportedOperationException If the API returns an informational or redirection response
     * @throws ClientException If the API returns a client error response
     * @throws ServerException If the API returns a server error response
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class, UnsupportedOperationException::class, ClientException::class, ServerException::class)
    suspend fun magicLinkStatus(appId: kotlin.String, getMagicLinkStatusRequest: GetMagicLinkStatusRequest) : AuthResponse = withContext(Dispatchers.IO) {
        val localVarResponse = magicLinkStatusWithHttpInfo(appId = appId, getMagicLinkStatusRequest = getMagicLinkStatusRequest)

        return@withContext when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as AuthResponse
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
     * Magic Link Status
     * Check if a magic link has been activated yet or not. Once the magic link has been activated, this endpoint will return an authentication token for the user. This endpoint can be used to initiate a login on one device and then poll and wait for the login to complete on another device.
     * @param appId App ID
     * @param getMagicLinkStatusRequest Magic Link ID
     * @return ApiResponse<AuthResponse?>
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class)
    suspend fun magicLinkStatusWithHttpInfo(appId: kotlin.String, getMagicLinkStatusRequest: GetMagicLinkStatusRequest) : ApiResponse<AuthResponse?> = withContext(Dispatchers.IO) {
        val localVariableConfig = magicLinkStatusRequestConfig(appId = appId, getMagicLinkStatusRequest = getMagicLinkStatusRequest)

        return@withContext request<GetMagicLinkStatusRequest, AuthResponse>(
            localVariableConfig
        )
    }

    /**
     * To obtain the request config of the operation magicLinkStatus
     *
     * @param appId App ID
     * @param getMagicLinkStatusRequest Magic Link ID
     * @return RequestConfig
     */
    fun magicLinkStatusRequestConfig(appId: kotlin.String, getMagicLinkStatusRequest: GetMagicLinkStatusRequest) : RequestConfig<GetMagicLinkStatusRequest> {
        val localVariableBody = getMagicLinkStatusRequest
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()
        localVariableHeaders["Content-Type"] = "application/json"
        localVariableHeaders["Accept"] = "application/json"

        return RequestConfig(
            method = RequestMethod.POST,
            path = "/apps/{app_id}/magic-link/status".replace("{"+"app_id"+"}", encodeURIComponent(appId.toString())),
            query = localVariableQuery,
            headers = localVariableHeaders,
            requiresAuthentication = false,
            body = localVariableBody
        )
    }


    private fun encodeURIComponent(uriComponent: kotlin.String): kotlin.String =
        HttpUrl.Builder().scheme("http").host("localhost").addPathSegment(uriComponent).build().encodedPathSegments[0]
}
