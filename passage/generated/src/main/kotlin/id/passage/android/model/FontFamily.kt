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
 * Body font family
 *
 * Values: helvetica,arial,arialBlack,verdana,tahoma,trebuchetMS,impact,gillSans,timesNewRoman,georgia,palatino,baskerville,andaléMono,courier,lucida,monaco,bradleyHand,brushScriptMT,luminari,comicSansMS
 */

enum class FontFamily(val value: kotlin.String) {

    @Json(name = "Helvetica")
    helvetica("Helvetica"),

    @Json(name = "Arial")
    arial("Arial"),

    @Json(name = "Arial Black")
    arialBlack("Arial Black"),

    @Json(name = "Verdana")
    verdana("Verdana"),

    @Json(name = "Tahoma")
    tahoma("Tahoma"),

    @Json(name = "Trebuchet MS")
    trebuchetMS("Trebuchet MS"),

    @Json(name = "Impact")
    impact("Impact"),

    @Json(name = "Gill Sans")
    gillSans("Gill Sans"),

    @Json(name = "Times New Roman")
    timesNewRoman("Times New Roman"),

    @Json(name = "Georgia")
    georgia("Georgia"),

    @Json(name = "Palatino")
    palatino("Palatino"),

    @Json(name = "Baskerville")
    baskerville("Baskerville"),

    @Json(name = "Andalé Mono")
    andaléMono("Andalé Mono"),

    @Json(name = "Courier")
    courier("Courier"),

    @Json(name = "Lucida")
    lucida("Lucida"),

    @Json(name = "Monaco")
    monaco("Monaco"),

    @Json(name = "Bradley Hand")
    bradleyHand("Bradley Hand"),

    @Json(name = "Brush Script MT")
    brushScriptMT("Brush Script MT"),

    @Json(name = "Luminari")
    luminari("Luminari"),

    @Json(name = "Comic Sans MS")
    comicSansMS("Comic Sans MS");

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
        fun encode(data: kotlin.Any?): kotlin.String? = if (data is FontFamily) "$data" else null

        /**
         * Returns a valid [FontFamily] for [data], null otherwise.
         */
        fun decode(data: kotlin.Any?): FontFamily? = data?.let {
          val normalizedData = "$it".lowercase()
          values().firstOrNull { value ->
            it == value || normalizedData == "$value".lowercase()
          }
        }
    }
}

