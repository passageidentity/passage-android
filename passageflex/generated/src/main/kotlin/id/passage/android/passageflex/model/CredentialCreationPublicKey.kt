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

import id.passage.android.passageflex.model.CredentialCreationPublicKeyAuthenticatorSelection
import id.passage.android.passageflex.model.CredentialCreationPublicKeyExcludeCredentialsInner
import id.passage.android.passageflex.model.CredentialCreationPublicKeyPubKeyCredParamsInner
import id.passage.android.passageflex.model.CredentialCreationPublicKeyRp
import id.passage.android.passageflex.model.CredentialCreationPublicKeyUser

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * 
 *
 * @param attestation 
 * @param authenticatorSelection 
 * @param challenge 
 * @param excludeCredentials 
 * @param extensions 
 * @param pubKeyCredParams 
 * @param rp 
 * @param timeout 
 * @param user 
 */
@JsonClass(generateAdapter = true)

data class CredentialCreationPublicKey (

    @Json(name = "attestation")
    val attestation: kotlin.String? = null,

    @Json(name = "authenticatorSelection")
    val authenticatorSelection: CredentialCreationPublicKeyAuthenticatorSelection? = null,

    @Json(name = "challenge")
    val challenge: kotlin.String? = null,

    @Json(name = "excludeCredentials")
    val excludeCredentials: kotlin.collections.List<CredentialCreationPublicKeyExcludeCredentialsInner>? = null,

    @Json(name = "extensions")
    val extensions: kotlin.Any? = null,

    @Json(name = "pubKeyCredParams")
    val pubKeyCredParams: kotlin.collections.List<CredentialCreationPublicKeyPubKeyCredParamsInner>? = null,

    @Json(name = "rp")
    val rp: CredentialCreationPublicKeyRp? = null,

    @Json(name = "timeout")
    val timeout: kotlin.Int? = null,

    @Json(name = "user")
    val user: CredentialCreationPublicKeyUser? = null

)

