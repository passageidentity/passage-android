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


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * 
 *
 * @param fieldName 
 * @param friendlyName 
 * @param id 
 * @param profile 
 * @param registration 
 * @param type 
 */
@JsonClass(generateAdapter = true)

data class UserMetadataField (

    @Json(name = "field_name")
    val fieldName: kotlin.String,

    @Json(name = "friendly_name")
    val friendlyName: kotlin.String,

    @Json(name = "id")
    val id: kotlin.String,

    @Json(name = "profile")
    val profile: kotlin.Boolean,

    @Json(name = "registration")
    val registration: kotlin.Boolean,

    @Json(name = "type")
    val type: kotlin.String

)

