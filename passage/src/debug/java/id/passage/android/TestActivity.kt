package id.passage.android

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.runBlocking

class TestActivity : AppCompatActivity() {
    private lateinit var passage: Passage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        passage = Passage(this)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val authCode = intent.data?.getQueryParameter("code") ?: ""
        val state = intent.data?.getQueryParameter("state") ?: ""
        if (authCode.isNotEmpty()) {
            runBlocking {
                try {
                    passage.hostedAuthFinish(authCode, "JkXmvBNPFTL0Zb7Ya7W4wc0o7cOi9o8K", state)
                } catch (e: Exception) {
                    // Handle any exceptions
                }
            }
        }
    }
}
