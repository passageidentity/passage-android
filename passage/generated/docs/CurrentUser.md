
# CurrentUser

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**createdAt** | **kotlin.String** | When this user was created | 
**email** | **kotlin.String** | The user&#39;s email | 
**emailVerified** | **kotlin.Boolean** | Whether or not the user&#39;s email has been verified | 
**id** | **kotlin.String** | The user ID | 
**lastLoginAt** | **kotlin.String** | The last time this user logged in | 
**loginCount** | **kotlin.Int** | How many times the user has successfully logged in | 
**phone** | **kotlin.String** | The user&#39;s phone | 
**phoneVerified** | **kotlin.Boolean** | Whether or not the user&#39;s phone has been verified | 
**socialConnections** | [**UserSocialConnections**](UserSocialConnections.md) |  | 
**status** | [**UserStatus**](UserStatus.md) |  | 
**updatedAt** | **kotlin.String** | When this user was last updated | 
**userMetadata** | [**kotlin.Any**](.md) |  | 
**webauthn** | **kotlin.Boolean** | Whether or not the user has authenticated via webAuthn before (if len(WebAuthnDevices) &gt; 0) | 
**webauthnDevices** | [**kotlin.collections.List&lt;Credential&gt;**](Credential.md) | The list of devices this user has authenticated with via webAuthn | 
**webauthnTypes** | [**kotlin.collections.List&lt;WebAuthnType&gt;**](WebAuthnType.md) | List of credential types that user has created | 



