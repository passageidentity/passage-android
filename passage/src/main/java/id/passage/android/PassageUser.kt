package id.passage.android

import android.util.Log
import androidx.credentials.exceptions.CreateCredentialException
import id.passage.android.api.CurrentuserAPI
import id.passage.android.model.ApiCurrentUserDevice
import id.passage.android.model.ApiCurrentUserDevices
import id.passage.android.model.ApiupdateDeviceRequest
import id.passage.android.model.ModelsCredential
import id.passage.android.model.ModelsCurrentUser
import id.passage.android.model.ModelsUser
import id.passage.android.model.UserUpdateUserEmailRequest
import id.passage.android.model.UserUpdateUserPhoneRequest
import id.passage.client.infrastructure.ApiClient
import java.lang.Exception

@Suppress("UNUSED")
class PassageUser private constructor(

    /* When this user was created */
    val createdAt: String? = null,

    /* The user's email */
    val email: String? = null,

    /* Whether or not the user's email has been verified */
    val emailVerified: Boolean? = null,

    /* The userID (referred to as Handle) */
    val id: String? = null,

    /* The last time this user logged in */
    val lastLoginAt: String? = null,

    /* How many times the user has successfully logged in */
    val loginCount: Int? = null,

    /* The user's phone */
    val phone: String? = null,

    /* Whether or not the user's phone has been verified */
    val phoneVerified: Boolean? = null,

    /* User status: active, inactive, pending */
    val status: String? = null,

    /* When this user was last updated */
    val updatedAt: String? = null,

    val userMetadata: Any? = null,

    /* Whether or not the user has authenticated via webAuthn before (if len(WebAuthnDevices) > 0) */
    val webauthn: Boolean? = null,

    /* The list of devices this user has authenticated with via webAuthn */
    val webauthnDevices: List<ModelsCredential>? = null,

    /* List of credential types that user has created */
    val webauthnTypes: List<String>? = null

) {

    companion object {

        // TODO: Document this. Its for developer if they don't want to use the PassageStore
        fun setAuthToken(token: String?) {
            ApiClient.accessToken = token
        }

        /**
         * Get Current User
         * Returns an instance of PassageUser, which represents an authenticated Passage user.
         * The PassageUser class has methods that can be used to retrieve data on the current user
         * which require authentication.
         * @param appId App ID
         * @param language Language
         * @return PassageUser?
         * @throws PassageClientException If the API returns a client error response
         * @throws PassageServerException If the API returns a server error response
         * @throws PassageException If the request fails for another reason
         */
        internal suspend fun getCurrentUser(): PassageUser? {
            val currentUserAPI = CurrentuserAPI(Passage.BASE_PATH)
            val modelsCurrentUser = currentUserAPI.getCurrentuser(Passage.appId).user ?: return null
            return convertToPassageUser(modelsCurrentUser)
        }

        private fun convertToPassageUser(modelsCurrentUser: ModelsCurrentUser): PassageUser {
            return PassageUser(
                createdAt = modelsCurrentUser.createdAt,
                email = modelsCurrentUser.email,
                emailVerified = modelsCurrentUser.emailVerified,
                id = modelsCurrentUser.id,
                lastLoginAt = modelsCurrentUser.lastLoginAt,
                loginCount = modelsCurrentUser.loginCount,
                phone = modelsCurrentUser.phone,
                phoneVerified = modelsCurrentUser.phoneVerified,
                status = modelsCurrentUser.status,
                updatedAt = modelsCurrentUser.updatedAt,
                userMetadata= modelsCurrentUser.userMetadata,
                webauthn = modelsCurrentUser.webauthn,
                webauthnDevices = modelsCurrentUser.webauthnDevices,
                webauthnTypes = modelsCurrentUser.webauthnTypes
            )
        }

        internal fun convertToPassageUser(modelsUser: ModelsUser): PassageUser {
            return PassageUser(
                email = modelsUser.email,
                emailVerified = modelsUser.emailVerified,
                id = modelsUser.id,
                phone = modelsUser.phone,
                phoneVerified = modelsUser.phoneVerified,
                status = modelsUser.status,
                userMetadata= modelsUser.userMetadata,
                webauthn = modelsUser.webauthn,
                webauthnTypes = modelsUser.webauthnTypes
            )
        }
    }

    /**
     * Change Email
     * Initiate an email change for the authenticated user. An email change requires verification, so an email will be sent to the user which they must verify before the email change takes effect.
     * @param newEmail valid email
     * @return MagicLink?
     * @throws PassageClientException If the API returns a client error response
     * @throws PassageServerException If the API returns a server error response
     * @throws PassageException If the request fails for another reason
     */
    suspend fun changeEmail(newEmail: String): MagicLink? {
        val currentUserAPI = CurrentuserAPI(Passage.BASE_PATH)
        val request = UserUpdateUserEmailRequest(Passage.language, null, newEmail, null)
        val response = currentUserAPI.updateEmailCurrentuser(Passage.appId, request)
        return response.magicLink
    }

    /**
     * Change Phone
     * Initiate a phone number change for the authenticated user. An phone number change requires verification, so an SMS with a link will be sent to the user which they must verify before the phone number change takes effect.
     * @param newPhone valid E164 phone number
     * @return MagicLink?
     * @throws PassageClientException If the API returns a client error response
     * @throws PassageServerException If the API returns a server error response
     * @throws PassageException If the request fails for another reason
     */
    suspend fun changePhone(newPhone: String): MagicLink? {
        val currentUserAPI = CurrentuserAPI(Passage.BASE_PATH)
        val request = UserUpdateUserPhoneRequest(Passage.language, null, newPhone, null)
        val response = currentUserAPI.updatePhoneCurrentuser(Passage.appId, request)
        return response.magicLink
    }

    /**
     * List Devices
     * List all WebAuthn devices for the authenticated user. User must be authenticated via bearer token.
     * @return ApiCurrentUserDevices
     * @throws PassageClientException If the API returns a client error response
     * @throws PassageServerException If the API returns a server error response
     * @throws PassageException If the request fails for another reason
     */
    suspend fun listDevices(): ApiCurrentUserDevices {
        val currentUserAPI = CurrentuserAPI(Passage.BASE_PATH)
        return currentUserAPI.getCurrentuserDevices(Passage.appId)
    }

    /**
     * Edit Device
     * Update a device by ID for the current user. Currently the only field that can be updated is
     * the friendly name. User must be authenticated via a bearer token.
     * @param deviceId Device ID
     * @param newDeviceName Friendly Name
     * @return ApiCurrentUserDevice
     * @throws PassageClientException If the API returns a client error response
     * @throws PassageServerException If the API returns a server error response
     * @throws PassageException If the request fails for another reason
     */
    suspend fun editDevice(deviceId: String, newDeviceName: String): ApiCurrentUserDevice {
        val currentUserAPI = CurrentuserAPI(Passage.BASE_PATH)
        val request = ApiupdateDeviceRequest(friendlyName = newDeviceName)
        return currentUserAPI.updateCurrentuserDevice(Passage.appId, deviceId, request)
    }

    /**
     * Add a device passkey
     * Returns the created device for the user. User must be authenticated via a bearer token.
     * @return PassageAuthResult?
     * @throws CreateCredentialException If the attempt to create a passkey fails
     * @throws PassageClientException If the Passage API returns a client error response
     * @throws PassageServerException If the Passage API returns a server error response
     * @throws PassageException If the request fails for another reason
     */
    suspend fun addDevice(): PassageAuthResult? {
        val currentUserAPI = CurrentuserAPI(Passage.BASE_PATH)
        val startResponse = currentUserAPI.postCurrentuserAddDeviceStart(Passage.appId)
        // TODO: Finish
        return null
    }

    /**
     * Delete Device
     * Revoke a device by ID for the current user. User must be authenticated via a bearer token.
     * @param deviceId Device ID
     * @return void
     * @throws PassageClientException If the API returns a client error response
     * @throws PassageServerException If the API returns a server error response
     * @throws PassageException If the request fails for another reason
     */
    suspend fun deleteDevice(deviceId: String) {
        val currentUserAPI = CurrentuserAPI(Passage.BASE_PATH)
        currentUserAPI.deleteCurrentuserDevice(Passage.appId, deviceId)
    }

}
