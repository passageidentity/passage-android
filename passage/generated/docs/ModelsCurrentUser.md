
# ModelsCurrentUser

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**createdAt** | **kotlin.String** | When this user was created |  [optional]
**email** | **kotlin.String** | The user&#39;s email |  [optional]
**emailVerified** | **kotlin.Boolean** | Whether or not the user&#39;s email has been verified |  [optional]
**id** | **kotlin.String** | The userID (referred to as Handle) |  [optional]
**lastLoginAt** | **kotlin.String** | The last time this user logged in |  [optional]
**loginCount** | **kotlin.Int** | How many times the user has successfully logged in |  [optional]
**phone** | **kotlin.String** | The user&#39;s phone |  [optional]
**phoneVerified** | **kotlin.Boolean** | Whether or not the user&#39;s phone has been verified |  [optional]
**status** | **kotlin.String** | User status: active, inactive, pending |  [optional]
**updatedAt** | **kotlin.String** | When this user was last updated |  [optional]
**userMetadata** | [**kotlin.Any**](.md) |  |  [optional]
**webauthn** | **kotlin.Boolean** | Whether or not the user has authenticated via webAuthn before (if len(WebAuthnDevices) &gt; 0) |  [optional]
**webauthnDevices** | [**kotlin.collections.List&lt;ModelsCredential&gt;**](ModelsCredential.md) | The list of devices this user has authenticated with via webAuthn |  [optional]
**webauthnTypes** | **kotlin.collections.List&lt;kotlin.String&gt;** | List of credential types that user has created |  [optional]



