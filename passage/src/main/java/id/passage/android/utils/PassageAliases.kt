
package id.passage.android.utils

import id.passage.android.api.CurrentuserAPI
import id.passage.android.model.App
import id.passage.android.model.Credential
import id.passage.android.model.CurrentUser
import id.passage.android.model.OneTimePasscodeResponse
import id.passage.android.model.User
import id.passage.android.model.UserMetadataResponse

typealias PassageAppInfo = App

typealias CurrentUserInfo = CurrentUser

typealias OneTimePasscode = OneTimePasscodeResponse

typealias Passkey = Credential

typealias Metadata = UserMetadataResponse

typealias SocialConnection = CurrentuserAPI.SocialConnectionType_deleteCurrentuserSocialConnection

typealias PublicUserInfo = User
