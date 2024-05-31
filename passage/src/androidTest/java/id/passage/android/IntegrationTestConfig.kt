package id.passage.android

import id.passage.android.model.User
import id.passage.android.model.UserStatus

internal class IntegrationTestConfig {
    companion object {
        const val API_BASE_URL = "https://auth-uat.passage.dev/v1"
        const val APP_ID_OTP = "Ezbk6fSdx9pNQ7v7UbVEnzeC"
        const val APP_ID_MAGIC_LINK = "Pea2GdtBHN3esylK4ZRlF19U"
        const val WAIT_TIME_MILLISECONDS: Long = 8000
        const val EXISTING_USER_EMAIL_OTP = "authentigator+1716916054778@ncor7c1m.mailosaur.net"
        const val EXISTING_USER_EMAIL_MAGIC_LINK = "authentigator+1716572384858@ncor7c1m.mailosaur.net"
        const val DEACTIVATED_USER_EMAIL_MAGIC_LINK = "authentigator+1716778790434@ncor7c1m.mailosaur.net"
        const val AUTH_TOEKN = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImV3R0xmZ2lkZWdRSXc3ZUdJQlBhSE94YiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJodHRwczovL3RyeS11YXQucGFzc2FnZS5kZXYiLCJleHAiOjE3MzI3MjQ1NzAsImlhdCI6MTcxNzE3MjU3MCwiaXNzIjoiaHR0cHM6Ly9hdXRoLXVhdC5wYXNzYWdlLmRldi92MS9hcHBzL0V6Yms2ZlNkeDlwTlE3djdVYlZFbnplQyIsIm5iZiI6MTcxNzE3MjU2NSwic3ViIjoiQ2Y3MFprd24xeXducHV1YTFaRkY3MTdHIn0.Bcfpuuv82FTNifdWHJVCRDDcP-7YAEmDKH0v_Hxsoi4JUwd1N-m4alznNBg4tCZAOaBto5IYkwLXfpaE7yZ_E4VFJ74arXzeZyh6CwQIItGlwWQUQIMGkiX6HZlsq4dSAoaQtmNOC395egR_sBAhlbvcClNFP850qJ7A5d-KNvi2VfzLuEYaDwhQjJMmBhcs1GRGnhRZihBpiLk8Yt21ColoRNenwAH0830e3zMldEYm6hhJZprwNyAsKqQVf-Ot9-wwJzZ-_N9gRnVy6stYOYiVIUtrhp0h_ZvGbhDSWyM4DCGcAPNivQI2_0ILY9xy_GVzmXLx8UUfAdrZJ-R2bA"
        val CURRENT_USER =
            PassageUser.convertToPassageUser(
                User(
                    email = "authentigator+1716916054778@ncor7c1m.mailosaur.net",
                    emailVerified = true,
                    id = "Cf70Zkwn1ywnpuua1ZFF717G",
                    phone = "",
                    phoneVerified = false,
                    status = UserStatus.active,
                    userMetadata = null,
                    webauthn = false,
                    webauthnTypes = emptyList(),
                ),
            )
    }
}
