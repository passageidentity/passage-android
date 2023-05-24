package id.passage.android

import MailosaurAPIClient
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import id.passage.android.exceptions.PassageUserUnauthorizedException
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class TokenStoreTests {

    private lateinit var passage: Passage

    companion object {
        const val existingUserEmail = "authentigator+1684801767403@ncor7c1m.mailosaur.net"
    }

    @Before
    fun setup(): Unit = runBlocking {
        Passage.BASE_PATH = "https://auth-uat.passage.dev/v1"
        activityRule?.scenario?.onActivity { activity ->
            activity?.let {
                passage = Passage(it)
            }
        }
        // Log in user
        val otpId = passage.newLoginOneTimePasscode(existingUserEmail).otpId
        delay(3000)
        val otp = MailosaurAPIClient.getMostRecentOneTimePasscode()
        passage.oneTimePasscodeActivate(otp, otpId)
    }

    @After
    fun teardown() {

    }

    @get:Rule
    var activityRule: ActivityScenarioRule<TestActivity?>? = ActivityScenarioRule(
        TestActivity::class.java
    )

    @Test
    fun testCurrentUser_isNotNull() = runTest {
        try {
            val currentUser = passage.getCurrentUser()
            assertThat(currentUser).isNotNull()
        } catch (_: Exception) {
            assertThat(false).isTrue()
        }
    }

    @Test
    fun testCurrentUserAfterSignOut_isNull() = runTest {
        try {
            passage.signOutCurrentUser()
            val signedOutUser = passage.getCurrentUser()
            assertThat(signedOutUser).isNull()
        } catch (_: Exception) {
            assertThat(false).isTrue()
        }
    }

    @Test
    fun authToken_isNotNull() = runTest {
        try {
            val authToken = passage.tokenStore.authToken
            assertThat(authToken).isNotNull()
        } catch (_: Exception) {
            assertThat(false).isTrue()
        }
    }

    @Test
    fun authTokenAfterSignOut_isNull() = runTest {
        try {
            passage.signOutCurrentUser()
            val authToken = passage.tokenStore.authToken
            assertThat(authToken).isNull()
        } catch (_: Exception) {
            assertThat(false).isTrue()
        }
    }

    @Test
    fun authTokenChangesAfterRefresh() = runTest {
        try {
            val oldToken = passage.tokenStore.authToken
            passage.tokenStore.attemptRefreshTokenStore()
            val newToken = passage.tokenStore.authToken
            assertThat(oldToken).isNotNull()
            assertThat(newToken).isNotNull()
            assertThat(oldToken).isNotEqualTo(newToken)
        } catch (_: Exception) {
            assertThat(false).isTrue()
        }
    }

    @Test
    fun authTokenThrowsErrorAfterRevoke() = runTest {
        try {
            val user = passage.getCurrentUser()
            passage.tokenStore.clearAndRevokeTokens()
            user?.listDevicePasskeys()
            assertThat(false).isTrue()
        } catch (e: Exception) {
            when (e) {
                is PassageUserUnauthorizedException -> {
                    assertThat(true).isTrue()
                }
                else -> {
                    assertThat(false).isTrue()
                }
            }
        }
    }

}
