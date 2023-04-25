package id.passage.android

import android.app.Activity
import androidx.credentials.CreateCredentialResponse
import androidx.credentials.CreatePublicKeyCredentialRequest
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.GetPublicKeyCredentialOption
import androidx.credentials.exceptions.CreateCredentialException
import androidx.credentials.exceptions.GetCredentialException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class PasskeyUtils {

    companion object {

        /**
         * Registers a user passkey credential that can be used to authenticate the user to
         * the app in the future.
         *
         * The execution potentially launches framework UI flows for a user to view their registration
         * options, grant consent, etc.
         *
         * @param requestJson The privileged request in JSON format in the [standard webauthn web json](https://w3c.github.io/webauthn/#dictdef-publickeycredentialcreationoptionsjson).
         * @throws CreateCredentialException If the request fails
         */
        suspend fun createPasskey(requestJson: String, activity: Activity): CreateCredentialResponse {
            val credentialManager = CredentialManager.create(activity)
            val publicKeyCredRequest = CreatePublicKeyCredentialRequest(requestJson)
            val publicKeyCredResponse = CoroutineScope(Dispatchers.IO).async {
                // Show the user Credential Manager with option to create a Passkey
                return@async credentialManager.createCredential(
                    publicKeyCredRequest,
                    activity
                )
            }.await()
            return publicKeyCredResponse
        }

        /**
         * Requests a passkey credential from the user.
         *
         * The execution potentially launches framework UI flows for a user to view available
         * credentials, consent to using one of them, etc.
         *
         * @param requestJson The privileged request in JSON format in the [standard webauthn web json](https://w3c.github.io/webauthn/#dictdef-publickeycredentialcreationoptionsjson).
         * @throws GetCredentialException If the request fails
         */
        suspend fun getPasskey(requestJson: String, activity: Activity): GetCredentialResponse {
            val credentialManager = CredentialManager.create(activity)
            val getCredOption = GetPublicKeyCredentialOption(requestJson)
            val getCredRequest = GetCredentialRequest(listOf(getCredOption))
            val getCredResponse = CoroutineScope(Dispatchers.IO).async {
                // Show the user Credential Manager with option to login with a Passkey
                return@async credentialManager.getCredential(
                    getCredRequest,
                    activity
                )
            }
            return getCredResponse.await()
        }
    }

}