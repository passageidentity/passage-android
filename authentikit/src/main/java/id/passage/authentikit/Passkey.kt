package id.passage.authentikit

import android.content.Context
import android.os.Build
import java.net.HttpURLConnection
import java.net.URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.util.UUID

public class Passkey(private val context: Context, private val organizationId: String) {

    private companion object {
        const val deviceOS = "Android"
        const val minimumAndroidVersion = Build.VERSION_CODES.P // Android 28
    }

    private val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    private val lastEvaluationKey = "lastEvaluationDate"

    public suspend fun evaluateReadiness() {
        if (!canEvaluateReadiness()) {
            println("❌ Evaluate readiness can only be called once in 24 hours.")
            return
        }
        val deviceId = getOrGenerateDeviceId()
        val deviceOSVersion = Build.VERSION.SDK_INT
        val supportsPasskeys = deviceOSVersion >= minimumAndroidVersion
        val requestHeaders = mapOf(
            "app_identifier" to context.packageName,
            "device_os" to deviceOS,
            "device_os_version" to deviceOSVersion.toString(),
            "Content-Type" to "application/json"
        )
        val requestBody = mapOf(
            "device_id" to deviceId,
            "security_key" to supportsPasskeys,
            "platform" to supportsPasskeys,
            "cloud_synced" to supportsPasskeys,
            "cross_platform" to supportsPasskeys,
            "conditional_ui" to supportsPasskeys
        )
        val urlString = "${Authentikit.BASE_PATH}/v1/organizations/$organizationId/analytics/passkey-readiness"
        val url = URL(urlString)

        withContext(Dispatchers.IO) {
            try {
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "POST"
                for ((key, value) in requestHeaders) {
                    connection.setRequestProperty(key, value)
                }
                val jsonBody = JSONObject(requestBody).toString()
                connection.outputStream.use { it.write(jsonBody.toByteArray()) }
                val responseCode = connection.responseCode
                if (responseCode in 200..299) {
                    updateLastEvaluationDate()
                    println("✅ Successfully evaluated passkey readiness.")
                } else {
                    println("❌ Failed to evaluate passkey readiness.")
                }
            } catch (e: Exception) {
                println("❌ Failed to evaluate passkey readiness.")
            }
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
        val lastEvaluationDate = sharedPreferences.getLong(lastEvaluationKey, 0L)
        val currentTime = System.currentTimeMillis()
        return (currentTime - lastEvaluationDate) > 24 * 60 * 60 * 1000
    }

    private fun updateLastEvaluationDate() {
        sharedPreferences.edit().putLong(lastEvaluationKey, System.currentTimeMillis()).apply()
    }

}
