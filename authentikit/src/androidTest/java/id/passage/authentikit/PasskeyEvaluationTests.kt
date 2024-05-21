package id.passage.authentikit

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.test.runTest
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PasskeyEvaluationTests {
    companion object {
        const val VALID_KEY = "q2sff2MGjWzSOfMAW9fXODoi"
        const val INVALID_KEY = "INVALID_KEY"
    }

    @Before
    fun setup() {
        Authentikit.BASE_PATH = "https://auth-uat.passage.dev"
    }

    @Test
    fun validClientKeySucceeds() =
        runTest {
            val appContext = InstrumentationRegistry.getInstrumentation().targetContext
            val authKit = Authentikit(appContext, VALID_KEY)
            authKit.passkey.evaluateReadiness()
        }

    @Test
    fun invalidClientKeyFails() =
        runTest {
            val appContext = InstrumentationRegistry.getInstrumentation().targetContext
            val authKit = Authentikit(appContext, INVALID_KEY)
            try {
                authKit.passkey.evaluateReadiness()
                fail("evaluateReadiness should fail with PasskeyEvaluationException")
            } catch (e: Exception) {
                assert(e is PasskeyEvaluationException)
            }
        }
}
