package id.passage.android

import MailosaurAPIClient
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import id.passage.android.IntegrationTestConfig.Companion.API_BASE_URL
import id.passage.android.IntegrationTestConfig.Companion.APP_ID_OTP
import id.passage.android.IntegrationTestConfig.Companion.EXISTING_USER_EMAIL_OTP
import id.passage.android.IntegrationTestConfig.Companion.WAIT_TIME_MILLISECONDS
import id.passage.android.exceptions.PassageUserUnauthorizedException
import junit.framework.TestCase.fail
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class TokenStoreTests {
    private lateinit var passage: Passage

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
            passage.oneTimePasscode.activate(otp, otpId)
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
                val signedOutUser = passage.currentUser.userInfo()
                assertThat(signedOutUser).isNull()
            } catch (e: Exception) {
                fail("Test failed due to unexpected exception: ${e.message}")
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
}
