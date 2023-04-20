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

import id.passage.android.model.ProtocolCredentialAssertion

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * 
 *
 * @param challenge 
 * @param id 
 */
@JsonClass(generateAdapter = true)

data class ApiCredentialAssertionChallenge (

    @Json(name = "challenge")
    val challenge: ProtocolCredentialAssertion? = null,

    @Json(name = "id")
    val id: kotlin.String? = null

)

