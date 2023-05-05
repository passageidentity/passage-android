/**
 *
 * Please note:
 * This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * Do not edit this file manually.
 *
 */

@file:Suppress(
    "ArrayInDataClass",
    "EnumEntryName",
    "RemoveRedundantQualifierName",
    "UnusedImport"
)

package id.passage.android.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * 
 *
 * @param authenticatorAttachment AuthenticatorAttachment If this member is present, eligible authenticators are filtered to only authenticators attached with the specified AuthenticatorAttachment enum.
 * @param requireResidentKey RequireResidentKey this member describes the Relying Party's requirements regarding resident credentials. If the parameter is set to true, the authenticator MUST create a client-side-resident public key credential source when creating a public key credential.
 * @param residentKey ResidentKey this member describes the Relying Party's requirements regarding resident credentials per Webauthn Level 2.
 * @param userVerification UserVerification This member describes the Relying Party's requirements regarding user verification for the create() operation. Eligible authenticators are filtered to only those capable of satisfying this requirement.
 */
@JsonClass(generateAdapter = true)

data class ProtocolCredentialCreationPublicKeyAuthenticatorSelection (

    /* AuthenticatorAttachment If this member is present, eligible authenticators are filtered to only authenticators attached with the specified AuthenticatorAttachment enum. */
    @Json(name = "authenticatorAttachment")
    val authenticatorAttachment: kotlin.String? = null,

    /* RequireResidentKey this member describes the Relying Party's requirements regarding resident credentials. If the parameter is set to true, the authenticator MUST create a client-side-resident public key credential source when creating a public key credential. */
    @Json(name = "requireResidentKey")
    val requireResidentKey: kotlin.Boolean? = null,

    /* ResidentKey this member describes the Relying Party's requirements regarding resident credentials per Webauthn Level 2. */
    @Json(name = "residentKey")
    val residentKey: kotlin.String? = null,

    /* UserVerification This member describes the Relying Party's requirements regarding user verification for the create() operation. Eligible authenticators are filtered to only those capable of satisfying this requirement. */
    @Json(name = "userVerification")
    val userVerification: kotlin.String? = null

)

