package id.passage.android

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import id.passage.android.IntegrationTestConfig.Companion.AUTH_TOEKN
import id.passage.android.IntegrationTestConfig.Companion.CURRENT_USER
import id.passage.android.exceptions.PassageUserRequestException
import id.passage.android.exceptions.PassageUserUnauthorizedException
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
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
                Passage.setAuthToken(AUTH_TOEKN)
                val date = System.currentTimeMillis()
                val identifier = "authentigator+$date@passage.id"
                val response = CURRENT_USER.changeEmail(identifier)
                assertNotNull(response.id)
            } catch (e: Exception) {
                assertTrue(false)
            }
        }

    @Test
    fun testChangeEmailUnAuthed() =
        runBlocking {
            try {
                Passage.setAuthToken("")
                val date = System.currentTimeMillis()
                val identifier = "authentigator+$date@passage.id"
                CURRENT_USER.changeEmail(identifier)
                assertTrue(false)
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
                Passage.setAuthToken(AUTH_TOEKN)
                val response = CURRENT_USER.changePhone("+14155552671")
                assertNotNull(response.id)
            } catch (e: Exception) {
                assertTrue(false)
            }
        }

    @Test
    fun testChangePhoneInvalid() =
        runBlocking {
            // make sure we have an authToken.
            assertNotEquals("", AUTH_TOEKN)
            try {
                Passage.setAuthToken(AUTH_TOEKN)
                val response = CURRENT_USER.changePhone("444")
                assertNotNull(response.id)
                assertTrue(false)
            } catch (e: Exception) {
                assertTrue(e is PassageUserRequestException)
            }
        }

    @Test
    fun testChangePhoneUnAuthed() =
        runBlocking {
            try {
                Passage.setAuthToken("")
                CURRENT_USER.changePhone("+14155552671")
                assertTrue(false)
            } catch (e: Exception) {
                assertTrue(e is PassageUserUnauthorizedException)
            }
        }
}
