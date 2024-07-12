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
        println("Device instance obtained.")

        // Handle the Chrome welcome screen
        val addAccountButton = device.findObject(UiSelector().className("android.widget.Button").text("Add account to device"))
        val useWithoutAccountButton = device.findObject(UiSelector().className("android.widget.Button").text("Use without an account"))

        if (addAccountButton.exists()) {
            println("Add account button found, clicking 'Use without an account'.")
            useWithoutAccountButton.click()
            delay(IntegrationTestConfig.WAIT_TIME_MILLISECONDS)
        } else {
            println("Add account button not found.")
        }

        // Check if the user is on the login screen with the email field
        val emailField = device.findObject(UiSelector().className("android.widget.EditText").instance(0))
        if (emailField.exists()) {
            println("Email field found, entering email.")
            emailField.setText(EXISTING_USER_EMAIL_OTP)
            val nextButton = device.findObject(UiSelector().className("android.widget.Button").text("Continue"))
            nextButton.click()
        } else {
            println("Email field not found, checking for 'Continue' button.")
            // User is already logged in, click Continue button
            val continueButton = device.findObject(UiSelector().className("android.widget.Button").text("Continue"))
            if (continueButton.exists()) {
                println("Continue button found, clicking it.")
                continueButton.click()
            } else {
                println("Continue button not found.")
            }
        }

        delay(IntegrationTestConfig.WAIT_TIME_MILLISECONDS)

        val otpCode = MailosaurAPIClient.getMostRecentOneTimePasscode() // Replace with the actual OTP code
        println("OTP code retrieved: $otpCode")

        otpCode.forEachIndexed { index, char ->
            val otpField = device.findObject(UiSelector().className("android.widget.EditText").instance(index))
            if (otpField.exists()) {
                println("OTP field $index found, entering OTP character.")
                otpField.click()
                otpField.setText(char.toString())
                Thread.sleep(200)
            } else {
                println("OTP field $index not found.")
            }
        }

        delay(IntegrationTestConfig.WAIT_TIME_MILLISECONDS)

        val skipButton = device.findObject(UiSelector().className("android.widget.Button").text("Skip"))
        if (skipButton.exists()) {
            println("Skip button found, clicking it.")
            skipButton.click()
            delay(IntegrationTestConfig.WAIT_TIME_MILLISECONDS)
        } else {
            println("Skip button not found.")
        }
    }


    @Test
    fun testHostedLogout(): Unit =
        runBlocking {
            try {
                val alreadyAuthenticated = passage.getCurrentUser()
                if (alreadyAuthenticated == null) hostedAuthLogin()
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
