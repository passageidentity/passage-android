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
 * the nonce to exchange for an authentication token
 *
 * @param nonce 
 */
@JsonClass(generateAdapter = true)

data class Nonce (

    @Json(name = "nonce")
    val nonce: kotlin.String

)

