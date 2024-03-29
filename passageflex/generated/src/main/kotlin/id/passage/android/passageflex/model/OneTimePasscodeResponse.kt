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
 * @param otpId The ID of the one-time passcode. Provide it when activating.
 */
@JsonClass(generateAdapter = true)

data class OneTimePasscodeResponse (

    /* The ID of the one-time passcode. Provide it when activating. */
    @Json(name = "otp_id")
    val otpId: kotlin.String

)

