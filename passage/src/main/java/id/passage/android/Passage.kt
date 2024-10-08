package id.passage.android

import PassageClientService
import android.app.Activity
import id.passage.android.utils.ResourceUtils.Companion.getRequiredResourceFromApp
import okhttp3.OkHttpClient

class Passage(
    activity: Activity,
    appId: String,
) {
    // region VARIABLES
    var app: PassageApp
    var passkey: PassagePasskey
    var magicLink: PassageMagicLink
    var hosted: PassageHosted
    var oneTimePasscode: PassageOneTimePasscode
    var social: PassageSocial
    var currentUser: PassageCurrentUser
    var tokenStore: PassageTokenStore
    private var passageClient: OkHttpClient

    // region CONSTANTS AND SINGLETON VARIABLES
    internal companion object {
        internal const val TAG = "Passage"
        internal lateinit var appId: String
        internal lateinit var authOrigin: String
    }

    // region INITIALIZATION

    init {
        authOrigin = getRequiredResourceFromApp(activity, "passage_auth_origin")
        passageClient = PassageClientService.setup(activity)
        Companion.appId = appId
        tokenStore = PassageTokenStore(activity)
        app = PassageApp(passageClient)
        passkey = PassagePasskey(passageClient, activity, tokenStore)
        oneTimePasscode = PassageOneTimePasscode(passageClient, tokenStore)
        hosted = PassageHosted(activity, tokenStore)
        social = PassageSocial(passageClient, activity, tokenStore)
        magicLink = PassageMagicLink(passageClient, tokenStore)
        currentUser = PassageCurrentUser(tokenStore, activity, passageClient)
    }
}
