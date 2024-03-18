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

import id.passage.android.passageflex.model.Credential
import id.passage.android.passageflex.model.UserSocialConnections
import id.passage.android.passageflex.model.UserStatus
import id.passage.android.passageflex.model.WebAuthnType

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * 
 *
 * @param createdAt When this user was created
 * @param email The user's email
 * @param emailVerified Whether or not the user's email has been verified
 * @param id The user ID
 * @param lastLoginAt The last time this user logged in
 * @param loginCount How many times the user has successfully logged in
 * @param phone The user's phone
 * @param phoneVerified Whether or not the user's phone has been verified
 * @param socialConnections 
 * @param status 
 * @param updatedAt When this user was last updated
 * @param userMetadata 
 * @param webauthn Whether or not the user has authenticated via webAuthn before (if len(WebAuthnDevices) > 0)
 * @param webauthnDevices The list of devices this user has authenticated with via webAuthn
 * @param webauthnTypes List of credential types that user has created
 */
@JsonClass(generateAdapter = true)

data class CurrentUser (

    /* When this user was created */
    @Json(name = "created_at")
    val createdAt: kotlin.String,

    /* The user's email */
    @Json(name = "email")
    val email: kotlin.String,

    /* Whether or not the user's email has been verified */
    @Json(name = "email_verified")
    val emailVerified: kotlin.Boolean,

    /* The user ID */
    @Json(name = "id")
    val id: kotlin.String,

    /* The last time this user logged in */
    @Json(name = "last_login_at")
    val lastLoginAt: kotlin.String,

    /* How many times the user has successfully logged in */
    @Json(name = "login_count")
    val loginCount: kotlin.Int,

    /* The user's phone */
    @Json(name = "phone")
    val phone: kotlin.String,

    /* Whether or not the user's phone has been verified */
    @Json(name = "phone_verified")
    val phoneVerified: kotlin.Boolean,

    @Json(name = "social_connections")
    val socialConnections: UserSocialConnections,

    @Json(name = "status")
    val status: UserStatus,

    /* When this user was last updated */
    @Json(name = "updated_at")
    val updatedAt: kotlin.String,

    @Json(name = "user_metadata")
    val userMetadata: kotlin.Any?,

    /* Whether or not the user has authenticated via webAuthn before (if len(WebAuthnDevices) > 0) */
    @Json(name = "webauthn")
    val webauthn: kotlin.Boolean,

    /* The list of devices this user has authenticated with via webAuthn */
    @Json(name = "webauthn_devices")
    val webauthnDevices: kotlin.collections.List<Credential>,

    /* List of credential types that user has created */
    @Json(name = "webauthn_types")
    val webauthnTypes: kotlin.collections.List<WebAuthnType>

)
