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

import id.passage.android.model.ProtocolCredentialCreationPublicKeyAuthenticatorSelection
import id.passage.android.model.ProtocolCredentialCreationPublicKeyExcludeCredentialsInner
import id.passage.android.model.ProtocolCredentialCreationPublicKeyPubKeyCredParamsInner
import id.passage.android.model.ProtocolCredentialCreationPublicKeyRp
import id.passage.android.model.ProtocolCredentialCreationPublicKeyUser

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

data class ProtocolCredentialCreationPublicKey (

    @Json(name = "attestation")
    val attestation: kotlin.String? = null,

    @Json(name = "authenticatorSelection")
    val authenticatorSelection: ProtocolCredentialCreationPublicKeyAuthenticatorSelection? = null,

    @Json(name = "challenge")
    val challenge: kotlin.String? = null,

    @Json(name = "excludeCredentials")
    val excludeCredentials: kotlin.collections.List<ProtocolCredentialCreationPublicKeyExcludeCredentialsInner>? = null,

    @Json(name = "extensions")
    val extensions: kotlin.Any? = null,

    @Json(name = "pubKeyCredParams")
    val pubKeyCredParams: kotlin.collections.List<ProtocolCredentialCreationPublicKeyPubKeyCredParamsInner>? = null,

    @Json(name = "rp")
    val rp: ProtocolCredentialCreationPublicKeyRp? = null,

    @Json(name = "timeout")
    val timeout: kotlin.Int? = null,

    @Json(name = "user")
    val user: ProtocolCredentialCreationPublicKeyUser? = null

)
