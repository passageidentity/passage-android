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

import id.passage.android.passageflex.model.AuthMethods
import id.passage.android.passageflex.model.ElementCustomization
import id.passage.android.passageflex.model.Layouts
import id.passage.android.passageflex.model.SocialConnections
import id.passage.android.passageflex.model.UserMetadataField

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * 
 *
 * @param allowedIdentifier 
 * @param authFallbackMethod Deprecated Property. Please refer to `auth_methods` to view settings for individual authentication methods.
 * @param authFallbackMethodTtl Deprecated Property. Please refer to `auth_methods` to view settings for individual authentication methods.
 * @param authMethods 
 * @param authOrigin 
 * @param defaultLanguage 
 * @param elementCustomization 
 * @param elementCustomizationDark 
 * @param ephemeral 
 * @param id 
 * @param layouts 
 * @param loginUrl 
 * @param name 
 * @param passageBranding 
 * @param publicSignup 
 * @param profileManagement 
 * @param redirectUrl 
 * @param requireEmailVerification 
 * @param requireIdentifierVerification 
 * @param requiredIdentifier 
 * @param rsaPublicKey 
 * @param sessionTimeoutLength 
 * @param socialConnections 
 * @param userMetadataSchema 
 */
@JsonClass(generateAdapter = true)

data class App (

    @Json(name = "allowed_identifier")
    val allowedIdentifier: kotlin.String,

    /* Deprecated Property. Please refer to `auth_methods` to view settings for individual authentication methods. */
    @Json(name = "auth_fallback_method")
    @Deprecated(message = "This property is deprecated.")
    val authFallbackMethod: App.AuthFallbackMethod,

    /* Deprecated Property. Please refer to `auth_methods` to view settings for individual authentication methods. */
    @Json(name = "auth_fallback_method_ttl")
    @Deprecated(message = "This property is deprecated.")
    val authFallbackMethodTtl: kotlin.Int,

    @Json(name = "auth_methods")
    val authMethods: AuthMethods,

    @Json(name = "auth_origin")
    val authOrigin: kotlin.String,

    @Json(name = "default_language")
    val defaultLanguage: kotlin.String,

    @Json(name = "element_customization")
    val elementCustomization: ElementCustomization,

    @Json(name = "element_customization_dark")
    val elementCustomizationDark: ElementCustomization,

    @Json(name = "ephemeral")
    val ephemeral: kotlin.Boolean,

    @Json(name = "id")
    val id: kotlin.String,

    @Json(name = "layouts")
    val layouts: Layouts,

    @Json(name = "login_url")
    val loginUrl: kotlin.String,

    @Json(name = "name")
    val name: kotlin.String,

    @Json(name = "passage_branding")
    val passageBranding: kotlin.Boolean,

    @Json(name = "public_signup")
    val publicSignup: kotlin.Boolean,

    @Json(name = "profile_management")
    val profileManagement: kotlin.Boolean,

    @Json(name = "redirect_url")
    val redirectUrl: kotlin.String,

    @Json(name = "require_email_verification")
    val requireEmailVerification: kotlin.Boolean,

    @Json(name = "require_identifier_verification")
    val requireIdentifierVerification: kotlin.Boolean,

    @Json(name = "required_identifier")
    val requiredIdentifier: kotlin.String,

    @Json(name = "rsa_public_key")
    val rsaPublicKey: kotlin.String,

    @Json(name = "session_timeout_length")
    val sessionTimeoutLength: kotlin.Int,

    @Json(name = "social_connections")
    val socialConnections: SocialConnections,

    @Json(name = "user_metadata_schema")
    val userMetadataSchema: kotlin.collections.List<UserMetadataField>

) {

    /**
     * Deprecated Property. Please refer to `auth_methods` to view settings for individual authentication methods.
     *
     * Values: magicLink,otp,none
     */
    enum class AuthFallbackMethod(val value: kotlin.String) {
        @Json(name = "magic_link") magicLink("magic_link"),
        @Json(name = "otp") otp("otp"),
        @Json(name = "none") none("none");
    }
}

