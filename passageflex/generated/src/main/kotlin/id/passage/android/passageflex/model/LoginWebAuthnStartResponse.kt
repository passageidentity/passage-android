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

import id.passage.android.passageflex.model.CredentialAssertionChallenge1
import id.passage.android.passageflex.model.User

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * 
 *
 * @param handshake 
 * @param user 
 */
@JsonClass(generateAdapter = true)

data class LoginWebAuthnStartResponse (

    @Json(name = "handshake")
    val handshake: CredentialAssertionChallenge1,

    @Json(name = "user")
    val user: User? = null

)

