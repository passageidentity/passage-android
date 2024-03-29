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

import id.passage.android.passageflex.model.MagicLinkAuthMethod
import id.passage.android.passageflex.model.OtpAuthMethod

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Denotes what methods this app is allowed to use for authentication with configurations
 *
 * @param passkeys 
 * @param otp 
 * @param magicLink 
 */
@JsonClass(generateAdapter = true)

data class AuthMethods (

    @Json(name = "passkeys")
    val passkeys: kotlin.Any? = null,

    @Json(name = "otp")
    val otp: OtpAuthMethod? = null,

    @Json(name = "magic_link")
    val magicLink: MagicLinkAuthMethod? = null

)

