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
 * @param authenticatorData 
 * @param clientDataJSON From the spec https://www.w3.org/TR/webauthn/#dom-authenticatorresponse-clientdatajson This attribute contains a JSON serialization of the client data passed to the authenticator by the client in its call to either create() or get().
 * @param signature 
 * @param userHandle 
 */
@JsonClass(generateAdapter = true)

data class ProtocolAuthenticatorAssertionResponse (

    @Json(name = "authenticatorData")
    val authenticatorData: kotlin.String? = null,

    /* From the spec https://www.w3.org/TR/webauthn/#dom-authenticatorresponse-clientdatajson This attribute contains a JSON serialization of the client data passed to the authenticator by the client in its call to either create() or get(). */
    @Json(name = "clientDataJSON")
    val clientDataJSON: kotlin.String? = null,

    @Json(name = "signature")
    val signature: kotlin.String? = null,

    @Json(name = "userHandle")
    val userHandle: kotlin.String? = null

)

