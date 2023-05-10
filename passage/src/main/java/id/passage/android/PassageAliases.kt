@file:Suppress("RedundantVisibilityModifier")

package id.passage.android

import id.passage.android.model.ApiMagicLink
import id.passage.android.model.AuthResult1
import id.passage.android.model.IdentityApp
import id.passage.android.model.Model401Code
import id.passage.android.model.Model403Code
import id.passage.android.model.OneTimePasscodeResponse

public typealias MagicLink = ApiMagicLink

public typealias PassageAuthResult = AuthResult1

public typealias OneTimePasscode = OneTimePasscodeResponse

public typealias PassageApp = IdentityApp

public typealias PassageAuthFallbackMethod = IdentityApp.AuthFallbackMethod

public typealias Passage401Code = Model401Code

public typealias Passage403Code = Model403Code
