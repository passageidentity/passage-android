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

import id.passage.android.passageflex.model.CredentialCreation

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * 
 *
 * @param challenge 
 * @param id 
 */
@JsonClass(generateAdapter = true)

data class CredentialCreationChallenge (

    @Json(name = "challenge")
    val challenge: CredentialCreation,

    @Json(name = "id")
    val id: kotlin.String

)

