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

import id.passage.android.model.EmailSmsAuthMethod

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Denotes what methods this app is allowed to use for authentication with configurations
 *
 * @param passkey 
 * @param otp 
 * @param magicLink 
 */
@JsonClass(generateAdapter = true)

data class AuthMethods (

    @Json(name = "passkey")
    val passkey: kotlin.Any? = null,

    @Json(name = "otp")
    val otp: EmailSmsAuthMethod? = null,

    @Json(name = "magic_link")
    val magicLink: EmailSmsAuthMethod? = null

)

