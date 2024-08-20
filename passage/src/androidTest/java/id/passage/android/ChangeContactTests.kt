package id.passage.android

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import id.passage.android.IntegrationTestConfig.Companion.AUTH_TOEKN
import id.passage.android.exceptions.PassageUserRequestException
import id.passage.android.exceptions.PassageUserUnauthorizedException
import id.passage.android.model.AuthResult
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import junit.framework.TestCase.fail
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class ChangeContactTests {
    private lateinit var passage: Passage

    @Before
    fun setUp() {
        activityRule?.scenario?.onActivity { activity ->
            activity?.let {
                passage = Passage(it, IntegrationTestConfig.APP_ID_OTP)
                passage.overrideBasePath(IntegrationTestConfig.API_BASE_URL)
            }
        }
    }

    @get:Rule
    var activityRule: ActivityScenarioRule<TestActivity?>? =
        ActivityScenarioRule(
            TestActivity::class.java,
        )

    @Test
    fun testChangeEmail() =
        runBlocking {
            // make sure we have an authToken.
            assertNotEquals(AUTH_TOEKN, "")
            try {
                passage.tokenStore.setTokens(AuthResult(AUTH_TOEKN, ""))
                val date = System.currentTimeMillis()
                val identifier = "authentigator+$date@passage.id"
                val response = passage.currentUser.changeEmail(identifier)
                assertNotNull(response.id)
            } catch (e: Exception) {
                fail("Test failed due to unexpected exception: ${e.message}")
            }
        }

    @Test
    fun testChangeEmailUnAuthed() =
        runBlocking {
            try {
                passage.tokenStore.setTokens(AuthResult("invalid", ""))
                val date = System.currentTimeMillis()
                val identifier = "authentigator+$date@passage.id"
                passage.currentUser.changeEmail(identifier)
                fail("Test should throw PassageUserUnauthorizedException")
            } catch (e: Exception) {
                assertTrue(e is PassageUserUnauthorizedException)
            }
        }

    @Test
    fun testChangePhone() =
        runBlocking {
            // make sure we have an authToken.
            assertNotEquals("", AUTH_TOEKN)
            try {
                passage.tokenStore.setTokens(AuthResult(AUTH_TOEKN, ""))
                val response = passage.currentUser.changePhone("+14155552671")
                assertNotNull(response.id)
            } catch (e: Exception) {
                fail("Test failed due to unexpected exception: ${e.message}")
            }
        }

    @Test
    fun testChangePhoneInvalid() =
        runBlocking {
            // make sure we have an authToken.
            assertNotEquals("", AUTH_TOEKN)
            try {
                passage.tokenStore.setTokens(AuthResult(AUTH_TOEKN, ""))
                val response = passage.currentUser.changePhone("444")
                assertNotNull(response.id)
                fail("Test should throw PassageUserRequestException")
            } catch (e: Exception) {
                assertTrue(e is PassageUserRequestException)
            }
        }

    @Test
    fun testChangePhoneUnAuthed() =
        runBlocking {
            try {
                passage.tokenStore.setTokens(AuthResult("invalid", ""))
                passage.currentUser.changePhone("+14155552671")
                fail("Test should throw PassageUserUnauthorizedException")
            } catch (e: Exception) {
                assertTrue(e is PassageUserUnauthorizedException)
            }
        }
}
