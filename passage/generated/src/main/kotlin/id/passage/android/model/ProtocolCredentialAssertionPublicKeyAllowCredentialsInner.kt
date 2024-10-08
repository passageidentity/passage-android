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
 * @param id CredentialID The ID of a credential to allow/disallow.
 * @param type The valid credential types.
 * @param transports The authenticator transports that can be used.
 */
@JsonClass(generateAdapter = true)

data class ProtocolCredentialAssertionPublicKeyAllowCredentialsInner (

    /* CredentialID The ID of a credential to allow/disallow. */
    @Json(name = "id")
    val id: kotlin.String,

    /* The valid credential types. */
    @Json(name = "type")
    val type: kotlin.String,

    /* The authenticator transports that can be used. */
    @Json(name = "transports")
    val transports: kotlin.collections.List<kotlin.String>? = null

)

