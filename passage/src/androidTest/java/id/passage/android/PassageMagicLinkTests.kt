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
import id.passage.android.exceptions.NewLoginMagicLinkInvalidIdentifierException
import id.passage.android.exceptions.NewRegisterMagicLinkInvalidIdentifierException
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
            passage.signOutCurrentUser()
        }
    }

    @Test
    fun testSendRegisterMagicLink() =
        runTest {
            try {
                val date = System.currentTimeMillis()
                val identifier = "authentigator+$date@passage.id"
                passage.newRegisterMagicLink(identifier, null)
            } catch (e: Exception) {
                fail(e.message)
            }
        }

    @Test
    fun testRegisterExistingUserMagicLink() =
        runTest {
            try {
                passage.newRegisterMagicLink(EXISTING_USER_EMAIL_MAGIC_LINK, null)
                fail("Test should throw NewRegisterMagicLinkInvalidIdentifierException")
            } catch (e: Exception) {
                assertThat(e is NewRegisterMagicLinkInvalidIdentifierException)
            }
        }

    @Test
    fun testRegisterInvalidEmailAddressFormatMagicLink() =
        runTest {
            try {
                passage.newRegisterMagicLink("invalid", null)
                fail("Test should throw NewRegisterMagicLinkInvalidIdentifierException")
            } catch (e: Exception) {
                assertThat(e is NewRegisterMagicLinkInvalidIdentifierException)
            }
        }

    @Test
    fun testSendLoginMagicLink() =
        runTest {
            try {
                val identifier = EXISTING_USER_EMAIL_MAGIC_LINK
                passage.newLoginMagicLink(identifier, null)
            } catch (e: Exception) {
                fail(e.message)
            }
        }

    @Test
    fun testInvalidLoginMagicLink() =
        runTest {
            try {
                passage.newLoginMagicLink("Invalid@invalid.com", null)
                fail("Test should throw NewLoginMagicLinkInvalidIdentifierException")
            } catch (e: Exception) {
                assertThat(e is NewLoginMagicLinkInvalidIdentifierException)
            }
        }

    @Test
    fun testActivateRegisterMagicLink(): Unit =
        runBlocking {
            try {
                val date = System.currentTimeMillis()
                val identifier = "authentigator+$date@${MailosaurAPIClient.serverId}.mailosaur.net"
                passage.newRegisterMagicLink(identifier, null)
                delay(IntegrationTestConfig.WAIT_TIME_MILLISECONDS)
                val magicLink: String = MailosaurAPIClient.getMostRecentMagicLink()
                if (magicLink == "") {
                    fail("Test Failed: Magic link is empty")
                }
                passage.magicLinkActivate(magicLink)
            } catch (e: Exception) {
                fail(e.toString())
            }
        }

    @Test
    fun testActivateLoginMagicLink(): Unit =
        runBlocking {
            try {
                passage.newLoginMagicLink(EXISTING_USER_EMAIL_MAGIC_LINK, null)
                delay(IntegrationTestConfig.WAIT_TIME_MILLISECONDS)
                val magicLink: String = MailosaurAPIClient.getMostRecentMagicLink()
                if (magicLink.isEmpty()) {
                    fail("Test failed: Mailosaur function returned an empty magic link")
                }
                passage.magicLinkActivate(magicLink)
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
                    passage.newRegisterMagicLink(identifier, null)
                    val magicLink = "Invalid"
                    passage.magicLinkActivate(magicLink)
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
                passage.newLoginMagicLink(DEACTIVATED_USER_EMAIL_MAGIC_LINK, null)
                delay(IntegrationTestConfig.WAIT_TIME_MILLISECONDS)
                val magicLink: String = MailosaurAPIClient.getMostRecentMagicLink()
                if (magicLink.isEmpty()) {
                    fail("Mailosaur catched error; returning empty string")
                }
                passage.magicLinkActivate(magicLink)
                fail("Test should throw MagicLinkActivateUserNotActiveException")
            } catch (e: Exception) {
                assertThat(e is MagicLinkActivateUserNotActiveException)
            }
        }
}
