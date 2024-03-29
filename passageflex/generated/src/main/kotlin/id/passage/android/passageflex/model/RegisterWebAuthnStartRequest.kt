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

package id.passage.android.passageflex.model

import id.passage.android.passageflex.model.AuthenticatorAttachment

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * 
 *
 * @param identifier valid email or E164 phone number
 * @param authenticatorAttachment 
 */
@JsonClass(generateAdapter = true)

data class RegisterWebAuthnStartRequest (

    /* valid email or E164 phone number */
    @Json(name = "identifier")
    val identifier: kotlin.String,

    @Json(name = "authenticator_attachment")
    val authenticatorAttachment: AuthenticatorAttachment? = AuthenticatorAttachment.platform

)

