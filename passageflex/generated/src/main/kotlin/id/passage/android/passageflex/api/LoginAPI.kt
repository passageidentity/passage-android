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

import id.passage.android.passageflex.model.AuthResponse
import id.passage.android.passageflex.model.LoginMagicLinkRequest
import id.passage.android.passageflex.model.LoginMagicLinkResponse
import id.passage.android.passageflex.model.LoginOneTimePasscodeRequest
import id.passage.android.passageflex.model.LoginWebAuthnFinishRequest
import id.passage.android.passageflex.model.LoginWebAuthnStartRequest
import id.passage.android.passageflex.model.LoginWebAuthnStartResponse
import id.passage.android.passageflex.model.Model400Error
import id.passage.android.passageflex.model.Model401Error
import id.passage.android.passageflex.model.Model403Error
import id.passage.android.passageflex.model.Model404Error
import id.passage.android.passageflex.model.Model500Error
import id.passage.android.passageflex.model.OneTimePasscodeResponse

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

class LoginAPI(basePath: kotlin.String = defaultBasePath, client: OkHttpClient = ApiClient.defaultClient) : ApiClient(basePath, client) {
    companion object {
        @JvmStatic
        val defaultBasePath: String by lazy {
            System.getProperties().getProperty(ApiClient.baseUrlKey, "https://auth.passage.id/v1")
        }
    }

    /**
     * Login with Magic Link
     * Send a login email or SMS to the user. The user will receive an email or text with a link to complete their login.
     * @param appId App ID
     * @param loginMagicLinkRequest User Data
     * @return LoginMagicLinkResponse
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     * @throws UnsupportedOperationException If the API returns an informational or redirection response
     * @throws ClientException If the API returns a client error response
     * @throws ServerException If the API returns a server error response
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class, UnsupportedOperationException::class, ClientException::class, ServerException::class)
    suspend fun loginMagicLink(appId: kotlin.String, loginMagicLinkRequest: LoginMagicLinkRequest) : LoginMagicLinkResponse = withContext(Dispatchers.IO) {
        val localVarResponse = loginMagicLinkWithHttpInfo(appId = appId, loginMagicLinkRequest = loginMagicLinkRequest)

        return@withContext when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as LoginMagicLinkResponse
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
     * @param loginMagicLinkRequest User Data
     * @return ApiResponse<LoginMagicLinkResponse?>
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class)
    suspend fun loginMagicLinkWithHttpInfo(appId: kotlin.String, loginMagicLinkRequest: LoginMagicLinkRequest) : ApiResponse<LoginMagicLinkResponse?> = withContext(Dispatchers.IO) {
        val localVariableConfig = loginMagicLinkRequestConfig(appId = appId, loginMagicLinkRequest = loginMagicLinkRequest)

        return@withContext request<LoginMagicLinkRequest, LoginMagicLinkResponse>(
            localVariableConfig
        )
    }

    /**
     * To obtain the request config of the operation loginMagicLink
     *
     * @param appId App ID
     * @param loginMagicLinkRequest User Data
     * @return RequestConfig
     */
    fun loginMagicLinkRequestConfig(appId: kotlin.String, loginMagicLinkRequest: LoginMagicLinkRequest) : RequestConfig<LoginMagicLinkRequest> {
        val localVariableBody = loginMagicLinkRequest
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()
        localVariableHeaders["Content-Type"] = "application/json"
        localVariableHeaders["Accept"] = "application/json"

        return RequestConfig(
            method = RequestMethod.POST,
            path = "/apps/{app_id}/login/magic-link".replace("{"+"app_id"+"}", encodeURIComponent(appId.toString())),
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
     * @return AuthResponse
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     * @throws UnsupportedOperationException If the API returns an informational or redirection response
     * @throws ClientException If the API returns a client error response
     * @throws ServerException If the API returns a server error response
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class, UnsupportedOperationException::class, ClientException::class, ServerException::class)
    suspend fun loginWebauthnFinish(appId: kotlin.String, loginWebAuthnFinishRequest: LoginWebAuthnFinishRequest) : AuthResponse = withContext(Dispatchers.IO) {
        val localVarResponse = loginWebauthnFinishWithHttpInfo(appId = appId, loginWebAuthnFinishRequest = loginWebAuthnFinishRequest)

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
     * Finish WebAuthn Login
     * Complete a WebAuthn login and authenticate the user. This endpoint accepts and verifies the response from &#x60;navigator.credential.get()&#x60; and returns an authentication token for the user.
     * @param appId App ID
     * @param loginWebAuthnFinishRequest User Data
     * @return ApiResponse<AuthResponse?>
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class)
    suspend fun loginWebauthnFinishWithHttpInfo(appId: kotlin.String, loginWebAuthnFinishRequest: LoginWebAuthnFinishRequest) : ApiResponse<AuthResponse?> = withContext(Dispatchers.IO) {
        val localVariableConfig = loginWebauthnFinishRequestConfig(appId = appId, loginWebAuthnFinishRequest = loginWebAuthnFinishRequest)

        return@withContext request<LoginWebAuthnFinishRequest, AuthResponse>(
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
     * @param loginWebAuthnStartRequest User Data (optional)
     * @return LoginWebAuthnStartResponse
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     * @throws UnsupportedOperationException If the API returns an informational or redirection response
     * @throws ClientException If the API returns a client error response
     * @throws ServerException If the API returns a server error response
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class, UnsupportedOperationException::class, ClientException::class, ServerException::class)
    suspend fun loginWebauthnStart(appId: kotlin.String, loginWebAuthnStartRequest: LoginWebAuthnStartRequest? = null) : LoginWebAuthnStartResponse = withContext(Dispatchers.IO) {
        val localVarResponse = loginWebauthnStartWithHttpInfo(appId = appId, loginWebAuthnStartRequest = loginWebAuthnStartRequest)

        return@withContext when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as LoginWebAuthnStartResponse
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
     * @param loginWebAuthnStartRequest User Data (optional)
     * @return ApiResponse<LoginWebAuthnStartResponse?>
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class)
    suspend fun loginWebauthnStartWithHttpInfo(appId: kotlin.String, loginWebAuthnStartRequest: LoginWebAuthnStartRequest?) : ApiResponse<LoginWebAuthnStartResponse?> = withContext(Dispatchers.IO) {
        val localVariableConfig = loginWebauthnStartRequestConfig(appId = appId, loginWebAuthnStartRequest = loginWebAuthnStartRequest)

        return@withContext request<LoginWebAuthnStartRequest, LoginWebAuthnStartResponse>(
            localVariableConfig
        )
    }

    /**
     * To obtain the request config of the operation loginWebauthnStart
     *
     * @param appId App ID
     * @param loginWebAuthnStartRequest User Data (optional)
     * @return RequestConfig
     */
    fun loginWebauthnStartRequestConfig(appId: kotlin.String, loginWebAuthnStartRequest: LoginWebAuthnStartRequest?) : RequestConfig<LoginWebAuthnStartRequest> {
        val localVariableBody = loginWebAuthnStartRequest
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()
        localVariableHeaders["Accept"] = "application/json"

        return RequestConfig(
            method = RequestMethod.POST,
            path = "/apps/{app_id}/login/webauthn/start".replace("{"+"app_id"+"}", encodeURIComponent(appId.toString())),
            query = localVariableQuery,
            headers = localVariableHeaders,
            requiresAuthentication = false,
            body = localVariableBody
        )
    }


    private fun encodeURIComponent(uriComponent: kotlin.String): kotlin.String =
        HttpUrl.Builder().scheme("http").host("localhost").addPathSegment(uriComponent).build().encodedPathSegments[0]
}