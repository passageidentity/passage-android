package id.passage.authentikit

import android.content.Context

public class Authentikit(
    context: Context,
    organizationId: String
) {

    val passkey: Passkey

    init {
        passkey = Passkey(context, organizationId)
    }

    internal companion object {
        const val BASE_PATH = "https://auth-uat.passage.dev"
    }

}
