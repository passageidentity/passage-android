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


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * 
 *
 * @param alg the algorithm for the key
 * @param e the exponent for the standard pem
 * @param kid the unique identifier for the key
 * @param kty the key type (https://datatracker.ietf.org/doc/html/rfc7518)
 * @param n the modulus for a standard pem
 * @param use how the key is meant to be used (i.e. 'sig' represents signature)
 */
@JsonClass(generateAdapter = true)

data class ApiJWKItem (

    /* the algorithm for the key */
    @Json(name = "alg")
    val alg: kotlin.String? = null,

    /* the exponent for the standard pem */
    @Json(name = "e")
    val e: kotlin.String? = null,

    /* the unique identifier for the key */
    @Json(name = "kid")
    val kid: kotlin.String? = null,

    /* the key type (https://datatracker.ietf.org/doc/html/rfc7518) */
    @Json(name = "kty")
    val kty: kotlin.String? = null,

    /* the modulus for a standard pem */
    @Json(name = "n")
    val n: kotlin.String? = null,

    /* how the key is meant to be used (i.e. 'sig' represents signature) */
    @Json(name = "use")
    val use: kotlin.String? = null

)

