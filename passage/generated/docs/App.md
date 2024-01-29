
# App

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**allowedIdentifier** | **kotlin.String** |  | 
**authFallbackMethod** | [**inline**](#AuthFallbackMethod) | Deprecated Property. Please refer to &#x60;auth_methods&#x60; to view settings for individual authentication methods. | 
**authFallbackMethodTtl** | **kotlin.Int** | Deprecated Property. Please refer to &#x60;auth_methods&#x60; to view settings for individual authentication methods. | 
**authMethods** | [**AuthMethods**](AuthMethods.md) |  | 
**authOrigin** | **kotlin.String** |  | 
**defaultLanguage** | **kotlin.String** |  | 
**elementCustomization** | [**ElementCustomization**](ElementCustomization.md) |  | 
**elementCustomizationDark** | [**ElementCustomization**](ElementCustomization.md) |  | 
**ephemeral** | **kotlin.Boolean** |  | 
**id** | **kotlin.String** |  | 
**layouts** | [**Layouts**](Layouts.md) |  | 
**loginUrl** | **kotlin.String** |  | 
**name** | **kotlin.String** |  | 
**passageBranding** | **kotlin.Boolean** |  | 
**publicSignup** | **kotlin.Boolean** |  | 
**profileManagement** | **kotlin.Boolean** |  | 
**redirectUrl** | **kotlin.String** |  | 
**requireEmailVerification** | **kotlin.Boolean** |  | 
**requireIdentifierVerification** | **kotlin.Boolean** |  | 
**requiredIdentifier** | **kotlin.String** |  | 
**rsaPublicKey** | **kotlin.String** |  | 
**sessionTimeoutLength** | **kotlin.Int** |  | 
**socialConnections** | [**SocialConnections**](SocialConnections.md) |  | 
**userMetadataSchema** | [**kotlin.collections.List&lt;UserMetadataField&gt;**](UserMetadataField.md) |  | 


<a name="AuthFallbackMethod"></a>
## Enum: auth_fallback_method
Name | Value
---- | -----
authFallbackMethod | magic_link, otp, none



