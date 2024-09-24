package id.passage.android.utils

import android.app.Activity
import androidx.credentials.CreateCredentialResponse
import androidx.credentials.CreatePublicKeyCredentialRequest
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.GetPublicKeyCredentialOption
import androidx.credentials.exceptions.CreateCredentialException
import androidx.credentials.exceptions.GetCredentialException
import com.squareup.moshi.Moshi
import id.passage.android.exceptions.*
import id.passage.android.exceptions.CredentialParsingException.Companion.CHALLENGE_MISSING
import id.passage.android.exceptions.CredentialParsingException.Companion.CHALLENGE_PARSING_FAILED
import id.passage.android.exceptions.CredentialParsingException.Companion.CREDENTIAL_PARSING_FAILED
import id.passage.android.model.CredentialAssertionChallenge
import id.passage.android.model.CredentialAssertionResponse
import id.passage.android.model.CredentialAssertionResponseJsonAdapter
import id.passage.android.model.CredentialCreationChallenge
import id.passage.android.model.CredentialCreationPublicKeyJsonAdapter
import id.passage.android.model.CredentialCreationResponse
import id.passage.android.model.CredentialCreationResponseJsonAdapter
import id.passage.android.model.ProtocolCredentialAssertionPublicKey
import id.passage.android.model.ProtocolCredentialAssertionPublicKeyJsonAdapter

class PasskeyUtils {
    companion object {
        private const val REGISTRATION_RESPONSE_BUNDLE_KEY = "androidx.credentials.BUNDLE_KEY_REGISTRATION_RESPONSE_JSON"
        private const val AUTH_RESPONSE_BUNDLE_KEY = "androidx.credentials.BUNDLE_KEY_AUTHENTICATION_RESPONSE_JSON"

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
        public suspend fun createPasskey(
            requestJson: String,
            activity: Activity,
        ): CreateCredentialResponse {
            val credentialManager = CredentialManager.create(activity)
            val publicKeyCredRequest = CreatePublicKeyCredentialRequest(requestJson)
            // Show the user Credential Manager with option to create a Passkey
            return credentialManager.createCredential(activity, publicKeyCredRequest)
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
        public suspend fun getPasskey(
            requestJson: String,
            activity: Activity,
        ): GetCredentialResponse {
            val credentialManager = CredentialManager.create(activity)
            val getCredOption = GetPublicKeyCredentialOption(requestJson)
            val getCredRequest = GetCredentialRequest(listOf(getCredOption))
            // Show the user Credential Manager with option to login with a Passkey
            return credentialManager.getCredential(activity, getCredRequest)
        }

        /**
         * Get Create Credential Options JSON
         *
         * Extract the public key from Passage webauthnStart challenge and transform into a JSON string
         * @param challenge The webauthn start challenge sent from Passage
         * @return String
         * @throws CredentialParsingException
         */
        internal fun getCreateCredentialOptionsJson(challenge: CredentialCreationChallenge?): String {
            val publicKey =
                challenge?.challenge?.publicKey
                    ?: throw CredentialParsingException(CHALLENGE_MISSING)
            val moshi = Moshi.Builder().build()
            val adapter = CredentialCreationPublicKeyJsonAdapter(moshi)
            return adapter.toJson(publicKey)
                ?: throw CredentialParsingException(CHALLENGE_PARSING_FAILED)
        }

        /**
         * Get Create Credential Handshake Response
         *
         * Extract the CreateCredentialResponse from Credential Manager response data and transform
         * into the Passage handshake response class used to finish webauthn registration.
         * @param createCredentialResponse The response from Android's Credential Manager
         * @return ProtocolCredentialCreationResponse
         * @throws CredentialParsingException
         */
        internal fun getCreateCredentialHandshakeResponse(createCredentialResponse: CreateCredentialResponse): CredentialCreationResponse {
            val handshakeResponseJson =
                createCredentialResponse.data.getString(REGISTRATION_RESPONSE_BUNDLE_KEY).toString()
            val moshi = Moshi.Builder().build()
            val handshakeResponseAdapter = CredentialCreationResponseJsonAdapter(moshi)
            return handshakeResponseAdapter.fromJson(handshakeResponseJson)
                ?: throw CredentialParsingException(CREDENTIAL_PARSING_FAILED)
        }

        /**
         * Get Credential Options JSON
         *
         * Extract the public key from Passage webauthnStart challenge and convert into a JSON string
         * @param challenge The webauthn start challenge sent from Passage
         * @return String
         * @throws CredentialParsingException
         */
        internal fun getCredentialOptionsJson(challenge: CredentialAssertionChallenge): String {
            val credOptions = challenge.challenge.publicKey
            val moshi = Moshi.Builder().build()
            val credOptionsAdapter = ProtocolCredentialAssertionPublicKeyJsonAdapter(moshi)
            // Passage API bug: Login API frequently returns challenge with non-url-safe characters
            // "+" and "/" that cause "Bad Base 64" exception to be thrown by the credential manager.
            val modifiedChallenge = credOptions.challenge.replace('+', '-').replace('/', '_')
            val modifiedCredOptions =
                ProtocolCredentialAssertionPublicKey(
                    allowCredentials = credOptions.allowCredentials,
                    challenge = modifiedChallenge,
                    extensions = credOptions.extensions,
                    rpId = credOptions.rpId,
                    timeout = credOptions.timeout,
                    userVerification = credOptions.userVerification,
                )
            return credOptionsAdapter.toJson(modifiedCredOptions)
                ?: throw CredentialParsingException(CHALLENGE_PARSING_FAILED)
        }

        /**
         * Get Credential Handshake Response
         *
         * Extract the CredentialResponse from Credential Manager response data and transform into
         * the Passage handshake response class used to finish webauthn login.
         * @param credentialResponse The response from Android's Credential Manager
         * @return ProtocolCredentialAssertionResponse
         * @throws CredentialParsingException
         */
        internal fun getCredentialHandshakeResponse(credentialResponse: GetCredentialResponse): CredentialAssertionResponse {
            val handshakeResponseJson =
                credentialResponse.credential.data
                    .getString(AUTH_RESPONSE_BUNDLE_KEY)
                    .toString()
            val moshi = Moshi.Builder().build()
            val handshakeResponseAdapter = CredentialAssertionResponseJsonAdapter(moshi)
            return handshakeResponseAdapter.fromJson(handshakeResponseJson)
                ?: throw CredentialParsingException(CREDENTIAL_PARSING_FAILED)
        }
    }
}
