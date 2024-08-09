package id.passage.android.utils

import android.app.Activity

internal class ResourceUtils {
    internal companion object {
        internal fun getOptionalResourceFromApp(
            activity: Activity,
            resName: String,
        ): String? {
            val stringRes = activity.resources.getIdentifier(resName, "string", activity.packageName)
            if (stringRes == 0) return null
            return activity.getString(stringRes)
        }

        internal fun getRequiredResourceFromApp(
            activity: Activity,
            resName: String,
        ): String {
            val stringRes = getOptionalResourceFromApp(activity, resName)
            require(!stringRes.isNullOrBlank()) {
                String.format(
                    "The 'R.string.%s' value it's not defined in your project's resources file.",
                    resName,
                )
            }
            return stringRes
        }
    }
}
