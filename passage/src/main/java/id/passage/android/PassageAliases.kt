@file:Suppress("RedundantVisibilityModifier")

package id.passage.android

import id.passage.android.model.App
import id.passage.android.model.AuthResult
import id.passage.android.model.Credential
import id.passage.android.model.OneTimePasscodeResponse

public typealias PassageAuthResult = AuthResult

public typealias OneTimePasscode = OneTimePasscodeResponse

@Deprecated("Use PassageAppInfo instead.", replaceWith = ReplaceWith("PassageAppInfo"))
public typealias PassageApp = App

public typealias PassageAppInfo = App

@Deprecated("Authentication methods are no longer treated as fallbacks. This enum class will be removed in a future version.")
public typealias PassageAuthFallbackMethod = App.AuthFallbackMethod

public typealias PassageCredential = Credential
