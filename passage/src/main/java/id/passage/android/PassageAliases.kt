@file:Suppress("RedundantVisibilityModifier")

package id.passage.android

import id.passage.android.model.ApiMagicLink
import id.passage.android.model.App
import id.passage.android.model.AuthResult1
import id.passage.android.model.IdentityApp
import id.passage.android.model.ModelsCredential
import id.passage.android.model.OneTimePasscodeResponse

public typealias MagicLink = ApiMagicLink

public typealias PassageAuthResult = AuthResult1

public typealias OneTimePasscode = OneTimePasscodeResponse

@Deprecated("Use PassageAppInfo instead.", replaceWith = ReplaceWith("PassageAppInfo"))
public typealias PassageApp = IdentityApp

public typealias PassageAppInfo = App

@Deprecated("Authentication methods are no longer treated as fallbacks. This enum class will be removed in a future version.")
public typealias PassageAuthFallbackMethod = IdentityApp.AuthFallbackMethod

public typealias PassageCredential = ModelsCredential
