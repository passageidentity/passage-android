package id.passage.android

import android.content.Intent
import androidx.browser.customtabs.CustomTabsIntent.SHARE_STATE_DEFAULT
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasDataString
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import junit.framework.TestCase.fail
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.containsString

@RunWith(AndroidJUnit4::class)
internal class SocialTests {

    private lateinit var passage: Passage

    companion object {
        const val appId = "ADFxfSU7wg3loThFZ9cig3SG"
        const val apiBaseUrl = "https://auth-uat.passage.dev/v1"
    }

    @Before
    fun setup() {
        Intents.init()
        activityRule?.scenario?.onActivity { activity ->
            activity?.let {
                passage = Passage(it, appId)
                passage.overrideBasePath(apiBaseUrl)
            }
        }
    }

    @After
    fun teardown() = runTest {
        Intents.release()
        val uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        // Simulate a back press to dismiss the Custom Chrome Tab
        uiDevice.pressBack()
    }

    @get:Rule
    var activityRule: ActivityScenarioRule<TestActivity?>? = ActivityScenarioRule(
        TestActivity::class.java
    )

    @Test
    fun testAuthorizeWith() = runTest {
        try {
            val expectedBasePath = "$apiBaseUrl/apps/${appId}/social/authorize"
            val expectedRedirectUri = "redirect_uri=https%3A%2F%2Ftry-uat.passage.dev"
            val expectedCodeChallengeMethod = "code_challenge_method=S256"
            val expectedConnectionType = "connection_type=github"
            val expectedState = "state="
            val expectedCodeChallenge = "code_challenge="
            passage.authorizeWith(PassageSocialConnection.github)
            intended(allOf(
                // Web browser is open
                hasAction(Intent.ACTION_VIEW),
                // Web browser is a Custom Chrome Tab
                hasExtra("androidx.browser.customtabs.extra.SHARE_STATE", SHARE_STATE_DEFAULT),
                // Web browser url has expected base path
                hasDataString(containsString(expectedBasePath)),
                // Web browser url contains expected query params and values
                hasDataString(containsString(expectedRedirectUri)),
                hasDataString(containsString(expectedCodeChallengeMethod)),
                hasDataString(containsString(expectedConnectionType)),
                hasDataString(containsString(expectedState)),
                hasDataString(containsString(expectedCodeChallenge)),
            ))
        } catch (e: Exception) {
            fail("Test failed due to unexpected exception: ${e.message}")
        }
    }

}
