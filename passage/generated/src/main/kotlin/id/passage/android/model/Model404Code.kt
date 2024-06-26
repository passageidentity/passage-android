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

/**
 * 
 *
 * Values: appNotFound,userNotFound,magicLinkNotFound,socialConnectionNotFound,transactionNotFound
 */

enum class Model404Code(val value: kotlin.String) {

    @Json(name = "app_not_found")
    appNotFound("app_not_found"),

    @Json(name = "user_not_found")
    userNotFound("user_not_found"),

    @Json(name = "magic_link_not_found")
    magicLinkNotFound("magic_link_not_found"),

    @Json(name = "social_connection_not_found")
    socialConnectionNotFound("social_connection_not_found"),

    @Json(name = "transaction_not_found")
    transactionNotFound("transaction_not_found");

    /**
     * Override toString() to avoid using the enum variable name as the value, and instead use
     * the actual value defined in the API spec file.
     *
     * This solves a problem when the variable name and its value are different, and ensures that
     * the client sends the correct enum values to the server always.
     */
    override fun toString(): String = value

    companion object {
        /**
         * Converts the provided [data] to a [String] on success, null otherwise.
         */
        fun encode(data: kotlin.Any?): kotlin.String? = if (data is Model404Code) "$data" else null

        /**
         * Returns a valid [Model404Code] for [data], null otherwise.
         */
        fun decode(data: kotlin.Any?): Model404Code? = data?.let {
          val normalizedData = "$it".lowercase()
          values().firstOrNull { value ->
            it == value || normalizedData == "$value".lowercase()
          }
        }
    }
}

