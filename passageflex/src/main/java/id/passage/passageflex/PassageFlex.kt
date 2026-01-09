package id.passage.passageflex

import android.app.Activity

/**
 *  Use `PassageFlex` to easily add passkeys to your existing authentication system on Android devices.
 *
 *  Find out more at: https://passage.1password.com
 */
@Deprecated(
  message = "This SDK is no longer supported.",
  level = DeprecationLevel.WARNING
)
public class PassageFlex(
    activity: Activity,
    appId: String,
) {
    /**
     * PassageFlexPasskey utilizes Android's native passkey APIs and Passage Flex APIs together.
     */
    public val passkey = PassageFlexPasskey(activity, appId)
}
