package id.passage.authentikit

import android.content.Context

@Deprecated(
  message = "This SDK is no longer supported.",
  level = DeprecationLevel.WARNING
)
public class Authentikit(
    context: Context,
    clientSideKey: String,
) {
    val passkey: Passkey

    init {
        passkey = Passkey(context, clientSideKey)
    }

    internal companion object {
        var basePath = "https://auth.passage.id"
        const val PACKAGE_VERSION = "0.1.0"
    }
}
