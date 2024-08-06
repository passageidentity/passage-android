@file:Suppress("RedundantVisibilityModifier")

package id.passage.android

import id.passage.android.api.CurrentuserAPI
import id.passage.android.model.App
import id.passage.android.model.Credential
import id.passage.android.model.CurrentUser
import id.passage.android.model.MagicLink
import id.passage.android.model.OAuth2ConnectionType
import id.passage.android.model.User
import id.passage.android.model.UserMetadataResponse

public typealias PassageAppInfo = App

public typealias MagicLinkResponse = MagicLink

public typealias CurrentUserInfo = CurrentUser

public typealias PassagePasskey = Credential

public typealias Metadata = UserMetadataResponse

public typealias SocialConnection = CurrentuserAPI.SocialConnectionType_deleteCurrentuserSocialConnection

public typealias PublicUserInfo = User

public typealias PassageSocialConnection = OAuth2ConnectionType
