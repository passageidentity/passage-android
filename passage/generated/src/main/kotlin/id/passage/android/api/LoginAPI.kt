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

import id.passage.android.model.APIError
import id.passage.android.model.ApiloginMagicLinkRequest
import id.passage.android.model.ApiloginMagicLinkResponse
import id.passage.android.model.ApiloginWebAuthnStartRequest
import id.passage.android.model.ApiloginWebAuthnStartResponse
import id.passage.android.model.AuthResponse1
import id.passage.android.model.HttpErrorsHTTPError
import id.passage.android.model.LoginOneTimePasscodeRequest
import id.passage.android.model.LoginWebAuthnFinishRequest
import id.passage.android.model.Model400Error
import id.passage.android.model.Model401Error
import id.passage.android.model.Model500Error
import id.passage.android.model.OneTimePasscodeResponse

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

class LoginAPI(basePath: kotlin.String = defaultBasePath, client: OkHttpClient = ApiClient.defaultClient) : ApiClient(basePath, client) {
    companion object {
        @JvmStatic
        val defaultBasePath: String by lazy {
            System.getProperties().getProperty(ApiClient.baseUrlKey, "https://virtserver.swaggerhub.com/passage_swagger/auth-gw/v1")
        }
    }

    /**
     * Login with Magic Link
     * Send a login email or SMS to the user. The user will receive an email or text with a link to complete their login.
     * @param appId App ID
     * @param user User Data
     * @return ApiloginMagicLinkResponse
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     * @throws UnsupportedOperationException If the API returns an informational or redirection response
     * @throws ClientException If the API returns a client error response
     * @throws ServerException If the API returns a server error response
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class, UnsupportedOperationException::class, ClientException::class, ServerException::class)
    suspend fun loginMagicLink(appId: kotlin.String, user: ApiloginMagicLinkRequest) : ApiloginMagicLinkResponse = withContext(Dispatchers.IO) {
        val localVarResponse = loginMagicLinkWithHttpInfo(appId = appId, user = user)

        return@withContext when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as ApiloginMagicLinkResponse
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
     * Login with Magic Link
     * Send a login email or SMS to the user. The user will receive an email or text with a link to complete their login.
     * @param appId App ID
     * @param user User Data
     * @return ApiResponse<ApiloginMagicLinkResponse?>
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class)
    suspend fun loginMagicLinkWithHttpInfo(appId: kotlin.String, user: ApiloginMagicLinkRequest) : ApiResponse<ApiloginMagicLinkResponse?> = withContext(Dispatchers.IO) {
        val localVariableConfig = loginMagicLinkRequestConfig(appId = appId, user = user)

        return@withContext request<ApiloginMagicLinkRequest, ApiloginMagicLinkResponse>(
            localVariableConfig
        )
    }

    /**
     * To obtain the request config of the operation loginMagicLink
     *
     * @param appId App ID
     * @param user User Data
     * @return RequestConfig
     */
    fun loginMagicLinkRequestConfig(appId: kotlin.String, user: ApiloginMagicLinkRequest) : RequestConfig<ApiloginMagicLinkRequest> {
        val localVariableBody = user
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()
        localVariableHeaders["Content-Type"] = "application/json"
        localVariableHeaders["Accept"] = "application/json"

        return RequestConfig(
            method = RequestMethod.POST,
            path = "/apps/{app_id}/login/magic-link/".replace("{"+"app_id"+"}", encodeURIComponent(appId.toString())),
            query = localVariableQuery,
            headers = localVariableHeaders,
            requiresAuthentication = false,
            body = localVariableBody
        )
    }

    /**
     * Login with OTP
     * Send a login email or SMS to the user. The user will receive an email or text with a one-time passcode to complete their login.
     * @param appId App ID
     * @param loginOneTimePasscodeRequest User Data
     * @return OneTimePasscodeResponse
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     * @throws UnsupportedOperationException If the API returns an informational or redirection response
     * @throws ClientException If the API returns a client error response
     * @throws ServerException If the API returns a server error response
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class, UnsupportedOperationException::class, ClientException::class, ServerException::class)
    suspend fun loginOneTimePasscode(appId: kotlin.String, loginOneTimePasscodeRequest: LoginOneTimePasscodeRequest) : OneTimePasscodeResponse = withContext(Dispatchers.IO) {
        val localVarResponse = loginOneTimePasscodeWithHttpInfo(appId = appId, loginOneTimePasscodeRequest = loginOneTimePasscodeRequest)

        return@withContext when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as OneTimePasscodeResponse
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
     * Login with OTP
     * Send a login email or SMS to the user. The user will receive an email or text with a one-time passcode to complete their login.
     * @param appId App ID
     * @param loginOneTimePasscodeRequest User Data
     * @return ApiResponse<OneTimePasscodeResponse?>
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class)
    suspend fun loginOneTimePasscodeWithHttpInfo(appId: kotlin.String, loginOneTimePasscodeRequest: LoginOneTimePasscodeRequest) : ApiResponse<OneTimePasscodeResponse?> = withContext(Dispatchers.IO) {
        val localVariableConfig = loginOneTimePasscodeRequestConfig(appId = appId, loginOneTimePasscodeRequest = loginOneTimePasscodeRequest)

        return@withContext request<LoginOneTimePasscodeRequest, OneTimePasscodeResponse>(
            localVariableConfig
        )
    }

    /**
     * To obtain the request config of the operation loginOneTimePasscode
     *
     * @param appId App ID
     * @param loginOneTimePasscodeRequest User Data
     * @return RequestConfig
     */
    fun loginOneTimePasscodeRequestConfig(appId: kotlin.String, loginOneTimePasscodeRequest: LoginOneTimePasscodeRequest) : RequestConfig<LoginOneTimePasscodeRequest> {
        val localVariableBody = loginOneTimePasscodeRequest
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()
        localVariableHeaders["Content-Type"] = "application/json"
        localVariableHeaders["Accept"] = "application/json"

        return RequestConfig(
            method = RequestMethod.POST,
            path = "/apps/{app_id}/login/otp".replace("{"+"app_id"+"}", encodeURIComponent(appId.toString())),
            query = localVariableQuery,
            headers = localVariableHeaders,
            requiresAuthentication = false,
            body = localVariableBody
        )
    }

    /**
     * Finish WebAuthn Login
     * Complete a WebAuthn login and authenticate the user. This endpoint accepts and verifies the response from &#x60;navigator.credential.get()&#x60; and returns an authentication token for the user.
     * @param appId App ID
     * @param loginWebAuthnFinishRequest User Data
     * @return AuthResponse1
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     * @throws UnsupportedOperationException If the API returns an informational or redirection response
     * @throws ClientException If the API returns a client error response
     * @throws ServerException If the API returns a server error response
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class, UnsupportedOperationException::class, ClientException::class, ServerException::class)
    suspend fun loginWebauthnFinish(appId: kotlin.String, loginWebAuthnFinishRequest: LoginWebAuthnFinishRequest) : AuthResponse1 = withContext(Dispatchers.IO) {
        val localVarResponse = loginWebauthnFinishWithHttpInfo(appId = appId, loginWebAuthnFinishRequest = loginWebAuthnFinishRequest)

        return@withContext when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as AuthResponse1
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
     * Finish WebAuthn Login
     * Complete a WebAuthn login and authenticate the user. This endpoint accepts and verifies the response from &#x60;navigator.credential.get()&#x60; and returns an authentication token for the user.
     * @param appId App ID
     * @param loginWebAuthnFinishRequest User Data
     * @return ApiResponse<AuthResponse1?>
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class)
    suspend fun loginWebauthnFinishWithHttpInfo(appId: kotlin.String, loginWebAuthnFinishRequest: LoginWebAuthnFinishRequest) : ApiResponse<AuthResponse1?> = withContext(Dispatchers.IO) {
        val localVariableConfig = loginWebauthnFinishRequestConfig(appId = appId, loginWebAuthnFinishRequest = loginWebAuthnFinishRequest)

        return@withContext request<LoginWebAuthnFinishRequest, AuthResponse1>(
            localVariableConfig
        )
    }

    /**
     * To obtain the request config of the operation loginWebauthnFinish
     *
     * @param appId App ID
     * @param loginWebAuthnFinishRequest User Data
     * @return RequestConfig
     */
    fun loginWebauthnFinishRequestConfig(appId: kotlin.String, loginWebAuthnFinishRequest: LoginWebAuthnFinishRequest) : RequestConfig<LoginWebAuthnFinishRequest> {
        val localVariableBody = loginWebAuthnFinishRequest
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()
        localVariableHeaders["Content-Type"] = "application/json"
        localVariableHeaders["Accept"] = "application/json"

        return RequestConfig(
            method = RequestMethod.POST,
            path = "/apps/{app_id}/login/webauthn/finish".replace("{"+"app_id"+"}", encodeURIComponent(appId.toString())),
            query = localVariableQuery,
            headers = localVariableHeaders,
            requiresAuthentication = false,
            body = localVariableBody
        )
    }

    /**
     * Start WebAuthn Login
     * Initiate a WebAuthn login for a user. This endpoint creates a WebAuthn credential assertion challenge that is used to perform the login ceremony from the browser.
     * @param appId App ID
     * @param user User Data
     * @return ApiloginWebAuthnStartResponse
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     * @throws UnsupportedOperationException If the API returns an informational or redirection response
     * @throws ClientException If the API returns a client error response
     * @throws ServerException If the API returns a server error response
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class, UnsupportedOperationException::class, ClientException::class, ServerException::class)
    suspend fun loginWebauthnStart(appId: kotlin.String, user: ApiloginWebAuthnStartRequest) : ApiloginWebAuthnStartResponse = withContext(Dispatchers.IO) {
        val localVarResponse = loginWebauthnStartWithHttpInfo(appId = appId, user = user)

        return@withContext when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as ApiloginWebAuthnStartResponse
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
     * Start WebAuthn Login
     * Initiate a WebAuthn login for a user. This endpoint creates a WebAuthn credential assertion challenge that is used to perform the login ceremony from the browser.
     * @param appId App ID
     * @param user User Data
     * @return ApiResponse<ApiloginWebAuthnStartResponse?>
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class)
    suspend fun loginWebauthnStartWithHttpInfo(appId: kotlin.String, user: ApiloginWebAuthnStartRequest) : ApiResponse<ApiloginWebAuthnStartResponse?> = withContext(Dispatchers.IO) {
        val localVariableConfig = loginWebauthnStartRequestConfig(appId = appId, user = user)

        return@withContext request<ApiloginWebAuthnStartRequest, ApiloginWebAuthnStartResponse>(
            localVariableConfig
        )
    }

    /**
     * To obtain the request config of the operation loginWebauthnStart
     *
     * @param appId App ID
     * @param user User Data
     * @return RequestConfig
     */
    fun loginWebauthnStartRequestConfig(appId: kotlin.String, user: ApiloginWebAuthnStartRequest) : RequestConfig<ApiloginWebAuthnStartRequest> {
        val localVariableBody = user
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()
        localVariableHeaders["Content-Type"] = "application/json"
        localVariableHeaders["Accept"] = "application/json"

        return RequestConfig(
            method = RequestMethod.POST,
            path = "/apps/{app_id}/login/webauthn/start/".replace("{"+"app_id"+"}", encodeURIComponent(appId.toString())),
            query = localVariableQuery,
            headers = localVariableHeaders,
            requiresAuthentication = false,
            body = localVariableBody
        )
    }


    private fun encodeURIComponent(uriComponent: kotlin.String): kotlin.String =
        HttpUrl.Builder().scheme("http").host("localhost").addPathSegment(uriComponent).build().encodedPathSegments[0]
}
