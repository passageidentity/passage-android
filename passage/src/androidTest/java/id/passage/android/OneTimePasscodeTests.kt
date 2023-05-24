package id.passage.android

import MailosaurAPIClient
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import id.passage.android.exceptions.NewLoginOneTimePasscodeInvalidIdentifierException
import id.passage.android.exceptions.NewRegisterOneTimePasscodeInvalidIdentifierException
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class OneTimePasscodeTests {

    private lateinit var passage: Passage

    companion object {
        const val existingUserEmail = "authentigator+1684801767403@ncor7c1m.mailosaur.net"
    }

    @Before
    fun setup() {
        Passage.BASE_PATH = "https://auth-uat.passage.dev/v1"
        activityRule?.scenario?.onActivity { activity ->
            activity?.let {
                passage = Passage(it)
            }
        }
    }

    @After
    fun teardown() = runTest {
        passage.signOutCurrentUser()
    }

    @get:Rule
    var activityRule: ActivityScenarioRule<TestActivity?>? = ActivityScenarioRule(
        TestActivity::class.java
    )

    @Test
    fun testRegisterOTPValid() = runTest {
        val date = System.currentTimeMillis()
        val identifier = "authentigator+$date@passage.id"
        try {
            passage.newRegisterOneTimePasscode(identifier)
            assertThat(true).isTrue()
        } catch (_: Exception) {
            assertThat(false).isTrue()
        }
    }

    @Test
    fun testRegisterOTPNotValid() = runTest {
        val identifier = "INVALID_IDENTIFIER"
        try {
            passage.newRegisterOneTimePasscode(identifier)
            assertThat(false).isTrue()
        } catch (e: Exception) {
            var isExpectedException = false
            if (e is NewRegisterOneTimePasscodeInvalidIdentifierException) {
                isExpectedException = true
            }
            assertThat(isExpectedException).isTrue()
        }
    }

    @Test
    fun testActivateRegisterOTPValid() = runBlocking {
        val date = System.currentTimeMillis()
        val identifier = "authentigator+$date@${MailosaurAPIClient.serverId}.mailosaur.net"
        try {
            val otpId = passage.newRegisterOneTimePasscode(identifier).otpId
            delay(3000)
            val otp = MailosaurAPIClient.getMostRecentOneTimePasscode()
            passage.oneTimePasscodeActivate(otp, otpId)
            assertThat(true).isTrue()
        } catch (_: Exception) {
            assertThat(false).isTrue()
        }
    }

    @Test
    fun testLoginOTPValid() = runTest {
        val identifier = existingUserEmail
        try {
            passage.newLoginOneTimePasscode(identifier)
            assertThat(true).isTrue()
        } catch (_: Exception) {
            assertThat(false).isTrue()
        }
    }

    @Test
    fun testLoginOTPNotValid() = runTest {
        val identifier = "INVALID_IDENTIFIER"
        try {
            passage.newLoginOneTimePasscode(identifier)
            assertThat(false).isTrue()
        } catch (e: Exception) {
            var isExpectedException = false
            if (e is NewLoginOneTimePasscodeInvalidIdentifierException) {
                isExpectedException = true
            }
            assertThat(isExpectedException).isTrue()
        }
    }

    @Test
    fun testActivateLoginOTPValid() = runBlocking {
        val identifier = existingUserEmail
        try {
            val otpId = passage.newLoginOneTimePasscode(identifier).otpId
            delay(3000)
            val otp = MailosaurAPIClient.getMostRecentOneTimePasscode()
            passage.oneTimePasscodeActivate(otp, otpId)
            assertThat(true).isTrue()
        } catch (_: Exception) {
            assertThat(false).isTrue()
        }
    }

}
