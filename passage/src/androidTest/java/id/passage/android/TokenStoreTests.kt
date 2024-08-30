package id.passage.android

import MailosaurAPIClient
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import id.passage.android.IntegrationTestConfig.Companion.API_BASE_URL
import id.passage.android.IntegrationTestConfig.Companion.APP_ID_OTP
import id.passage.android.IntegrationTestConfig.Companion.EXISTING_USER_EMAIL_OTP
import id.passage.android.IntegrationTestConfig.Companion.WAIT_TIME_MILLISECONDS
import id.passage.android.exceptions.PassageTokenException
import id.passage.android.exceptions.PassageUserUnauthorizedException
import id.passage.android.exceptions.UserInfoUnauthorizedException
import id.passage.android.model.AuthResult
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.fail
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class TokenStoreTests {
    private lateinit var passage: Passage
    private var refreshToken = ""

    @Before
    fun setup(): Unit =
        runBlocking {
            activityRule?.scenario?.onActivity { activity ->
                activity?.let {
                    passage = Passage(it, APP_ID_OTP)
                    passage.overrideBasePath(API_BASE_URL)
                }
            }
            // Log in user
            val otpId = passage.oneTimePasscode.login(EXISTING_USER_EMAIL_OTP).otpId
            delay(WAIT_TIME_MILLISECONDS)
            val otp = MailosaurAPIClient.getMostRecentOneTimePasscode()
            val authResult = passage.oneTimePasscode.activate(otp, otpId)
            refreshToken = authResult.authToken
        }

    @After
    fun teardown(): Unit =
        runBlocking {
            passage.currentUser.logout()
        }

    @get:Rule
    var activityRule: ActivityScenarioRule<TestActivity?>? =
        ActivityScenarioRule(
            TestActivity::class.java,
        )

    @Test
    fun testCurrentUser_isNotNull() =
        runTest {
            try {
                val currentUser = passage.currentUser.userInfo()
                assertThat(currentUser).isNotNull()
            } catch (e: Exception) {
                fail("Test failed due to unexpected exception: ${e.message}")
            }
        }

    @Test
    fun testCurrentUserAfterSignOut_isNull() =
        runTest {
            try {
                passage.currentUser.logout()
                passage.currentUser.userInfo()
                fail("Test should throw UserInfoUnauthorizedException")
            } catch (e: Exception) {
                assertTrue(e is UserInfoUnauthorizedException)
            }
        }

    @Test
    fun authToken_isNotNull() =
        runTest {
            try {
                val authToken = passage.tokenStore.authToken
                assertThat(authToken).isNotNull()
            } catch (e: Exception) {
                fail("Test failed due to unexpected exception: ${e.message}")
            }
        }

    @Test
    fun authTokenAfterSignOut_isNull() =
        runTest {
            try {
                passage.currentUser.logout()
                val authToken = passage.tokenStore.authToken
                assertThat(authToken).isNull()
            } catch (e: Exception) {
                fail("Test failed due to unexpected exception: ${e.message}")
            }
        }

    @Test
    fun authTokenThrowsErrorAfterRevoke() =
        runTest {
            try {
                val user = passage.currentUser.userInfo()
                passage.tokenStore.clearAndRevokeTokens()
                passage.currentUser.passkeys()
                fail("Test should throw PassageUserUnauthorizedException")
            } catch (e: Exception) {
                assertThat(e is PassageUserUnauthorizedException)
            }
        }

    @Test
    fun getValidAuthTokenWithValidToken() =
        runTest {
            val result = passage.tokenStore.getValidAuthToken()
            assertNotNull(result)
        }

    @Test
    fun getValidAuthTokenWithInvalidToken() =
        runTest {
            try {
                passage.tokenStore.setTokens(AuthResult("invalid", ""))
                passage.tokenStore.getValidAuthToken()
                fail("Test should throw PassageTokenException")
            } catch (e: Exception) {
                assertThat(e is PassageTokenException)
            }
        }

    @Test
    fun isAuthTokenValidWithValidToken() {
        val validToken = IntegrationTestConfig.AUTH_TOEKN
        val result = passage.tokenStore.isAuthTokenValid(validToken)
        assertThat(result).isTrue()
    }

    @Test
    fun isAuthTokenValidWithInvalidToken() {
        val invalidToken = "invalidAuthToken"
        val result = passage.tokenStore.isAuthTokenValid(invalidToken)
        assertThat(result).isFalse()
    }

    @Test
    fun revokedWithValidToken() =
        runTest {
            val validRefreshToken = refreshToken
            passage.tokenStore.revokeRefreshToken(validRefreshToken)
            // should not throw any error
        }

    @Test
    fun refreshWithInvalidToken(): Unit =
        runBlocking {
            try {
                val invalidRefreshToken = "invalid"
                val authResult = passage.tokenStore.refreshAuthToken(invalidRefreshToken)
                fail("Test should throw PassageTokenException")
            } catch (e: Exception) {
                assertThat(e is PassageTokenException)
            }
        }
}
