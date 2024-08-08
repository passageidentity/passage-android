package id.passage.android

import android.app.Activity
import id.passage.android.api.CurrentuserAPI
import id.passage.android.exceptions.AddDevicePasskeyException
import id.passage.android.exceptions.GetMetadataException
import id.passage.android.exceptions.PassageUserException
import id.passage.android.exceptions.UpdateMetadataException
import id.passage.android.exceptions.UserInfoException
import id.passage.android.model.AddDeviceFinishRequest
import id.passage.android.model.AuthenticatorAttachment
import id.passage.android.model.CurrentUserDevicesStartRequest
import id.passage.android.model.MagicLink
import id.passage.android.model.UpdateDeviceRequest
import id.passage.android.model.UpdateMetadataRequest
import id.passage.android.model.UpdateUserEmailRequest
import id.passage.android.model.UpdateUserPhoneRequest
import id.passage.android.model.UserSocialConnections
import id.passage.android.utils.HostedUtils

class PassageCurrentUser(
    private val tokenStore: PassageTokenStore,
    private val activity: Activity,
) {
    /**
     * Get Current User
     *
     * Returns an instance of PassageUser, which represents an authenticated Passage user.
     * The PassageUser class has methods that can be used to retrieve data on the current user
     * which require authentication.
     * @return PassageUser?
     * @throws PassageUserException
     */
    suspend fun userInfo(): CurrentUserInfo {
        val currentUserAPI = CurrentuserAPI(Passage.BASE_PATH)
        try {
            return currentUserAPI.getCurrentuser(Passage.appId).user
        } catch (e: Exception) {
            throw UserInfoException.convert(e)
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
    suspend fun changeEmail(newEmail: String): MagicLink {
        val currentUserAPI = CurrentuserAPI(Passage.BASE_PATH)
        val request = UpdateUserEmailRequest(newEmail, Passage.language, null, null)
        val response =
            try {
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
    suspend fun changePhone(newPhone: String): MagicLink {
        val currentUserAPI = CurrentuserAPI(Passage.BASE_PATH)
        val request = UpdateUserPhoneRequest(Passage.language, null, newPhone, null)
        val response =
            try {
                currentUserAPI.updatePhoneCurrentuser(Passage.appId, request)
            } catch (e: Exception) {
                throw PassageUserException.convert(e)
            }
        return response.magicLink
    }

    /**
     * List Device Passkeys
     *
     * List all WebAuthn devices for the authenticated user. User must be authenticated via bearer token.
     * @return List<PassageCredential>
     * @throws PassageUserException
     */
    suspend fun passkeys(): List<Passkey> {
        val currentUserAPI = CurrentuserAPI(Passage.BASE_PATH)
        return try {
            currentUserAPI.getCurrentuserDevices(Passage.appId).devices
        } catch (e: Exception) {
            throw PassageUserException.convert(e)
        }
    }

    /**
     * Add a Device Passkey
     *
     * Returns the created device for the user. User must be authenticated via a bearer token.
     * @param activity Activity to surface the Credentials Manager prompt within
     * @param options optional configuration for passkey creation
     * @return PassageCredential
     * @throws AddDevicePasskeyException
     */
    suspend fun addPasskey(options: PasskeyCreationOptions? = null): Passkey {
        try {
            val currentUserAPI = CurrentuserAPI(Passage.BASE_PATH)
            // Get Create Credential challenge from Passage
            val authenticatorAttachment =
                options?.authenticatorAttachment
                    ?: AuthenticatorAttachment.platform
            val request = CurrentUserDevicesStartRequest(authenticatorAttachment)
            val webauthnStartResponse = currentUserAPI.postCurrentuserAddDeviceStart(Passage.appId, request)
            // Use Create Credential challenge to prompt user to create a passkey
            val createCredOptionsJson = PasskeyUtils.getCreateCredentialOptionsJson(webauthnStartResponse.handshake)
            val createCredResponse = PasskeyUtils.createPasskey(createCredOptionsJson, activity)
            // Complete registration
            val handshakeResponse = PasskeyUtils.getCreateCredentialHandshakeResponse(createCredResponse)
            val finishRequest =
                AddDeviceFinishRequest(
                    handshakeId = webauthnStartResponse.handshake.id,
                    handshakeResponse = handshakeResponse,
                    userId = webauthnStartResponse.user?.id ?: "",
                )
            return currentUserAPI.postCurrentuserAddDeviceFinish(Passage.appId, finishRequest).device
        } catch (e: Exception) {
            throw AddDevicePasskeyException.convert(e)
        }
    }

    /**
     * Delete Device Passkey
     *
     * Revoke a device by ID for the current user. User must be authenticated via a bearer token.
     * @param deviceId Device ID
     * @return void
     * @throws PassageUserException
     */
    suspend fun deletePasskey(passkeyId: String) {
        val currentUserAPI = CurrentuserAPI(Passage.BASE_PATH)
        try {
            currentUserAPI.deleteCurrentuserDevice(Passage.appId, passkeyId)
        } catch (e: Exception) {
            PassageUserException.convert(e)
        }
    }

    /**
     * Edit Device Passkey Name
     *
     * Update a device by ID for the current user. Currently the only field that can be updated is
     * the friendly name. User must be authenticated via a bearer token.
     * @param deviceId Device ID
     * @param newDevicePasskeyName Friendly Name
     * @return PassageCredential
     * @throws PassageUserException
     */
    suspend fun editPasskey(
        passkeyId: String,
        friendlyName: String,
    ): Passkey {
        val currentUserAPI = CurrentuserAPI(Passage.BASE_PATH)
        val request = UpdateDeviceRequest(friendlyName = friendlyName)
        return try {
            currentUserAPI.updateCurrentuserDevice(Passage.appId, passkeyId, request).device
        } catch (e: Exception) {
            throw PassageUserException.convert(e)
        }
    }

    /**
     * Get Social Connections
     *
     * Retrieves social connections for the current user. The user must be authenticated via a bearer token.
     * @return SocialConnectionsResponse containing the list of social connections
     * @throws PassageUserException If an error occurs during the retrieval process
     */
    suspend fun socialConnections(): UserSocialConnections {
        val currentUserAPI = CurrentuserAPI(Passage.BASE_PATH)
        try {
            return currentUserAPI.getCurrentuserSocialConnections(Passage.appId).socialConnections
        } catch (e: Exception) {
            throw PassageUserException.convert(e)
        }
    }

    /**
     * Delete SocialConnection
     *
     * Deletes a social connection for the current user. The user must be authenticated via a bearer token.
     * @param socialConnectionType The type of social connection to delete
     * @throws PassageUserException If an error occurs during the deletion process
     */
    suspend fun deleteSocialConnection(socialConnectionType: SocialConnection) {
        val currentUserAPI = CurrentuserAPI(Passage.BASE_PATH)
        try {
            return currentUserAPI.deleteCurrentuserSocialConnection(Passage.appId, socialConnectionType)
        } catch (e: Exception) {
            throw PassageUserException.convert(e)
        }
    }

    /**
     * Retrieve Current User Metadata
     *
     * Fetches the metadata associated with the current user. This method requires that the user is authenticated via a bearer token.
     * The metadata contains various attributes associated with the user, which can be used for user-specific operations.
     * @return Metadata? containing the current user's metadata, null if retrieval fails
     */
    suspend fun metadata(): Metadata? {
        val currentUserAPI = CurrentuserAPI(Passage.BASE_PATH)
        return try {
            currentUserAPI.getCurrentuserMetadata(Passage.appId)
        } catch (e: Exception) {
            throw GetMetadataException.convert(e)
        }
    }

    /**
     * Update User Metadata
     *
     * Updates the metadata associated with the current user. This method requires that the user is authenticated via a bearer token.
     * The `metadata` parameter should contain the new metadata that will be associated with the user.
     * @param metadata The Metadata object containing the updated user information
     * @return CurrentUserInfo? containing the updated user information, null if the update fails
     */
    suspend fun updateMetadata(metadata: Metadata): CurrentUserInfo? {
        val currentUserAPI = CurrentuserAPI(Passage.BASE_PATH)
        val request = UpdateMetadataRequest(userMetadata = metadata.userMetadata)
        return try {
            currentUserAPI.updateCurrentuserMetadata(Passage.appId, request).user
        } catch (e: Exception) {
            throw UpdateMetadataException.convert(e)
        }
    }

    /**
     * Sign Out Current User
     *
     * If the user is using the Hosted Login feature, a webview will open to log them out and then clear the local token store.
     * @return void
     */
    suspend fun logout() {
        val idToken = tokenStore.idToken
        if (idToken != null) {
            HostedUtils.logout(activity, idToken)
        }
        tokenStore.clearAndRevokeTokens()
    }
}
