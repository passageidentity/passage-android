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
 * @param alg 
 * @param type 
 */
@JsonClass(generateAdapter = true)

data class ProtocolCredentialParameter (

    @Json(name = "alg")
    val alg: kotlin.Int? = null,

    @Json(name = "type")
    val type: kotlin.String? = null

)
