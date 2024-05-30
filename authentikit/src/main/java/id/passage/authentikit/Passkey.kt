package id.passage.authentikit

import android.content.Context
import android.os.Build
import org.json.JSONObject
import java.net.URL
import java.util.UUID
import javax.net.ssl.HttpsURLConnection

public class Passkey(private val context: Context, private val clientSideKey: String) {
    private companion object {
        const val DEVICE_OS = "Android"
        const val MINIMUM_ANDROID_VERSION = Build.VERSION_CODES.P // Android 28
        const val LAST_EVALUATION_KEY = "LAST_EVALUATION_DATE"
    }

    private val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    public suspend fun evaluateReadiness() {
        if (!canEvaluateReadiness()) return
        val deviceId = getOrGenerateDeviceId()
        val deviceOSVersion = Build.VERSION.SDK_INT
        val supportsPasskeys = deviceOSVersion >= MINIMUM_ANDROID_VERSION
        val requestHeaders =
            mapOf(
                "App-Identifier" to context.packageName,
                "Device-OS" to DEVICE_OS,
                "Device-OS-Version" to deviceOSVersion.toString(),
                "Origin" to context.packageName,
                "Content-Type" to "application/json",
                "X-API-KEY" to clientSideKey,
                "Passage-Version" to "Passage Authentikit Android ${Authentikit.PACKAGE_VERSION}",
            )
        val requestBody =
            mapOf(
                "cloud_synced" to supportsPasskeys,
                "conditional_ui" to supportsPasskeys,
                "cross_platform" to supportsPasskeys,
                "device_id" to deviceId,
                "platform" to supportsPasskeys,
                "security_key" to supportsPasskeys,
            )
        val url = URL("${Authentikit.basePath}/v1/analytics/passkey-readiness")
        val connection = url.openConnection() as HttpsURLConnection
        try {
            connection.requestMethod = "POST"
            connection.doOutput = true
            for ((key, value) in requestHeaders) {
                connection.setRequestProperty(key, value)
            }
            val jsonBody = JSONObject(requestBody).toString()
            connection.outputStream.use {
                it.write(jsonBody.toByteArray())
                it.flush()
            }
            val responseCode = connection.responseCode
            if (responseCode in 200..299) {
                updateLastEvaluationDate()
                return
            } else {
                throw PasskeyEvaluationException(connection.responseMessage ?: "Network request failed.")
            }
        } catch (e: Exception) {
            throw PasskeyEvaluationException(e.message ?: e.toString())
        } finally {
            connection.disconnect()
        }
    }

    private fun getOrGenerateDeviceId(): String {
        val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        var deviceId = sharedPreferences.getString("authentikit_device_id", null)
        if (deviceId == null) {
            deviceId = UUID.randomUUID().toString()
            sharedPreferences.edit().putString("authentikit_device_id", deviceId).apply()
        }
        return deviceId
    }

    private fun canEvaluateReadiness(): Boolean {
        val lastEvaluationDate = sharedPreferences.getLong(LAST_EVALUATION_KEY, 0L)
        val currentTime = System.currentTimeMillis()
        return (currentTime - lastEvaluationDate) > 24 * 60 * 60 * 1000
    }

    private fun updateLastEvaluationDate() {
        sharedPreferences.edit().putLong(LAST_EVALUATION_KEY, System.currentTimeMillis()).apply()
    }
}
