package id.passage.android

import android.app.Activity
import android.webkit.WebSettings
import id.passage.android.ResourceUtils.Companion.getOptionalResourceFromApp
import id.passage.android.ResourceUtils.Companion.getRequiredResourceFromApp
import okhttp3.OkHttpClient

@Suppress("unused", "RedundantVisibilityModifier", "RedundantModalityModifier")
public final class Passage(
    activity: Activity,
    appId: String? = null,
) {
    // region Private VARIABLES
    var app: App
    var passkey: Passkey
    var magicLink: MagicLink
    var hosted: Hosted
    var oneTimePasscode: OneTimePasscode
    var social: Social
    var currentUser: CurrentUser
    var tokenStore: PassageTokenStore
    var passageClient: OkHttpClient

    // region CONSTANTS AND SINGLETON VARIABLES
    internal companion object {
        internal const val TAG = "Passage"
        internal var BASE_PATH = "https://auth.passage.id/v1"
        internal lateinit var appId: String
        internal lateinit var authOrigin: String
        internal var language: String? = null
    }

    // region INITIALIZATION

    init {
        authOrigin = getRequiredResourceFromApp(activity, "passage_auth_origin")
        val userAgent = WebSettings.getDefaultUserAgent(activity) ?: "Android"
        passageClient =
            OkHttpClient
                .Builder()
                .addNetworkInterceptor { chain ->
                    chain.proceed(
                        chain
                            .request()
                            .newBuilder()
                            .header("User-Agent", userAgent)
                            .build(),
                    )
                }.build()
        Companion.appId = appId ?: getRequiredResourceFromApp(activity, "passage_app_id")
        language = getOptionalResourceFromApp(activity, "passage_language")
        tokenStore = PassageTokenStore(activity)
        app = App(passageClient)
        passkey = Passkey(passageClient, activity)
        oneTimePasscode = OneTimePasscode(passageClient, tokenStore)
        hosted = Hosted(activity, tokenStore)
        social = Social(passageClient, activity, tokenStore)
        magicLink = MagicLink(passageClient, tokenStore)
        currentUser = CurrentUser(tokenStore, activity)
    }

    public fun overrideBasePath(newPath: String) {
        BASE_PATH = newPath
    }
}
