package id.passage.passageflex

import android.app.Activity

internal class Utils {

    internal companion object {

        internal fun getAppId(activity: Activity): String {
            val resourceName = "passage_app_id"
            val resource = activity.resources.getIdentifier(resourceName, "string", activity.packageName)
            val resourceString = if (resource == 0) null else activity.getString(resource)
            require(!resourceString.isNullOrBlank()) {
                String.format(
                    "The 'R.string.%s' value it's not defined in your project's resources file.",
                    resourceName,
                )
            }
            return resourceString
        }

    }
}