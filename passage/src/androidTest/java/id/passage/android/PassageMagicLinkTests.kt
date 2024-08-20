package id.passage.android

import MailosaurAPIClient
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import id.passage.android.IntegrationTestConfig.Companion.API_BASE_URL
import id.passage.android.IntegrationTestConfig.Companion.APP_ID_MAGIC_LINK
import id.passage.android.IntegrationTestConfig.Companion.DEACTIVATED_USER_EMAIL_MAGIC_LINK
import id.passage.android.IntegrationTestConfig.Companion.EXISTING_USER_EMAIL_MAGIC_LINK
import id.passage.android.exceptions.MagicLinkActivateInvalidException
import id.passage.android.exceptions.MagicLinkActivateUserNotActiveException
import id.passage.android.exceptions.MagicLinkLoginException
import id.passage.android.exceptions.MagicLinkRegisterException
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
internal class PassageMagicLinkTests {
    private lateinit var passage: Passage

    @Before
    fun setUp() {
        activityRule?.scenario?.onActivity { activity ->
            activity?.let {
                passage = Passage(it, APP_ID_MAGIC_LINK)
                passage.overrideBasePath(API_BASE_URL)
            }
        }
    }

    @get:Rule
    var activityRule: ActivityScenarioRule<TestActivity?>? =
        ActivityScenarioRule(
            TestActivity::class.java,
        )

    @After
    fun tearDown() {
        runTest {
            passage.currentUser.logout()
        }
    }

    @Test
    fun testSendRegisterMagicLink() =
        runTest {
            try {
                val date = System.currentTimeMillis()
                val identifier = "authentigator+$date@passage.id"
                passage.magicLink.register(identifier, null)
            } catch (e: Exception) {
                fail(e.message)
            }
        }

    @Test
    fun testRegisterExistingUserMagicLink() =
        runTest {
            try {
                passage.magicLink.register(EXISTING_USER_EMAIL_MAGIC_LINK, null)
                fail("Test should throw NewRegisterMagicLinkInvalidIdentifierException")
            } catch (e: Exception) {
                assertThat(e is MagicLinkRegisterException)
            }
        }

    @Test
    fun testRegisterInvalidEmailAddressFormatMagicLink() =
        runTest {
            try {
                passage.magicLink.register("invalid", null)
                fail("Test should throw NewRegisterMagicLinkInvalidIdentifierException")
            } catch (e: Exception) {
                assertThat(e is MagicLinkRegisterException)
            }
        }

    @Test
    fun testSendLoginMagicLink() =
        runTest {
            try {
                val identifier = EXISTING_USER_EMAIL_MAGIC_LINK
                passage.magicLink.login(identifier, null)
            } catch (e: Exception) {
                fail(e.message)
            }
        }

    @Test
    fun testInvalidLoginMagicLink() =
        runTest {
            try {
                passage.magicLink.login("Invalid@invalid.com", null)
                fail("Test should throw NewLoginMagicLinkInvalidIdentifierException")
            } catch (e: Exception) {
                assertThat(e is MagicLinkLoginException)
            }
        }

    @Test
    fun testActivateRegisterMagicLink(): Unit =
        runBlocking {
            try {
                val date = System.currentTimeMillis()
                val identifier = "authentigator+$date@${MailosaurAPIClient.serverId}.mailosaur.net"
                passage.magicLink.register(identifier, null)
                delay(IntegrationTestConfig.WAIT_TIME_MILLISECONDS)
                val magicLink: String = MailosaurAPIClient.getMostRecentMagicLink()
                if (magicLink == "") {
                    fail("Test Failed: Magic link is empty")
                }
                passage.magicLink.activate(magicLink)
            } catch (e: Exception) {
                fail(e.toString())
            }
        }

    @Test
    fun testActivateLoginMagicLink(): Unit =
        runBlocking {
            try {
                passage.magicLink.login(EXISTING_USER_EMAIL_MAGIC_LINK, null)
                delay(IntegrationTestConfig.WAIT_TIME_MILLISECONDS)
                val magicLink: String = MailosaurAPIClient.getMostRecentMagicLink()
                if (magicLink.isEmpty()) {
                    fail("Test failed: Mailosaur function returned an empty magic link")
                }
                passage.magicLink.activate(magicLink)
            } catch (e: Exception) {
                fail(e.toString())
            }
        }

    @Test
    fun testActivateInvalidMagicLink() =
        runTest {
            runBlocking {
                try {
                    val date = System.currentTimeMillis()
                    val identifier = "authentigator+$date@${MailosaurAPIClient.serverId}.mailosaur.net"
                    passage.magicLink.register(identifier, null)
                    val magicLink = "Invalid"
                    passage.magicLink.activate(magicLink)
                    fail("Test failed: Mailosaur function returned an empty magic link")
                } catch (e: Exception) {
                    assertThat(e is MagicLinkActivateInvalidException)
                }
            }
        }

    @Test
    fun testActivateDeactivatedUserMagicLink() =
        runTest {
            try {
                passage.magicLink.login(DEACTIVATED_USER_EMAIL_MAGIC_LINK, null)
                delay(IntegrationTestConfig.WAIT_TIME_MILLISECONDS)
                val magicLink: String = MailosaurAPIClient.getMostRecentMagicLink()
                if (magicLink.isEmpty()) {
                    fail("Mailosaur catched error; returning empty string")
                }
                passage.magicLink.activate(magicLink)
                fail("Test should throw MagicLinkActivateUserNotActiveException")
            } catch (e: Exception) {
                assertThat(e is MagicLinkActivateUserNotActiveException)
            }
        }
}
