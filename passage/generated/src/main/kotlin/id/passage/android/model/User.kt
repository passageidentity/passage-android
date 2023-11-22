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

import id.passage.android.model.WebAuthnType

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * 
 *
 * @param email 
 * @param emailVerified Whether or not the user's email has been verified
 * @param id 
 * @param phone 
 * @param phoneVerified Whether or not the user's phone has been verified
 * @param status User status: active, inactive, pending
 * @param userMetadata 
 * @param webauthn 
 * @param webauthnTypes 
 */
@JsonClass(generateAdapter = true)

data class User (

    @Json(name = "email")
    val email: kotlin.String,

    /* Whether or not the user's email has been verified */
    @Json(name = "email_verified")
    val emailVerified: kotlin.Boolean,

    @Json(name = "id")
    val id: kotlin.String,

    @Json(name = "phone")
    val phone: kotlin.String,

    /* Whether or not the user's phone has been verified */
    @Json(name = "phone_verified")
    val phoneVerified: kotlin.Boolean,

    /* User status: active, inactive, pending */
    @Json(name = "status")
    val status: User.Status,

    @Json(name = "user_metadata")
    val userMetadata: kotlin.Any?,

    @Json(name = "webauthn")
    val webauthn: kotlin.Boolean,

    @Json(name = "webauthn_types")
    val webauthnTypes: kotlin.collections.List<WebAuthnType>

) {

    /**
     * User status: active, inactive, pending
     *
     * Values: active,inactive,pending
     */
    enum class Status(val value: kotlin.String) {
        @Json(name = "active") active("active"),
        @Json(name = "inactive") inactive("inactive"),
        @Json(name = "pending") pending("pending");
    }
}
