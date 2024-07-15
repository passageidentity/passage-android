package id.passage.android

import android.content.Intent
import androidx.browser.customtabs.CustomTabsIntent.SHARE_STATE_DEFAULT
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasDataString
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import com.google.common.truth.Truth.assertThat
import id.passage.android.IntegrationTestConfig.Companion.API_BASE_URL
import id.passage.android.IntegrationTestConfig.Companion.APP_ID_OIDC
import id.passage.android.exceptions.HostedAuthorizationError
import junit.framework.TestCase.fail
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.containsString
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
    fun testAuthorizeWith() =
        runTest {
            try {
                val expectedCodeChallengeMethod = "code_challenge_method=S256"
                val expectedState = "state="
                val expectedCodeChallenge = "code_challenge="

                passage.hostedAuthStart()

                intended(
                    allOf(
                        // Web browser is open
                        hasAction(Intent.ACTION_VIEW),
                        // Web browser is a Custom Chrome Tab
                        hasExtra("androidx.browser.customtabs.extra.SHARE_STATE", SHARE_STATE_DEFAULT),
                        hasDataString(containsString(expectedCodeChallengeMethod)),
                        hasDataString(containsString(expectedState)),
                        hasDataString(containsString(expectedCodeChallenge)),
                    ),
                )
            } catch (e: Exception) {
                fail("Test failed due to unexpected exception: ${e.message}")
            } finally {
                // Simulate a back press to dismiss the Custom Chrome Tab
                UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).pressBack()
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
