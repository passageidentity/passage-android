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

import id.passage.android.model.WebAuthnIcons
import id.passage.android.model.WebAuthnType

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * 
 *
 * @param createdAt The first time this webAuthn device was used to authenticate the user
 * @param credId The CredID for this webAuthn device (encoded to match what is stored in psg_cred_obj)
 * @param friendlyName The friendly name for the webAuthn device used to authenticate
 * @param id The ID of the webAuthn device used for authentication
 * @param lastLoginAt The last time this webAuthn device was used to authenticate the user
 * @param type 
 * @param updatedAt The last time this webAuthn device was updated
 * @param usageCount How many times this webAuthn device has been used to authenticate the user
 * @param userId The UserID for this webAuthn device
 * @param icons 
 */
@JsonClass(generateAdapter = true)

data class Credential (

    /* The first time this webAuthn device was used to authenticate the user */
    @Json(name = "created_at")
    val createdAt: kotlin.String,

    /* The CredID for this webAuthn device (encoded to match what is stored in psg_cred_obj) */
    @Json(name = "cred_id")
    val credId: kotlin.String,

    /* The friendly name for the webAuthn device used to authenticate */
    @Json(name = "friendly_name")
    val friendlyName: kotlin.String,

    /* The ID of the webAuthn device used for authentication */
    @Json(name = "id")
    val id: kotlin.String,

    /* The last time this webAuthn device was used to authenticate the user */
    @Json(name = "last_login_at")
    val lastLoginAt: kotlin.String,

    @Json(name = "type")
    val type: WebAuthnType,

    /* The last time this webAuthn device was updated */
    @Json(name = "updated_at")
    val updatedAt: kotlin.String,

    /* How many times this webAuthn device has been used to authenticate the user */
    @Json(name = "usage_count")
    val usageCount: kotlin.Int,

    /* The UserID for this webAuthn device */
    @Json(name = "user_id")
    val userId: kotlin.String,

    @Json(name = "icons")
    val icons: WebAuthnIcons

)
