@file:Suppress("RedundantVisibilityModifier")

package id.passage.android

import id.passage.android.api.CurrentuserAPI
import id.passage.android.model.App
import id.passage.android.model.Credential
import id.passage.android.model.CurrentUser
import id.passage.android.model.OneTimePasscodeResponse
import id.passage.android.model.User
import id.passage.android.model.UserMetadataResponse

public typealias PassageAppInfo = App

public typealias CurrentUserInfo = CurrentUser

public typealias OneTimePasscode = OneTimePasscodeResponse

public typealias Passkey = Credential

public typealias Metadata = UserMetadataResponse

public typealias SocialConnection = CurrentuserAPI.SocialConnectionType_deleteCurrentuserSocialConnection

public typealias PublicUserInfo = User
