package id.passage.passageflex

import android.app.Activity
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import id.passage.android.passageflex.model.AuthenticatorAttachment
import id.passage.passageflex.exceptions.AuthenticateCancellationException
import id.passage.passageflex.exceptions.AuthenticateException
import id.passage.passageflex.exceptions.RegisterException
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class PassageFlexTest {

    private lateinit var testActivity: Activity

    @Before
    fun setup() {
        activityRule?.scenario?.onActivity { activity ->
            activity?.let {
                testActivity = it
            }
        }
    }

    @get:Rule
    var activityRule: ActivityScenarioRule<TestActivity?>? =
        ActivityScenarioRule(
            TestActivity::class.java,
        )

    @Test
    fun testGetAppId() {
        val appId = Utils.getAppId(testActivity)
        assertEquals("hknBjKc5jettbgwAQ4j9bnsu", appId)
    }

    @Test
    fun testPasskeyRegister() = runBlocking<Unit> {
        // What this test is expected to do:
        // - Successfully show that Credential Manager passkey registration view given a transaction id.
        // What this test is expected NOT to do:
        // - Successful biometric auth
        // - Webauthn finish
        // - Produce the "nonce" value expected from `PassagePasskeyAuthentication.register`
        try {
            // 1.
            // Get a transaction id for a new user:
            val appId = Utils.getAppId(testActivity)
            val date = System.currentTimeMillis()
            val email = "authentigator+$date@passage.id"
            val transactionId = FlexServerMock.getTransactionId(email, true, appId)
            // 2.
            // Call "register" on the UI thread so we can look for the "Continue" button that's a
            // part of the Credential Manager UI.
            launch(Dispatchers.IO) {
                try {
                    // 3.
                    // This will trigger the Credential Manager prompt. We don't expect this to
                    // return a nonce value, since we cannot finish webauthn flow in a test
                    // environment.
                    PassagePasskeyAuthentication.register(
                        transactionId = transactionId,
                        activity = testActivity,
                        authenticatorAttachment = AuthenticatorAttachment.platform,
                        apiBasePath = FlexTestConfig.API_BASE_URL
                    )
                } catch (e: RegisterException) {
                    // 5.
                    // Clicking "Continue" in the test environment should produce a validation
                    // exception. This is as far as we can get in a test environment, so we can
                    // pass the test in this case.
                    if (e.message == "The incoming request cannot be validated") {
                        // Test passes
                    } else {
                        TestCase.fail("Test failed due to unexpected exception: ${e.message}")
                    }

                }
            }
            // 4.
            // Use UI Automator to wait for and click the "Continue" button.
            val uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
            val continueButton = uiDevice.wait(Until.findObject(By.text("Continue")), 5000)
            assertTrue("The Continue button was not found", continueButton != null)
            continueButton.click()
            // Sometimes, a second continue button may appear
            val secondContinueButton = uiDevice.wait(Until.findObject(By.text("Continue")), 1000)
            secondContinueButton?.click()
        } catch (e: RegisterException) {
            TestCase.fail("Test failed due to unexpected exception: ${e.message}")
        }
    }

    @Test
    fun testPasskeyAuthenticate() = runBlocking<Unit> {
        // What this test is expected to do:
        // - Successfully show "Sign in another way" UI
        // What this test is expected NOT to do:
        // - Show that Credential Manager passkey view
        // - Successful biometric auth
        // - Webauthn finish
        // - Produce the "nonce" value expected from `PassagePasskeyAuthentication.register`
        try {
            // 1.
            // Call "authenticate" on the UI thread so we can look for the "Sign in another way"
            // prompt from Credential Manager.
            launch(Dispatchers.IO) {
                try {
                    // 2.
                    // This will trigger the "Sign in another way" prompt. We don't expect this to
                    // return a nonce value, since we cannot finish webauthn flow in a test
                    // environment, AND there are no registered passkeys on the test device.
                    PassagePasskeyAuthentication.authenticate(
                        activity = testActivity,
                        apiBasePath = FlexTestConfig.API_BASE_URL
                    )
                } catch (e: AuthenticateException) {
                    // 4.
                    // Clicking "Continue" in the test environment should produce a validation
                    // exception. This is as far as we can get in a test environment, so we can
                    // pass the test in this case.
                    if (e is AuthenticateCancellationException) {
                        // Test passes
                    } else {
                        TestCase.fail("Test failed due to unexpected exception: ${e.message}")
                    }

                }
            }
            // 3.
            // Use UI Automator to wait for "Sign in another way" prompt. Then emulate a "back"
            // button press to dismiss the prompt, and finish `PassagePasskeyAuthentication.authenticate`
            val uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
            val prompt = uiDevice.wait(Until.findObject(By.text("Sign in another way")), 5000)
            assertTrue("The prompt was not found", prompt != null)
            UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).pressBack()
        } catch (e: RegisterException) {
            if (e.message == "The incoming request cannot be validated") {
                // Test passes
            } else {
                TestCase.fail("Test failed due to unexpected exception: ${e.message}")
            }
        }
    }

}
