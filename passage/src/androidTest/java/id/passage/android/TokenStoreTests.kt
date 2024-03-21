package id.passage.android

import MailosaurAPIClient
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import id.passage.android.IntegrationTestConfig.Companion.apiBaseUrl
import id.passage.android.IntegrationTestConfig.Companion.appId
import id.passage.android.IntegrationTestConfig.Companion.emailWaitTimeMilliseconds
import id.passage.android.IntegrationTestConfig.Companion.existingUserEmail
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
                    passage = Passage(it, appId)
                    passage.overrideBasePath(apiBaseUrl)
                }
            }
            // Log in user
            val otpId = passage.newLoginOneTimePasscode(existingUserEmail).otpId
            delay(emailWaitTimeMilliseconds)
            val otp = MailosaurAPIClient.getMostRecentOneTimePasscode()
            passage.oneTimePasscodeActivate(otp, otpId)
        }

    @After
    fun teardown(): Unit =
        runBlocking {
            passage.signOutCurrentUser()
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
                val currentUser = passage.getCurrentUser()
                assertThat(currentUser).isNotNull()
            } catch (e: Exception) {
                fail("Test failed due to unexpected exception: ${e.message}")
            }
        }

    @Test
    fun testCurrentUserAfterSignOut_isNull() =
        runTest {
            try {
                passage.signOutCurrentUser()
                val signedOutUser = passage.getCurrentUser()
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
                passage.signOutCurrentUser()
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
                val user = passage.getCurrentUser()
                passage.tokenStore.clearAndRevokeTokens()
                user?.listDevicePasskeys()
                fail("Test should throw PassageUserUnauthorizedException")
            } catch (e: Exception) {
                assertThat(e is PassageUserUnauthorizedException)
            }
        }
}
