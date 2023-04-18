
# ProtocolAuthenticatorSelection

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**authenticatorAttachment** | **kotlin.String** | AuthenticatorAttachment If this member is present, eligible authenticators are filtered to only authenticators attached with the specified AuthenticatorAttachment enum. |  [optional]
**requireResidentKey** | **kotlin.Boolean** | RequireResidentKey this member describes the Relying Party&#39;s requirements regarding resident credentials. If the parameter is set to true, the authenticator MUST create a client-side-resident public key credential source when creating a public key credential. |  [optional]
**residentKey** | **kotlin.String** | ResidentKey this member describes the Relying Party&#39;s requirements regarding resident credentials per Webauthn Level 2. |  [optional]
**userVerification** | **kotlin.String** | UserVerification This member describes the Relying Party&#39;s requirements regarding user verification for the create() operation. Eligible authenticators are filtered to only those capable of satisfying this requirement. |  [optional]



