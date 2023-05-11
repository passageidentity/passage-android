package id.passage.android

import android.app.Activity
import id.passage.android.api.CurrentuserAPI
import id.passage.android.exceptions.AddDevicePasskeyException
import id.passage.android.exceptions.PassageUserException
import id.passage.android.model.ApiCurrentUserDevice
import id.passage.android.model.ApiCurrentUserDevices
import id.passage.android.model.ApiaddDeviceFinishRequest
import id.passage.android.model.ApiupdateDeviceRequest
import id.passage.android.model.ModelsCredential
import id.passage.android.model.ModelsCurrentUser
import id.passage.android.model.ModelsUser
import id.passage.android.model.UserUpdateUserEmailRequest
import id.passage.android.model.UserUpdateUserPhoneRequest

@Suppress("unused", "RedundantVisibilityModifier", "RedundantModalityModifier")
final class PassageUser private constructor(

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

    internal companion object {

        /**
         * Get Current User
         *
         * Returns an instance of PassageUser, which represents an authenticated Passage user.
         * The PassageUser class has methods that can be used to retrieve data on the current user
         * which require authentication.
         * @return PassageUser?
         * @throws PassageUserException
         */
        internal suspend fun getCurrentUser(): PassageUser? {
            val currentUserAPI = CurrentuserAPI(Passage.BASE_PATH)
            val modelsCurrentUser = try {
                currentUserAPI.getCurrentuser(Passage.appId).user ?: return null
            } catch (e: Exception) {
                throw PassageUserException.convert(e)
            }
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
     *
     * Initiate an email change for the authenticated user. An email change requires verification, so an email will be sent to the user which they must verify before the email change takes effect.
     * @param newEmail valid email
     * @return MagicLink?
     * @throws PassageUserException
     */
    public suspend fun changeEmail(newEmail: String): MagicLink? {
        val currentUserAPI = CurrentuserAPI(Passage.BASE_PATH)
        val request = UserUpdateUserEmailRequest(Passage.language, null, newEmail, null)
        val response = try {
            currentUserAPI.updateEmailCurrentuser(Passage.appId, request)
        } catch (e: Exception) {
            throw PassageUserException.convert(e)
        }
        return response.magicLink
    }

    /**
     * Change Phone
     *
     * Initiate a phone number change for the authenticated user. An phone number change requires verification, so an SMS with a link will be sent to the user which they must verify before the phone number change takes effect.
     * @param newPhone valid E164 phone number
     * @return MagicLink?
     * @throws PassageUserException
     */
    public suspend fun changePhone(newPhone: String): MagicLink? {
        val currentUserAPI = CurrentuserAPI(Passage.BASE_PATH)
        val request = UserUpdateUserPhoneRequest(Passage.language, null, newPhone, null)
        val response = try {
            currentUserAPI.updatePhoneCurrentuser(Passage.appId, request)
        } catch (e: Exception) {
            throw PassageUserException.convert(e)
        }
        return response.magicLink
    }

    /**
     * List Devices
     *
     * List all WebAuthn devices for the authenticated user. User must be authenticated via bearer token.
     * @return ApiCurrentUserDevices
     * @throws PassageUserException
     */
    public suspend fun listDevices(): ApiCurrentUserDevices {
        val currentUserAPI = CurrentuserAPI(Passage.BASE_PATH)
        return try {
            currentUserAPI.getCurrentuserDevices(Passage.appId)
        } catch (e: Exception) {
            throw PassageUserException.convert(e)
        }
    }

    /**
     * Edit Device
     *
     * Update a device by ID for the current user. Currently the only field that can be updated is
     * the friendly name. User must be authenticated via a bearer token.
     * @param deviceId Device ID
     * @param newDeviceName Friendly Name
     * @return ApiCurrentUserDevice
     * @throws PassageUserException
     */
    public suspend fun editDevice(deviceId: String, newDeviceName: String): ApiCurrentUserDevice {
        val currentUserAPI = CurrentuserAPI(Passage.BASE_PATH)
        val request = ApiupdateDeviceRequest(friendlyName = newDeviceName)
        return try {
            currentUserAPI.updateCurrentuserDevice(Passage.appId, deviceId, request)
        } catch (e: Exception) {
            throw PassageUserException.convert(e)
        }
    }

    /**
     * Add a Device Passkey
     *
     * Returns the created device for the user. User must be authenticated via a bearer token.
     * @param activity Activity to surface the Credentials Manager prompt within
     * @return PassageCredential?
     * @throws AddDevicePasskeyException
     */
    public suspend fun addDevicePasskey(activity: Activity): PassageCredential? {
        try {
            val currentUserAPI = CurrentuserAPI(Passage.BASE_PATH)
            // Get Create Credential challenge from Passage
            val webauthnStartResponse = currentUserAPI.postCurrentuserAddDeviceStart(Passage.appId)
            // Use Create Credential challenge to prompt user to create a passkey
            val createCredOptionsJson = PasskeyUtils.getCreateCredentialOptionsJson(webauthnStartResponse.handshake)
            val createCredResponse = PasskeyUtils.createPasskey(createCredOptionsJson, activity)
            // Complete registration
            val handshakeResponse = PasskeyUtils.getCreateCredentialHandshakeResponse(createCredResponse)
            val finishRequest = ApiaddDeviceFinishRequest(
                handshakeId = webauthnStartResponse.handshake?.id,
                handshakeResponse = handshakeResponse,
                userId = webauthnStartResponse.user?.id
            )
            return currentUserAPI.postCurrentuserAddDeviceFinish(Passage.appId, finishRequest).device
        } catch (e: Exception) {
            throw AddDevicePasskeyException.convert(e)
        }
    }

    /**
     * Delete Device
     *
     * Revoke a device by ID for the current user. User must be authenticated via a bearer token.
     * @param deviceId Device ID
     * @return void
     * @throws PassageUserException
     */
    public suspend fun deleteDevice(deviceId: String) {
        val currentUserAPI = CurrentuserAPI(Passage.BASE_PATH)
        try {
            currentUserAPI.deleteCurrentuserDevice(Passage.appId, deviceId)
        } catch (e: Exception) {
            PassageUserException.convert(e)
        }
    }

}
