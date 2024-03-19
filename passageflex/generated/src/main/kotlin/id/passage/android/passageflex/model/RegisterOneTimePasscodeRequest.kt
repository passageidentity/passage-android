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
 * @param identifier valid email or E164 phone number
 * @param language language of the email to send (optional)
 */
@JsonClass(generateAdapter = true)

data class RegisterOneTimePasscodeRequest (

    /* valid email or E164 phone number */
    @Json(name = "identifier")
    val identifier: kotlin.String,

    /* language of the email to send (optional) */
    @Json(name = "language")
    val language: kotlin.String? = null

)

