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
 * @param refreshToken 
 */
@JsonClass(generateAdapter = true)

data class RefreshAuthTokenRequest (

    @Json(name = "refresh_token")
    val refreshToken: kotlin.String

)

