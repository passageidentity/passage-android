@file:Suppress("RedundantVisibilityModifier")

package id.passage.android

import id.passage.android.model.ApiMagicLink
import id.passage.android.model.AuthResult1
import id.passage.android.model.IdentityApp
import id.passage.android.model.Model401Code
import id.passage.android.model.Model403Code
import id.passage.android.model.ModelsCredential
import id.passage.android.model.OneTimePasscodeResponse

public typealias MagicLink = ApiMagicLink

public typealias PassageAuthResult = AuthResult1

public typealias OneTimePasscode = OneTimePasscodeResponse

public typealias PassageApp = IdentityApp

@Deprecated("Authentication methods are no longer treated as fallbacks. This enum class will be removed in a future version.")
public typealias PassageAuthFallbackMethod = IdentityApp.AuthFallbackMethod

public typealias PassageCredential = ModelsCredential
