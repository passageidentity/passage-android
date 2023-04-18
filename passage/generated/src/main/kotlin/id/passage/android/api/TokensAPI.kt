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

import id.passage.android.model.ApiAuthResponse
import id.passage.android.model.ApirefreshAuthTokenRequest
import id.passage.android.model.HttpErrorsHTTPError

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

class TokensAPI(basePath: kotlin.String = defaultBasePath, client: OkHttpClient = ApiClient.defaultClient) : ApiClient(basePath, client) {
    companion object {
        @JvmStatic
        val defaultBasePath: String by lazy {
            System.getProperties().getProperty(ApiClient.baseUrlKey, "http://auth.passage.id/v1")
        }
    }

    /**
     * Creates new auth and refresh token
     * Creates and returns a new auth token and a new refresh token
     * @param appId App ID
     * @param token Refresh token
     * @return ApiAuthResponse
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     * @throws UnsupportedOperationException If the API returns an informational or redirection response
     * @throws ClientException If the API returns a client error response
     * @throws ServerException If the API returns a server error response
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class, UnsupportedOperationException::class, ClientException::class, ServerException::class)
    suspend fun refreshAuthToken(appId: kotlin.String, token: ApirefreshAuthTokenRequest) : ApiAuthResponse = withContext(Dispatchers.IO) {
        val localVarResponse = refreshAuthTokenWithHttpInfo(appId = appId, token = token)

        return@withContext when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as ApiAuthResponse
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
     * Creates new auth and refresh token
     * Creates and returns a new auth token and a new refresh token
     * @param appId App ID
     * @param token Refresh token
     * @return ApiResponse<ApiAuthResponse?>
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class)
    suspend fun refreshAuthTokenWithHttpInfo(appId: kotlin.String, token: ApirefreshAuthTokenRequest) : ApiResponse<ApiAuthResponse?> = withContext(Dispatchers.IO) {
        val localVariableConfig = refreshAuthTokenRequestConfig(appId = appId, token = token)

        return@withContext request<ApirefreshAuthTokenRequest, ApiAuthResponse>(
            localVariableConfig
        )
    }

    /**
     * To obtain the request config of the operation refreshAuthToken
     *
     * @param appId App ID
     * @param token Refresh token
     * @return RequestConfig
     */
    fun refreshAuthTokenRequestConfig(appId: kotlin.String, token: ApirefreshAuthTokenRequest) : RequestConfig<ApirefreshAuthTokenRequest> {
        val localVariableBody = token
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()
        localVariableHeaders["Content-Type"] = "application/json"
        localVariableHeaders["Accept"] = "application/json"

        return RequestConfig(
            method = RequestMethod.POST,
            path = "/apps/{app_id}/tokens/".replace("{"+"app_id"+"}", encodeURIComponent(appId.toString())),
            query = localVariableQuery,
            headers = localVariableHeaders,
            requiresAuthentication = false,
            body = localVariableBody
        )
    }

    /**
     * Revokes refresh token
     * Revokes the refresh token
     * @param appId App ID
     * @param refreshToken Refresh token
     * @return void
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     * @throws UnsupportedOperationException If the API returns an informational or redirection response
     * @throws ClientException If the API returns a client error response
     * @throws ServerException If the API returns a server error response
     */
    @Throws(IllegalStateException::class, IOException::class, UnsupportedOperationException::class, ClientException::class, ServerException::class)
    suspend fun revokeRefreshToken(appId: kotlin.String, refreshToken: kotlin.String) : Unit = withContext(Dispatchers.IO) {
        val localVarResponse = revokeRefreshTokenWithHttpInfo(appId = appId, refreshToken = refreshToken)

        return@withContext when (localVarResponse.responseType) {
            ResponseType.Success -> Unit
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
     * Revokes refresh token
     * Revokes the refresh token
     * @param appId App ID
     * @param refreshToken Refresh token
     * @return ApiResponse<Unit?>
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     */
    @Throws(IllegalStateException::class, IOException::class)
    suspend fun revokeRefreshTokenWithHttpInfo(appId: kotlin.String, refreshToken: kotlin.String) : ApiResponse<Unit?> = withContext(Dispatchers.IO) {
        val localVariableConfig = revokeRefreshTokenRequestConfig(appId = appId, refreshToken = refreshToken)

        return@withContext request<Unit, Unit>(
            localVariableConfig
        )
    }

    /**
     * To obtain the request config of the operation revokeRefreshToken
     *
     * @param appId App ID
     * @param refreshToken Refresh token
     * @return RequestConfig
     */
    fun revokeRefreshTokenRequestConfig(appId: kotlin.String, refreshToken: kotlin.String) : RequestConfig<Unit> {
        val localVariableBody = null
        val localVariableQuery: MultiValueMap = mutableMapOf<kotlin.String, kotlin.collections.List<kotlin.String>>()
            .apply {
                put("refresh_token", listOf(refreshToken.toString()))
            }
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()
        
        return RequestConfig(
            method = RequestMethod.DELETE,
            path = "/apps/{app_id}/tokens/".replace("{"+"app_id"+"}", encodeURIComponent(appId.toString())),
            query = localVariableQuery,
            headers = localVariableHeaders,
            requiresAuthentication = false,
            body = localVariableBody
        )
    }


    private fun encodeURIComponent(uriComponent: kotlin.String): kotlin.String =
        HttpUrl.Builder().scheme("http").host("localhost").addPathSegment(uriComponent).build().encodedPathSegments[0]
}
