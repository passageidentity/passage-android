package id.passage.android

import id.passage.android.api.LoginAPI
import id.passage.android.api.OTPAPI
import id.passage.android.api.RegisterAPI
import id.passage.android.exceptions.OneTimePasscodeActivateException
import id.passage.android.exceptions.OneTimePasscodeLoginException
import id.passage.android.exceptions.OneTimePasscodeRegisterException
import id.passage.android.model.ActivateOneTimePasscodeRequest
import id.passage.android.model.AuthResult
import id.passage.android.model.LoginOneTimePasscodeRequest
import id.passage.android.model.RegisterOneTimePasscodeRequest
import id.passage.android.utils.OneTimePasscode
import okhttp3.OkHttpClient

class PassageOneTimePasscode(
    private val passageClient: OkHttpClient,
    private val tokenStore: PassageTokenStore,
) {
    /**
     * Register with One Time Passcode
     *
     * Create a user and send a registration email or SMS to the user. The user will receive an
     * email or text with a one time passcode to complete their registration.
     * @param identifier valid email or E164 phone number
     * @param language optional language string for localizing emails, if no language or an invalid language is provided the application default language will be used
     * @return OneTimePasscode
     * @throws OneTimePasscodeRegisterException
     */
    suspend fun register(
        identifier: String,
        language: String?,
    ): OneTimePasscode {
        val registerAPI = RegisterAPI(Passage.BASE_PATH, passageClient)
        val request = RegisterOneTimePasscodeRequest(identifier, language)
        val response =
            try {
                registerAPI.registerOneTimePasscode(Passage.appId, request)
            } catch (e: Exception) {
                throw OneTimePasscodeRegisterException.convert(e)
            }
        return response
    }

    /**
     * Login with One Time Passcode
     *
     * Send a login email or SMS to the user. The user will receive an email or text with a one time
     * passcode to complete their login.
     * @param identifier valid email or E164 phone number
     * @param language optional language string for localizing emails, if no language or an invalid language is provided the application default language will be used
     * @return OneTimePasscode
     * @throws OneTimePasscodeLoginException
     */
    suspend fun login(
        identifier: String,
        language: String?,
    ): OneTimePasscode {
        val loginAPI = LoginAPI(Passage.BASE_PATH, passageClient)
        val request = LoginOneTimePasscodeRequest(identifier, language)
        val response =
            try {
                loginAPI.loginOneTimePasscode(Passage.appId, request)
            } catch (e: Exception) {
                throw OneTimePasscodeLoginException.convert(e)
            }
        return response
    }

    /**
     * Authenticate One Time Passcode
     *
     * Authenticate a one time passcode for a user. This endpoint checks that the one time passcode
     * is valid, then returns an authentication token for the user.
     * @param otp The one time passcode
     * @param otpId The OTP id
     * @return PassageAuthResult
     * @throws OneTimePasscodeActivateException
     */
    suspend fun activate(
        otp: String,
        otpId: String,
    ): AuthResult {
        val otpAPI = OTPAPI(Passage.BASE_PATH, passageClient)
        val request = ActivateOneTimePasscodeRequest(otp, otpId)
        val response =
            try {
                otpAPI.activateOneTimePasscode(Passage.appId, request)
            } catch (e: Exception) {
                throw OneTimePasscodeActivateException.convert(e)
            }
        val otpAuthResult = response.authResult
        // NOTE: The OpenAPI codegen produces an `IdentityAuthResult` for passkey and magic link
        // responses, but produces an `AuthResult` for OTP. They have the same shape, so we
        // created a `PassageAuthResult` alias for `IdentityAuthResult`, and return that alias
        // from all of the methods that produce an auth result. This should be temporary, until
        // the Passage API returns just `AuthResult` for all.
        val authResult =
            AuthResult(
                authToken = otpAuthResult.authToken,
                redirectUrl = otpAuthResult.redirectUrl,
                refreshToken = otpAuthResult.refreshToken,
                refreshTokenExpiration = otpAuthResult.refreshTokenExpiration,
            )
        tokenStore.setTokens(authResult)
        return authResult
    }
}
