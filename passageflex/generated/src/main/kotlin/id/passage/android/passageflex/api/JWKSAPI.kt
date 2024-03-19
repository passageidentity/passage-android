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

package id.passage.android.passageflex.api

import java.io.IOException
import okhttp3.OkHttpClient
import okhttp3.HttpUrl

import id.passage.android.passageflex.model.JWKResponse
import id.passage.android.passageflex.model.Model404Error
import id.passage.android.passageflex.model.Model500Error

import com.squareup.moshi.Json

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import id.passage.passageflex.client.infrastructure.ApiClient
import id.passage.passageflex.client.infrastructure.ApiResponse
import id.passage.passageflex.client.infrastructure.ClientException
import id.passage.passageflex.client.infrastructure.ClientError
import id.passage.passageflex.client.infrastructure.ServerException
import id.passage.passageflex.client.infrastructure.ServerError
import id.passage.passageflex.client.infrastructure.MultiValueMap
import id.passage.passageflex.client.infrastructure.PartConfig
import id.passage.passageflex.client.infrastructure.RequestConfig
import id.passage.passageflex.client.infrastructure.RequestMethod
import id.passage.passageflex.client.infrastructure.ResponseType
import id.passage.passageflex.client.infrastructure.Success
import id.passage.passageflex.client.infrastructure.toMultiValue

class JWKSAPI(basePath: kotlin.String = defaultBasePath, client: OkHttpClient = ApiClient.defaultClient) : ApiClient(basePath, client) {
    companion object {
        @JvmStatic
        val defaultBasePath: String by lazy {
            System.getProperties().getProperty(ApiClient.baseUrlKey, "https://auth.passage.id/v1")
        }
    }

    /**
     * Get JWKS
     * Get JWKS for an app. KIDs in the JWT can be used to match the appropriate JWK, and use the JWK&#39;s public key to verify the JWT.
     * @param appId App ID
     * @return JWKResponse
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     * @throws UnsupportedOperationException If the API returns an informational or redirection response
     * @throws ClientException If the API returns a client error response
     * @throws ServerException If the API returns a server error response
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class, UnsupportedOperationException::class, ClientException::class, ServerException::class)
    suspend fun getJwks(appId: kotlin.String) : JWKResponse = withContext(Dispatchers.IO) {
        val localVarResponse = getJwksWithHttpInfo(appId = appId)

        return@withContext when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as JWKResponse
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
     * Get JWKS
     * Get JWKS for an app. KIDs in the JWT can be used to match the appropriate JWK, and use the JWK&#39;s public key to verify the JWT.
     * @param appId App ID
     * @return ApiResponse<JWKResponse?>
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class)
    suspend fun getJwksWithHttpInfo(appId: kotlin.String) : ApiResponse<JWKResponse?> = withContext(Dispatchers.IO) {
        val localVariableConfig = getJwksRequestConfig(appId = appId)

        return@withContext request<Unit, JWKResponse>(
            localVariableConfig
        )
    }

    /**
     * To obtain the request config of the operation getJwks
     *
     * @param appId App ID
     * @return RequestConfig
     */
    fun getJwksRequestConfig(appId: kotlin.String) : RequestConfig<Unit> {
        val localVariableBody = null
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()
        localVariableHeaders["Accept"] = "application/json"

        return RequestConfig(
            method = RequestMethod.GET,
            path = "/apps/{app_id}/.well-known/jwks.json".replace("{"+"app_id"+"}", encodeURIComponent(appId.toString())),
            query = localVariableQuery,
            headers = localVariableHeaders,
            requiresAuthentication = false,
            body = localVariableBody
        )
    }


    private fun encodeURIComponent(uriComponent: kotlin.String): kotlin.String =
        HttpUrl.Builder().scheme("http").host("localhost").addPathSegment(uriComponent).build().encodedPathSegments[0]
}
