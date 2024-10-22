import android.app.Activity
import id.passage.android.utils.ResourceUtils
import okhttp3.OkHttpClient

internal object PassageClientService {
    private const val PACKAGE_VERSION_NUMBER = "2.0.2"
    private const val DEFAULT_BASE_PATH = "https://auth.passage.id/v1"
    lateinit var basePath: String

    fun setup(activity: Activity): OkHttpClient {
        basePath = ResourceUtils.getOptionalResourceFromApp(activity, "clientApiBasePath") ?: DEFAULT_BASE_PATH
        val userAgent = getUserAgentInfo()

        return OkHttpClient
            .Builder()
            .addNetworkInterceptor { chain ->
                val originalRequest = chain.request()
                val requestBuilder =
                    originalRequest
                        .newBuilder()
                        .header("User-Agent", userAgent)
                        .header("Passage-Version", "kotlin $PACKAGE_VERSION_NUMBER")
                        .header("Base-Path", basePath)

                val modifiedRequest = requestBuilder.build()
                chain.proceed(modifiedRequest)
            }.build()
    }

    private fun getUserAgentInfo(): String {
        val name = "Passage Android"
        val prefix = "$name/$PACKAGE_VERSION_NUMBER"
        val systemVersion = android.os.Build.VERSION.RELEASE
        val model = android.os.Build.MODEL
        return "$prefix (Android; Version $systemVersion; Device $model)"
    }
}
