package id.passage.android

@Suppress("RedundantVisibilityModifier")
@Deprecated("Authentication results are no longer treated as fallbacks. This data class will be removed in a future version.")
public data class PassageAuthFallbackResult(val id: String, val method: PassageAuthFallbackMethod)
