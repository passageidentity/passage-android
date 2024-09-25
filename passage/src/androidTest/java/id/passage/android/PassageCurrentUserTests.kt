package id.passage.android

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import id.passage.android.IntegrationTestConfig.Companion.AUTH_TOEKN
import id.passage.android.IntegrationTestConfig.Companion.CURRENT_USER
import id.passage.android.exceptions.UserInfoUnauthorizedException
import id.passage.android.model.AuthResult
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import junit.framework.TestCase.fail
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class PassageCurrentUserTests {
    private lateinit var passage: Passage

    @Before
    fun setUp() {
        activityRule?.scenario?.onActivity { activity ->
            activity?.let {
                passage = Passage(it, IntegrationTestConfig.APP_ID_OTP)
            }
        }
    }

    @get:Rule
    var activityRule: ActivityScenarioRule<TestActivity?>? =
        ActivityScenarioRule(
            TestActivity::class.java,
        )

    @Test
    fun testCurrentUser() =
        runTest {
            // make sure we have an authToken.
            assertNotEquals(AUTH_TOEKN, "")
            try {
                passage.tokenStore.setTokens(AuthResult(AUTH_TOEKN, ""))
                val response = passage.currentUser.userInfo()
                assertEquals(response.id, CURRENT_USER.id)
                assertEquals(response.status, CURRENT_USER.status)
                assertEquals(response.email, CURRENT_USER.email)
                assertEquals(response.emailVerified, CURRENT_USER.emailVerified)
                assertEquals(response.phone, CURRENT_USER.phone)
                assertEquals(response.phoneVerified, CURRENT_USER.phoneVerified)
                assertEquals(response.webauthn, CURRENT_USER.webauthn)
            } catch (e: Exception) {
                fail("Test failed due to unexpected exception: ${e.message}")
            }
        }

    @Test
    fun testCurrentUserNotAuthorized() =
        runBlocking {
            try {
                passage.tokenStore.setTokens(AuthResult("invalid", ""))
                passage.currentUser.userInfo()
                fail("Test should throw UserInfoUnauthorizedException")
            } catch (e: Exception) {
                assertTrue(e is UserInfoUnauthorizedException)
            }
        }
}
