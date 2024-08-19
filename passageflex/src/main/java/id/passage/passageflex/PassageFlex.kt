package id.passage.passageflex

import android.app.Activity

/**
 *  Use `PassageFlex` to easily add passkeys to your existing authentication system on Android devices.
 *
 *  Find out more at: https://passage.1password.com
 */
public class PassageFlex(
    appId: String,
    activity: Activity
) {

    /**
     * PassageFlexPasskey utilizes Android's native passkey APIs and Passage Flex APIs together.
     */
    public val passkey = PassageFlexPasskey(appId, activity)

}
