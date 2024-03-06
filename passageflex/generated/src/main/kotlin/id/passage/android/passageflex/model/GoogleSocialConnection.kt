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
 * @param providerId The external ID of the Social Connection.
 * @param createdAt 
 * @param lastLoginAt 
 * @param providerIdentifier The email of connected social user.
 */
@JsonClass(generateAdapter = true)

data class GoogleSocialConnection (

    /* The external ID of the Social Connection. */
    @Json(name = "provider_id")
    val providerId: kotlin.String,

    @Json(name = "created_at")
    val createdAt: java.time.OffsetDateTime,

    @Json(name = "last_login_at")
    val lastLoginAt: java.time.OffsetDateTime,

    /* The email of connected social user. */
    @Json(name = "provider_identifier")
    val providerIdentifier: kotlin.String

)

