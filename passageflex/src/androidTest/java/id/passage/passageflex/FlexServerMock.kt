package id.passage.passageflex

import org.json.JSONObject
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.util.UUID

internal object FlexServerMock {
    suspend fun getTransactionId(
        email: String,
        isNewUser: Boolean,
        appId: String,
    ): String =
        kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) {
            val transactionType = if (isNewUser) "register" else "authenticate"
            val url = URL("${FlexTestConfig.MGMT_BASE_URL}/apps/$appId/transactions/$transactionType")
            (url.openConnection() as HttpURLConnection).run {
                requestMethod = "POST"
                doOutput = true // Allows POST data
                setRequestProperty("Content-Type", "application/json")
                setRequestProperty("Authorization", "Bearer ${FlexTestConfig.APP_API_KEY}")
                val postData = JSONObject()
                if (isNewUser) {
                    val randomUserId = UUID.randomUUID().toString()
                    postData.put("external_id", randomUserId)
                    postData.put("passkey_display_name", email)
                } else {
                    // Placeholder for authenticating with existing user id.
                    // We can implement this once we setup remote test devices.
//                postData.put("external_id", FlexTestConfig.EXISTING_USER_ID)
                }

                OutputStreamWriter(outputStream).use { writer ->
                    writer.write(postData.toString())
                    writer.flush()
                }
                if (responseCode == HttpURLConnection.HTTP_CREATED) {
                    val response = inputStream.bufferedReader().use { it.readText() }
                    val jsonResponse = JSONObject(response)
                    jsonResponse.getString("transaction_id")
                } else {
                    throw Exception("Failed to get transaction id: $responseCode")
                }
            }
        }

    // Placeholder for verifying a nonce returned from a PassageFlex operation.
    // We can implement this once we setup remote test devices.
    suspend fun verify(
        nonce: String,
        appId: String,
    ): String =
        kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) {
            val url = URL("${FlexTestConfig.MGMT_BASE_URL}/apps/$appId/verify")
            (url.openConnection() as HttpURLConnection).run {
                requestMethod = "POST"
                doOutput = true // Allows POST data
                setRequestProperty("Content-Type", "application/json")
                setRequestProperty("Authorization", "Bearer ${FlexTestConfig.APP_API_KEY}")
                val postData = JSONObject()
                postData.put("nonce", nonce)
                OutputStreamWriter(outputStream).use { writer ->
                    writer.write(postData.toString())
                    writer.flush()
                }
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val response = inputStream.bufferedReader().use { it.readText() }
                    val jsonResponse = JSONObject(response)
                    jsonResponse.getString("auth_token")
                } else {
                    throw Exception("Failed to get token: $responseCode")
                }
            }
        }
}
