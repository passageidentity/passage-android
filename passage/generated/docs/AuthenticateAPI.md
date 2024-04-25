# AuthenticateAPI

All URIs are relative to *https://auth.passage.id/v1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**authenticateWebauthnFinishWithTransaction**](AuthenticateAPI.md#authenticateWebauthnFinishWithTransaction) | **POST** /apps/{app_id}/authenticate/transactions/webauthn/finish | Finish WebAuthn authentication with an optional transaction
[**authenticateWebauthnStartWithTransaction**](AuthenticateAPI.md#authenticateWebauthnStartWithTransaction) | **POST** /apps/{app_id}/authenticate/transactions/webauthn/start | Start WebAuthn authentication with an optional transaction


<a name="authenticateWebauthnFinishWithTransaction"></a>
# **authenticateWebauthnFinishWithTransaction**
> Nonce authenticateWebauthnFinishWithTransaction(appId, authenticateWebAuthnFinishWithTransactionRequest)

Finish WebAuthn authentication with an optional transaction

Complete a WebAuthn authentication and authenticate the user via a transaction. This endpoint accepts and verifies the response from &#x60;navigator.credential.get()&#x60; and returns a nonce meant to be exchanged for an authentication token for the user.

### Example
```kotlin
// Import classes:
//import id.passage.client.infrastructure.*
//import id.passage.android.model.*

val apiInstance = AuthenticateAPI()
val appId : kotlin.String = appId_example // kotlin.String | App ID
val authenticateWebAuthnFinishWithTransactionRequest : AuthenticateWebAuthnFinishWithTransactionRequest =  // AuthenticateWebAuthnFinishWithTransactionRequest | 
try {
    val result : Nonce = apiInstance.authenticateWebauthnFinishWithTransaction(appId, authenticateWebAuthnFinishWithTransactionRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AuthenticateAPI#authenticateWebauthnFinishWithTransaction")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AuthenticateAPI#authenticateWebauthnFinishWithTransaction")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **appId** | **kotlin.String**| App ID |
 **authenticateWebAuthnFinishWithTransactionRequest** | [**AuthenticateWebAuthnFinishWithTransactionRequest**](AuthenticateWebAuthnFinishWithTransactionRequest.md)|  |

### Return type

[**Nonce**](Nonce.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="authenticateWebauthnStartWithTransaction"></a>
# **authenticateWebauthnStartWithTransaction**
> AuthenticateWebAuthnStartWithTransactionResponse authenticateWebauthnStartWithTransaction(appId, authenticateWebAuthnStartWithTransactionRequest)

Start WebAuthn authentication with an optional transaction

Initiate a WebAuthn authentication for a user via a transaction. This endpoint creates a WebAuthn credential assertion challenge that is used to perform the authentication ceremony from the browser.

### Example
```kotlin
// Import classes:
//import id.passage.client.infrastructure.*
//import id.passage.android.model.*

val apiInstance = AuthenticateAPI()
val appId : kotlin.String = appId_example // kotlin.String | App ID
val authenticateWebAuthnStartWithTransactionRequest : AuthenticateWebAuthnStartWithTransactionRequest =  // AuthenticateWebAuthnStartWithTransactionRequest | 
try {
    val result : AuthenticateWebAuthnStartWithTransactionResponse = apiInstance.authenticateWebauthnStartWithTransaction(appId, authenticateWebAuthnStartWithTransactionRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AuthenticateAPI#authenticateWebauthnStartWithTransaction")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AuthenticateAPI#authenticateWebauthnStartWithTransaction")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **appId** | **kotlin.String**| App ID |
 **authenticateWebAuthnStartWithTransactionRequest** | [**AuthenticateWebAuthnStartWithTransactionRequest**](AuthenticateWebAuthnStartWithTransactionRequest.md)|  | [optional]

### Return type

[**AuthenticateWebAuthnStartWithTransactionResponse**](AuthenticateWebAuthnStartWithTransactionResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

