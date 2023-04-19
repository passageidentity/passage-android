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

import id.passage.android.model.ApiUserResponse
import id.passage.android.model.HttpErrorsHTTPError
import id.passage.android.model.ModelsCreateUserParams

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

class UsersAPI(basePath: kotlin.String = defaultBasePath, client: OkHttpClient = ApiClient.defaultClient) : ApiClient(basePath, client) {
    companion object {
        @JvmStatic
        val defaultBasePath: String by lazy {
            System.getProperties().getProperty(ApiClient.baseUrlKey, "http://auth.passage.id/v1")
        }
    }

    /**
     * Get User
     * Get user information, if the user exists. This endpoint can be used to determine whether a user has an existing account and if they should login or register.
     * @param appId App ID
     * @param identifier email or phone number
     * @return ApiUserResponse
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     * @throws UnsupportedOperationException If the API returns an informational or redirection response
     * @throws ClientException If the API returns a client error response
     * @throws ServerException If the API returns a server error response
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class, UnsupportedOperationException::class, ClientException::class, ServerException::class)
    suspend fun checkUserIdentifier(appId: kotlin.String, identifier: kotlin.String) : ApiUserResponse = withContext(Dispatchers.IO) {
        val localVarResponse = checkUserIdentifierWithHttpInfo(appId = appId, identifier = identifier)

        return@withContext when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as ApiUserResponse
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
     * Get User
     * Get user information, if the user exists. This endpoint can be used to determine whether a user has an existing account and if they should login or register.
     * @param appId App ID
     * @param identifier email or phone number
     * @return ApiResponse<ApiUserResponse?>
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class)
    suspend fun checkUserIdentifierWithHttpInfo(appId: kotlin.String, identifier: kotlin.String) : ApiResponse<ApiUserResponse?> = withContext(Dispatchers.IO) {
        val localVariableConfig = checkUserIdentifierRequestConfig(appId = appId, identifier = identifier)

        return@withContext request<Unit, ApiUserResponse>(
            localVariableConfig
        )
    }

    /**
     * To obtain the request config of the operation checkUserIdentifier
     *
     * @param appId App ID
     * @param identifier email or phone number
     * @return RequestConfig
     */
    fun checkUserIdentifierRequestConfig(appId: kotlin.String, identifier: kotlin.String) : RequestConfig<Unit> {
        val localVariableBody = null
        val localVariableQuery: MultiValueMap = mutableMapOf<kotlin.String, kotlin.collections.List<kotlin.String>>()
            .apply {
                put("identifier", listOf(identifier.toString()))
            }
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()
        localVariableHeaders["Accept"] = "application/json"

        return RequestConfig(
            method = RequestMethod.GET,
            path = "/apps/{app_id}/users/".replace("{"+"app_id"+"}", encodeURIComponent(appId.toString())),
            query = localVariableQuery,
            headers = localVariableHeaders,
            requiresAuthentication = false,
            body = localVariableBody
        )
    }

    /**
     * Create User
     * Create a user
     * @param appId App ID
     * @param user user options
     * @return ApiUserResponse
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     * @throws UnsupportedOperationException If the API returns an informational or redirection response
     * @throws ClientException If the API returns a client error response
     * @throws ServerException If the API returns a server error response
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class, UnsupportedOperationException::class, ClientException::class, ServerException::class)
    suspend fun createUser(appId: kotlin.String, user: ModelsCreateUserParams) : ApiUserResponse = withContext(Dispatchers.IO) {
        val localVarResponse = createUserWithHttpInfo(appId = appId, user = user)

        return@withContext when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as ApiUserResponse
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
     * Create User
     * Create a user
     * @param appId App ID
     * @param user user options
     * @return ApiResponse<ApiUserResponse?>
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class)
    suspend fun createUserWithHttpInfo(appId: kotlin.String, user: ModelsCreateUserParams) : ApiResponse<ApiUserResponse?> = withContext(Dispatchers.IO) {
        val localVariableConfig = createUserRequestConfig(appId = appId, user = user)

        return@withContext request<ModelsCreateUserParams, ApiUserResponse>(
            localVariableConfig
        )
    }

    /**
     * To obtain the request config of the operation createUser
     *
     * @param appId App ID
     * @param user user options
     * @return RequestConfig
     */
    fun createUserRequestConfig(appId: kotlin.String, user: ModelsCreateUserParams) : RequestConfig<ModelsCreateUserParams> {
        val localVariableBody = user
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()
        localVariableHeaders["Accept"] = "application/json"

        return RequestConfig(
            method = RequestMethod.POST,
            path = "/apps/{app_id}/users/".replace("{"+"app_id"+"}", encodeURIComponent(appId.toString())),
            query = localVariableQuery,
            headers = localVariableHeaders,
            requiresAuthentication = false,
            body = localVariableBody
        )
    }


    private fun encodeURIComponent(uriComponent: kotlin.String): kotlin.String =
        HttpUrl.Builder().scheme("http").host("localhost").addPathSegment(uriComponent).build().encodedPathSegments[0]
}