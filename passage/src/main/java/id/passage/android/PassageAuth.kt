package id.passage.android

import android.util.Log
import id.passage.android.api.LoginAPI
import id.passage.android.api.RegisterAPI
import id.passage.android.model.ApiloginMagicLinkRequest
import id.passage.android.model.ApiregisterMagicLinkRequest

@Suppress("unused")
class PassageAuth {

    companion object {
        private const val TAG = "PassageAuth"
        private const val APP_ID = ""
        suspend fun login(identifier: String) {
            val login = LoginAPI()
            val request = ApiloginMagicLinkRequest(identifier)
            login.loginMagicLink(appId = "", user = request)
        }

        suspend fun register(identifier: String): String? {
            val register = RegisterAPI()
            val request = ApiregisterMagicLinkRequest(identifier)
            val response = register.registerMagicLink(APP_ID, request)
            return response.magicLink?.id
        }

    }

}
