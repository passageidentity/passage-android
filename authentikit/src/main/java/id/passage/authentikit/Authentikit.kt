package id.passage.authentikit

import android.os.Build
import android.content.Context
import java.util.UUID

public class Authentikit(
    val context: Context
) {

    private companion object {
        val sessionId = UUID.randomUUID().toString()
        const val deviceOS = "Android"
        const val minimumAndroidVersion = Build.VERSION_CODES.P // Android 28
    }

    public fun evaluateReadiness() {


        // Header values
        val deviceOS = deviceOS
        val deviceOSVersion = Build.VERSION.SDK_INT
        val appIdentifier = context.packageName

        // Body values
        val supportsPasskeys = deviceOSVersion >= minimumAndroidVersion

        val sessionId = sessionId

    }


}
