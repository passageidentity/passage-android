package id.passage.android

import androidx.test.espresso.intent.Intents
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import com.google.common.truth.Truth.assertThat
import id.passage.android.IntegrationTestConfig.Companion.API_BASE_URL
import id.passage.android.IntegrationTestConfig.Companion.APP_ID_OIDC
import id.passage.android.IntegrationTestConfig.Companion.EXISTING_USER_EMAIL_OTP
import id.passage.android.exceptions.HostedAuthorizationError
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
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
internal class HostedTests {
    private lateinit var passage: Passage

    @Before
    fun setup() {
        Intents.init()
        activityRule?.scenario?.onActivity { activity ->
            activity?.let {
                passage = Passage(it, APP_ID_OIDC)
                passage.overrideBasePath(API_BASE_URL)
            }
        }
    }

    @After
    fun teardown() =
        runTest {
            Intents.release()
        }

    @get:Rule
    var activityRule: ActivityScenarioRule<TestActivity?>? =
        ActivityScenarioRule(
            TestActivity::class.java,
        )

    @Test
    fun testHostedAuthStart(): Unit =
        runBlocking {
            try {
                hostedAuthLogin()
                val user = passage.getCurrentUser()
                assertNotNull(user)
            } catch (e: Exception) {
                fail("Test failed due to unexpected exception: ${e.message}")
            } finally {
                UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).pressBack()
            }
        }

    private suspend fun hostedAuthLogin() {
        passage.hostedAuthStart()
        delay(IntegrationTestConfig.WAIT_TIME_MILLISECONDS)
        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        val loginField = device.findObject(UiSelector().className("android.widget.EditText").instance(0))
        loginField.setText(EXISTING_USER_EMAIL_OTP)
        val continueButton = device.findObject(UiSelector().className("android.widget.Button").text("Continue"))
        continueButton.click()
        delay(IntegrationTestConfig.WAIT_TIME_MILLISECONDS)
        val otpCode = MailosaurAPIClient.getMostRecentOneTimePasscode() // Replace with the actual OTP code

        otpCode.forEachIndexed { index, char ->
            val otpField = device.findObject(UiSelector().className("android.widget.EditText").instance(index))
            otpField.click()
            otpField.setText(char.toString())
            Thread.sleep(200)
        }

        delay(IntegrationTestConfig.WAIT_TIME_MILLISECONDS)
        val skipButton = device.findObject(UiSelector().className("android.widget.Button").text("Skip"))
        skipButton.click()
    }

    @Test
    fun testHostedLogout(): Unit =
        runBlocking {
            try {
                hostedAuthLogin()
                passage.hostedAuthLogout()
                val user = passage.getCurrentUser()
                assertNull(user)
            } catch (e: Exception) {
                fail("Test failed due to unexpected exception: ${e.message}")
            }
        }

    @Test
    fun testFinishAuthorizationInvalidRequest() =
        runTest {
            try {
                val invalidAuthCode = "INVALID_AUTH_CODE"
                passage.hostedAuthFinish(invalidAuthCode, "", "")
                fail("Test should throw FinishOIDCAuthenticationInvalidRequestException")
            } catch (e: Exception) {
                assertThat(e is HostedAuthorizationError)
            }
        }
}
