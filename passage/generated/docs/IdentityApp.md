
# IdentityApp

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**allowedIdentifier** | **kotlin.String** |  |  [optional]
**authFallbackMethod** | [**inline**](#AuthFallbackMethod) |  |  [optional]
**authFallbackMethodTtl** | **kotlin.Int** |  |  [optional]
**authOrigin** | **kotlin.String** |  |  [optional]
**defaultLanguage** | **kotlin.String** |  |  [optional]
**ephemeral** | **kotlin.Boolean** |  |  [optional]
**id** | **kotlin.String** |  |  [optional]
**layouts** | [**ModelsLayouts**](ModelsLayouts.md) |  |  [optional]
**loginUrl** | **kotlin.String** |  |  [optional]
**name** | **kotlin.String** |  |  [optional]
**publicSignup** | **kotlin.Boolean** |  |  [optional]
**redirectUrl** | **kotlin.String** |  |  [optional]
**requireEmailVerification** | **kotlin.Boolean** |  |  [optional]
**requireIdentifierVerification** | **kotlin.Boolean** |  |  [optional]
**requiredIdentifier** | **kotlin.String** |  |  [optional]
**rsaPublicKey** | **kotlin.String** |  |  [optional]
**sessionTimeoutLength** | **kotlin.Int** |  |  [optional]
**userMetadataSchema** | [**kotlin.collections.List&lt;IdentityUserMetadataField&gt;**](IdentityUserMetadataField.md) |  |  [optional]


<a name="AuthFallbackMethod"></a>
## Enum: auth_fallback_method
Name | Value
---- | -----
authFallbackMethod | magic_link, otp, none



