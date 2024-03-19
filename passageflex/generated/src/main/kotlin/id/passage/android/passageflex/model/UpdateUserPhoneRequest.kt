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
 * @param language language of the email to send (optional)
 * @param magicLinkPath 
 * @param newPhone 
 * @param redirectUrl 
 */
@JsonClass(generateAdapter = true)

data class UpdateUserPhoneRequest (

    /* language of the email to send (optional) */
    @Json(name = "language")
    val language: kotlin.String? = null,

    @Json(name = "magic_link_path")
    val magicLinkPath: kotlin.String? = null,

    @Json(name = "new_phone")
    val newPhone: kotlin.String? = null,

    @Json(name = "redirect_url")
    val redirectUrl: kotlin.String? = null

)

