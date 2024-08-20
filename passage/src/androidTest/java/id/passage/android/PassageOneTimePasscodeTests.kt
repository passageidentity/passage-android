package id.passage.android

import MailosaurAPIClient
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import id.passage.android.IntegrationTestConfig.Companion.API_BASE_URL
import id.passage.android.IntegrationTestConfig.Companion.APP_ID_OTP
import id.passage.android.IntegrationTestConfig.Companion.EXISTING_USER_EMAIL_OTP
import id.passage.android.IntegrationTestConfig.Companion.WAIT_TIME_MILLISECONDS
import id.passage.android.exceptions.OneTimePasscodeLoginException
import id.passage.android.exceptions.OneTimePasscodeRegisterException
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
internal class PassageOneTimePasscodeTests {
    private lateinit var passage: Passage

    @Before
    fun setup() {
        activityRule?.scenario?.onActivity { activity ->
            activity?.let {
                passage = Passage(it, APP_ID_OTP)
                passage.overrideBasePath(API_BASE_URL)
            }
        }
    }

    @After
    fun teardown() =
        runTest {
            passage.currentUser.logout()
        }

    @get:Rule
    var activityRule: ActivityScenarioRule<TestActivity?>? =
        ActivityScenarioRule(
            TestActivity::class.java,
        )

    @Test
    fun testRegisterOTPValid() =
        runTest {
            val date = System.currentTimeMillis()
            val identifier = "authentigator+$date@passage.id"
            try {
                passage.oneTimePasscode.register(identifier)
            } catch (e: Exception) {
                fail("Test failed due to unexpected exception: ${e.message}")
            }
        }

    @Test
    fun testRegisterOTPNotValid() =
        runTest {
            val identifier = "INVALID_IDENTIFIER"
            try {
                passage.oneTimePasscode.register(identifier)
                fail("Test should throw NewRegisterOneTimePasscodeInvalidIdentifierException")
            } catch (e: Exception) {
                assertThat(e is OneTimePasscodeRegisterException)
            }
        }

    @Test
    fun testActivateRegisterOTPValid(): Unit =
        runBlocking {
            val date = System.currentTimeMillis()
            val identifier = "authentigator+$date@${MailosaurAPIClient.serverId}.mailosaur.net"
            try {
                val otpId = passage.oneTimePasscode.register(identifier).otpId
                delay(WAIT_TIME_MILLISECONDS)
                val otp = MailosaurAPIClient.getMostRecentOneTimePasscode()
                passage.oneTimePasscode.activate(otp, otpId)
            } catch (e: Exception) {
                fail("Test failed due to unexpected exception: ${e.message}")
            }
        }

    @Test
    fun testLoginOTPValid() =
        runTest {
            val identifier = EXISTING_USER_EMAIL_OTP
            try {
                passage.oneTimePasscode.login(identifier)
            } catch (e: Exception) {
                fail("Test failed due to unexpected exception: ${e.message}")
            }
        }

    @Test
    fun testLoginOTPNotValid() =
        runTest {
            val identifier = "INVALID_IDENTIFIER"
            try {
                passage.oneTimePasscode.login(identifier)
                fail("Test should throw NewLoginOneTimePasscodeInvalidIdentifierException")
            } catch (e: Exception) {
                assertThat(e is OneTimePasscodeLoginException)
            }
        }

    @Test
    fun testActivateLoginOTPValid(): Unit =
        runBlocking {
            val identifier = EXISTING_USER_EMAIL_OTP
            try {
                val otpId = passage.oneTimePasscode.login(identifier).otpId
                delay(WAIT_TIME_MILLISECONDS)
                val otp = MailosaurAPIClient.getMostRecentOneTimePasscode()
                passage.oneTimePasscode.activate(otp, otpId)
            } catch (e: Exception) {
                fail("Test failed due to unexpected exception: ${e.message}")
            }
        }
}
