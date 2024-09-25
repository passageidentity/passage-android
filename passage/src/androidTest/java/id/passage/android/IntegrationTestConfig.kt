package id.passage.android

import id.passage.android.model.UserStatus
import id.passage.android.utils.PublicUserInfo

internal class IntegrationTestConfig {
    companion object {
        const val API_BASE_URL = "https://auth-uat.passage.dev/v1"
        const val APP_ID_OTP = "Ezbk6fSdx9pNQ7v7UbVEnzeC"
        const val APP_ID_OIDC = "2ZWhX75KpwKKVdr4gxiZph9m"
        const val APP_ID_MAGIC_LINK = "Pea2GdtBHN3esylK4ZRlF19U"
        const val WAIT_TIME_MILLISECONDS: Long = 8000
        const val EXISTING_USER_EMAIL_OTP = "authentigator+1716916054778@ncor7c1m.mailosaur.net"
        const val EXISTING_USER_EMAIL_MAGIC_LINK = "authentigator+1716572384858@ncor7c1m.mailosaur.net"
        const val DEACTIVATED_USER_EMAIL_MAGIC_LINK = "authentigator+1716778790434@ncor7c1m.mailosaur.net"
        const val AUTH_TOEKN = BuildConfig.OTP_TEST_USER_AUTH_TOKEN
        val CURRENT_USER =
            PublicUserInfo(
                email = "authentigator+1727293832645@ncor7c1m.mailosaur.net",
                emailVerified = true,
                id = "ZN2JTd6NqbTAvN45SbjUzqVn",
                phone = "",
                phoneVerified = false,
                status = UserStatus.active,
                userMetadata = null,
                webauthn = false,
                webauthnTypes = emptyList(),
            )
    }
}
