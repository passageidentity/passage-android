package id.passage.authentikit

import android.content.Context

public class Authentikit(
    context: Context,
    clientSideKey: String
) {

    val passkey: Passkey

    init {
        passkey = Passkey(context, clientSideKey)
    }

    internal companion object {
        const val BASE_PATH = "https://auth.passage.id"
        const val PACKAGE_VERSION = "0.1.0"
    }

}
