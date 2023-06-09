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

import id.passage.android.model.ModelsCredential

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * 
 *
 * @param createdAt When this user was created
 * @param email The user's email
 * @param emailVerified Whether or not the user's email has been verified
 * @param id The userID (referred to as Handle)
 * @param lastLoginAt The last time this user logged in
 * @param loginCount How many times the user has successfully logged in
 * @param phone The user's phone
 * @param phoneVerified Whether or not the user's phone has been verified
 * @param status User status: active, inactive, pending
 * @param updatedAt When this user was last updated
 * @param userMetadata 
 * @param webauthn Whether or not the user has authenticated via webAuthn before (if len(WebAuthnDevices) > 0)
 * @param webauthnDevices The list of devices this user has authenticated with via webAuthn
 * @param webauthnTypes List of credential types that user has created
 */
@JsonClass(generateAdapter = true)

data class ModelsCurrentUser (

    /* When this user was created */
    @Json(name = "created_at")
    val createdAt: kotlin.String? = null,

    /* The user's email */
    @Json(name = "email")
    val email: kotlin.String? = null,

    /* Whether or not the user's email has been verified */
    @Json(name = "email_verified")
    val emailVerified: kotlin.Boolean? = null,

    /* The userID (referred to as Handle) */
    @Json(name = "id")
    val id: kotlin.String? = null,

    /* The last time this user logged in */
    @Json(name = "last_login_at")
    val lastLoginAt: kotlin.String? = null,

    /* How many times the user has successfully logged in */
    @Json(name = "login_count")
    val loginCount: kotlin.Int? = null,

    /* The user's phone */
    @Json(name = "phone")
    val phone: kotlin.String? = null,

    /* Whether or not the user's phone has been verified */
    @Json(name = "phone_verified")
    val phoneVerified: kotlin.Boolean? = null,

    /* User status: active, inactive, pending */
    @Json(name = "status")
    val status: kotlin.String? = null,

    /* When this user was last updated */
    @Json(name = "updated_at")
    val updatedAt: kotlin.String? = null,

    @Json(name = "user_metadata")
    val userMetadata: kotlin.Any? = null,

    /* Whether or not the user has authenticated via webAuthn before (if len(WebAuthnDevices) > 0) */
    @Json(name = "webauthn")
    val webauthn: kotlin.Boolean? = null,

    /* The list of devices this user has authenticated with via webAuthn */
    @Json(name = "webauthn_devices")
    val webauthnDevices: kotlin.collections.List<ModelsCredential>? = null,

    /* List of credential types that user has created */
    @Json(name = "webauthn_types")
    val webauthnTypes: kotlin.collections.List<kotlin.String>? = null

)

