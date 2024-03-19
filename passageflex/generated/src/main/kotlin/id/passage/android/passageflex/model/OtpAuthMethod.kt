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

import id.passage.android.passageflex.model.TtlDisplayUnit

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * 
 *
 * @param ttl Maximum time (IN SECONDS) for the auth to expire.
 * @param ttlDisplayUnit 
 */
@JsonClass(generateAdapter = true)

data class OtpAuthMethod (

    /* Maximum time (IN SECONDS) for the auth to expire. */
    @Json(name = "ttl")
    val ttl: kotlin.Int? = 300,

    @Json(name = "ttl_display_unit")
    @Deprecated(message = "This property is deprecated.")
    val ttlDisplayUnit: TtlDisplayUnit? = null

)

