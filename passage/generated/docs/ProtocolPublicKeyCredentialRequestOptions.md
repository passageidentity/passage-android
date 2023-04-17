
# ProtocolPublicKeyCredentialRequestOptions

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**allowCredentials** | [**kotlin.collections.List&lt;ProtocolCredentialDescriptor&gt;**](ProtocolCredentialDescriptor.md) |  |  [optional]
**challenge** | **kotlin.collections.List&lt;kotlin.Int&gt;** | AttestationObject is the byte slice version of attestationObject. This attribute contains an attestation object, which is opaque to, and cryptographically protected against tampering by, the client. The attestation object contains both authenticator data and an attestation statement. The former contains the AAGUID, a unique credential ID, and the credential public key. The contents of the attestation statement are determined by the attestation statement format used by the authenticator. It also contains any additional information that the Relying Party&#39;s server requires to validate the attestation statement, as well as to decode and validate the authenticator data along with the JSON-serialized client data. |  [optional]
**extensions** | [**kotlin.Any**](.md) |  |  [optional]
**rpId** | **kotlin.String** |  |  [optional]
**timeout** | **kotlin.Int** |  |  [optional]
**userVerification** | **kotlin.String** | UserVerification This member describes the Relying Party&#39;s requirements regarding user verification for the create() operation. Eligible authenticators are filtered to only those capable of satisfying this requirement. |  [optional]



