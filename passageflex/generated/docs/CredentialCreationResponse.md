
# CredentialCreationResponse

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**authenticatorAttachment** | **kotlin.String** |  |  [optional]
**clientExtensionResults** | [**kotlin.Any**](.md) |  |  [optional]
**id** | **kotlin.String** | ID is The credential&#39;s identifier. The requirements for the identifier are distinct for each type of credential. It might represent a username for username/password tuples, for example. |  [optional]
**rawId** | **kotlin.String** |  |  [optional]
**response** | [**CredentialCreationResponseResponse**](CredentialCreationResponseResponse.md) |  |  [optional]
**transports** | **kotlin.collections.List&lt;kotlin.String&gt;** |  |  [optional]
**type** | **kotlin.String** | Type is the value of the object&#39;s interface object&#39;s [[type]] slot, which specifies the credential type represented by this object. This should be type \&quot;public-key\&quot; for Webauthn credentials. |  [optional]



