package id.passage.android.utils

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.webkit.CookieManager
import androidx.browser.customtabs.CustomTabsCallback
import androidx.browser.customtabs.CustomTabsClient
import androidx.browser.customtabs.CustomTabsIntent
import androidx.browser.customtabs.CustomTabsServiceConnection
import androidx.browser.customtabs.CustomTabsSession

object WebViewUtils {
    private var customTabsSession: CustomTabsSession? = null

    private fun createCustomTabsSession(context: Context): CustomTabsSession? {
        CustomTabsClient.bindCustomTabsService(
            context,
            "com.android.chrome",
            object : CustomTabsServiceConnection() {
                override fun onCustomTabsServiceConnected(
                    name: ComponentName,
                    client: CustomTabsClient,
                ) {
                    customTabsSession =
                        client
                            .newSession(
                                object : CustomTabsCallback() {
                                    override fun onNavigationEvent(
                                        navigationEvent: Int,
                                        extras: Bundle?,
                                    ) {
                                        if (navigationEvent == TAB_HIDDEN || navigationEvent == NAVIGATION_FINISHED) {
                                            CookieManager
                                                .getInstance()
                                                .flush()
                                        }
                                    }
                                },
                            )
                }

                override fun onServiceDisconnected(name: ComponentName) {
                    customTabsSession = null
                }
            },
        )
        return customTabsSession
    }

    fun openCustomTab(
        context: Context,
        url: String,
    ) {
        val session = createCustomTabsSession(context)
        val customTabsIntent = CustomTabsIntent.Builder(session).build()
        customTabsIntent.launchUrl(context as Activity, Uri.parse(url))
    }
}
