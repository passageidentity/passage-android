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

import id.passage.android.passageflex.model.Model500Code

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * 
 *
 * @param code 
 * @param error 
 */
@JsonClass(generateAdapter = true)

data class Model500Error (

    @Json(name = "code")
    val code: Model500Code,

    @Json(name = "error")
    val error: kotlin.String

)

