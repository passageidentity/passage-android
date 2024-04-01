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

import id.passage.android.passageflex.model.CredentialCreationResponseResponse

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * 
 *
 * @param authenticatorAttachment 
 * @param clientExtensionResults 
 * @param id ID is The credential's identifier. The requirements for the identifier are distinct for each type of credential. It might represent a username for username/password tuples, for example.
 * @param rawId 
 * @param response 
 * @param transports 
 * @param type Type is the value of the object's interface object's [[type]] slot, which specifies the credential type represented by this object. This should be type \"public-key\" for Webauthn credentials.
 */
@JsonClass(generateAdapter = true)

data class CredentialCreationResponse (

    @Json(name = "authenticatorAttachment")
    val authenticatorAttachment: kotlin.String? = null,

    @Json(name = "clientExtensionResults")
    val clientExtensionResults: kotlin.Any? = null,

    /* ID is The credential's identifier. The requirements for the identifier are distinct for each type of credential. It might represent a username for username/password tuples, for example. */
    @Json(name = "id")
    val id: kotlin.String? = null,

    @Json(name = "rawId")
    val rawId: kotlin.String? = null,

    @Json(name = "response")
    val response: CredentialCreationResponseResponse? = null,

    @Json(name = "transports")
    val transports: kotlin.collections.List<kotlin.String>? = null,

    /* Type is the value of the object's interface object's [[type]] slot, which specifies the credential type represented by this object. This should be type \"public-key\" for Webauthn credentials. */
    @Json(name = "type")
    val type: kotlin.String? = null

)

