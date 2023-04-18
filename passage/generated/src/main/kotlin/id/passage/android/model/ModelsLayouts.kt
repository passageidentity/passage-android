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

import id.passage.android.model.ModelsLayoutConfig

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * 
 *
 * @param profile 
 * @param registration 
 */
@JsonClass(generateAdapter = true)

data class ModelsLayouts (

    @Json(name = "profile")
    val profile: kotlin.collections.List<ModelsLayoutConfig>? = null,

    @Json(name = "registration")
    val registration: kotlin.collections.List<ModelsLayoutConfig>? = null

)

