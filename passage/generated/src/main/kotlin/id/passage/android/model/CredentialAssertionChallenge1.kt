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

import id.passage.android.model.ProtocolCredentialAssertion1

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * 
 *
 * @param challenge 
 * @param id 
 */
@JsonClass(generateAdapter = true)

data class CredentialAssertionChallenge1 (

    @Json(name = "challenge")
    val challenge: ProtocolCredentialAssertion1,

    @Json(name = "id")
    val id: kotlin.String

)
