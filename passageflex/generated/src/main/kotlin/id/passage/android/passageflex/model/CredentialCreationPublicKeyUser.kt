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


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * 
 *
 * @param displayName A human-palatable name for the user account, intended only for display. For example, \"Alex P. Müller\" or \"田中 倫\". The Relying Party SHOULD let the user choose this, and SHOULD NOT restrict the choice more than necessary.
 * @param icon A serialized URL which resolves to an image associated with the entity. For example, this could be a user’s avatar or a Relying Party's logo. This URL MUST be an a priori authenticated URL. Authenticators MUST accept and store a 128-byte minimum length for an icon member’s value. Authenticators MAY ignore an icon member’s value if its length is greater than 128 bytes. The URL’s scheme MAY be \"data\" to avoid fetches of the URL, at the cost of needing more storage.  Deprecated: this has been removed from the specification recommendations.
 * @param id ID is the user handle of the user account entity. To ensure secure operation, authentication and authorization decisions MUST be made on the basis of this id member, not the displayName nor name members. See Section 6.1 of [RFC8266](https://www.w3.org/TR/webauthn/#biblio-rfc8266).
 * @param name A human-palatable name for the entity. Its function depends on what the PublicKeyCredentialEntity represents:  When inherited by PublicKeyCredentialRpEntity it is a human-palatable identifier for the Relying Party, intended only for display. For example, \"ACME Corporation\", \"Wonderful Widgets, Inc.\" or \"ОАО Примертех\".  When inherited by PublicKeyCredentialUserEntity, it is a human-palatable identifier for a user account. It is intended only for display, i.e., aiding the user in determining the difference between user accounts with similar displayNames. For example, \"alexm\", \"alex.p.mueller@example.com\" or \"+14255551234\".
 */
@JsonClass(generateAdapter = true)

data class CredentialCreationPublicKeyUser (

    /* A human-palatable name for the user account, intended only for display. For example, \"Alex P. Müller\" or \"田中 倫\". The Relying Party SHOULD let the user choose this, and SHOULD NOT restrict the choice more than necessary. */
    @Json(name = "displayName")
    val displayName: kotlin.String? = null,

    /* A serialized URL which resolves to an image associated with the entity. For example, this could be a user’s avatar or a Relying Party's logo. This URL MUST be an a priori authenticated URL. Authenticators MUST accept and store a 128-byte minimum length for an icon member’s value. Authenticators MAY ignore an icon member’s value if its length is greater than 128 bytes. The URL’s scheme MAY be \"data\" to avoid fetches of the URL, at the cost of needing more storage.  Deprecated: this has been removed from the specification recommendations. */
    @Json(name = "icon")
    val icon: kotlin.String? = null,

    /* ID is the user handle of the user account entity. To ensure secure operation, authentication and authorization decisions MUST be made on the basis of this id member, not the displayName nor name members. See Section 6.1 of [RFC8266](https://www.w3.org/TR/webauthn/#biblio-rfc8266). */
    @Json(name = "id")
    val id: kotlin.String? = null,

    /* A human-palatable name for the entity. Its function depends on what the PublicKeyCredentialEntity represents:  When inherited by PublicKeyCredentialRpEntity it is a human-palatable identifier for the Relying Party, intended only for display. For example, \"ACME Corporation\", \"Wonderful Widgets, Inc.\" or \"ОАО Примертех\".  When inherited by PublicKeyCredentialUserEntity, it is a human-palatable identifier for a user account. It is intended only for display, i.e., aiding the user in determining the difference between user accounts with similar displayNames. For example, \"alexm\", \"alex.p.mueller@example.com\" or \"+14255551234\". */
    @Json(name = "name")
    val name: kotlin.String? = null

)

