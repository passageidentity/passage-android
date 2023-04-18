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
 * @param transports The authenticator transports that can be used.
 * @param type The valid credential types.
 */
@JsonClass(generateAdapter = true)

data class ProtocolCredentialDescriptor (

    /* CredentialID The ID of a credential to allow/disallow. */
    @Json(name = "id")
    val id: kotlin.String? = null,

    /* The authenticator transports that can be used. */
    @Json(name = "transports")
    val transports: kotlin.collections.List<kotlin.String>? = null,

    /* The valid credential types. */
    @Json(name = "type")
    val type: kotlin.String? = null

)

