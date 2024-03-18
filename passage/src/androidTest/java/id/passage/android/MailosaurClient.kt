import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.Base64

@Serializable
private data class NameEmail(val name: String, val email: String)

@Serializable
private data class MessageLink(val href: String, val text: String)

@Serializable
private data class MessageCode(val value: String)

@Serializable
private data class MessageHTML(val body: String, val links: List<MessageLink>, val codes: List<MessageCode>)

@Serializable
private data class ListMessage(
    val id: String,
    val received: String,
    val type: String,
    val subject: String,
    val from: List<NameEmail>,
    val to: List<NameEmail>,
    val cc: List<String>,
    val bcc: List<String>,
)

@Serializable
private data class ListMessagesResponse(val items: List<ListMessage>)

@Serializable
private data class GetMessageResponse(
    val id: String,
    val received: String,
    val type: String,
    val subject: String,
    val from: List<NameEmail>,
    val to: List<NameEmail>,
    val cc: List<String>,
    val bcc: List<String>,
    val html: MessageHTML,
)

internal object MailosaurAPIClient {
    internal const val serverId = "ncor7c1m"

    private const val apiURL = "https://mailosaur.com/api/messages"
    private const val mailosaurAPIKey = ""

    private val client = OkHttpClient()

    private fun appUrl(path: String): String {
        return "$apiURL$path"
    }

    private val authHeader: String
        get() {
            val apiKey = "api:$mailosaurAPIKey".toByteArray(Charsets.UTF_8)
            val encodedApiKey = Base64.getEncoder().encodeToString(apiKey)
            return "Basic $encodedApiKey"
        }

    private fun buildRequest(url: String): Request {
        return try {
            val parsedUrl = url.toHttpUrlOrNull() ?: throw Exception("Bad url path")
            val request =
                Request.Builder()
                    .url(parsedUrl)
                    .addHeader("Authorization", authHeader)
                    .build()
            request
        } catch (e: Exception) {
            throw Exception("Bad url path")
        }
    }

    internal suspend fun getMostRecentMagicLink(): String {
        return try {
            val messages = listMessages()
            if (messages.isEmpty()) return ""
            val message = getMessage(messages[0].id)
            val incomingURL = message.html.links[0].href
            val components = java.net.URL(incomingURL).query
            val magicLink =
                components.split("&")
                    .find { it.startsWith("psg_magic_link=") }
                    ?.substringAfter("psg_magic_link=")
            magicLink ?: ""
        } catch (e: Exception) {
            ""
        }
    }

    internal suspend fun getMostRecentOneTimePasscode(): String {
        return try {
            val messages = listMessages()
            if (messages.isEmpty()) return ""
            val message = getMessage(messages[0].id)
            val oneTimePasscode = message.html.codes.firstOrNull()?.value ?: ""
            oneTimePasscode
        } catch (e: Exception) {
            ""
        }
    }

    private suspend fun getMessage(id: String): GetMessageResponse {
        val url = appUrl("/$id")
        val request = buildRequest(url)
        val json = Json { ignoreUnknownKeys = true }
        val response = client.newCall(request).execute()
        val responseData = response.body?.string()
        val message = responseData?.let { json.decodeFromString<GetMessageResponse>(it) }
        return message ?: throw Exception("Failed to parse response")
    }

    private suspend fun listMessages(): List<ListMessage> {
        val url = appUrl("?server=$serverId")
        val request = buildRequest(url)
        val json = Json { ignoreUnknownKeys = true }
        val response = client.newCall(request).execute()
        val responseData = response.body?.string()
        val messages = responseData?.let { json.decodeFromString<ListMessagesResponse>(it) }
        return messages?.items ?: emptyList()
    }
}
