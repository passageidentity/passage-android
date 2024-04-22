# RegisterAPI

All URIs are relative to *https://auth.passage.id/v1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**registerMagicLink**](RegisterAPI.md#registerMagicLink) | **POST** /apps/{app_id}/register/magic-link | Register with Magic Link
[**registerOneTimePasscode**](RegisterAPI.md#registerOneTimePasscode) | **POST** /apps/{app_id}/register/otp | Register with OTP
[**registerWebauthnFinish**](RegisterAPI.md#registerWebauthnFinish) | **POST** /apps/{app_id}/register/webauthn/finish | Finish WebAuthn Registration
[**registerWebauthnFinishWithTransaction**](RegisterAPI.md#registerWebauthnFinishWithTransaction) | **POST** /apps/{app_id}/register/transactions/webauthn/finish | Finish WebAuthn registration with a transaction
[**registerWebauthnStart**](RegisterAPI.md#registerWebauthnStart) | **POST** /apps/{app_id}/register/webauthn/start | Start WebAuthn Register
[**registerWebauthnStartWithTransaction**](RegisterAPI.md#registerWebauthnStartWithTransaction) | **POST** /apps/{app_id}/register/transactions/webauthn/start | Start WebAuthn registration with a transaction


<a name="registerMagicLink"></a>
# **registerMagicLink**
> RegisterMagicLinkResponse registerMagicLink(appId, user)

Register with Magic Link

Create a user and send an registration email or SMS to the user. The user will receive an email or text with a link to complete their registration.

### Example
```kotlin
// Import classes:
//import id.passage.client.infrastructure.*
//import id.passage.android.model.*

val apiInstance = RegisterAPI()
val appId : kotlin.String = appId_example // kotlin.String | App ID
val user : RegisterMagicLinkRequest =  // RegisterMagicLinkRequest | User Data
try {
    val result : RegisterMagicLinkResponse = apiInstance.registerMagicLink(appId, user)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling RegisterAPI#registerMagicLink")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling RegisterAPI#registerMagicLink")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **appId** | **kotlin.String**| App ID |
 **user** | [**RegisterMagicLinkRequest**](RegisterMagicLinkRequest.md)| User Data |

### Return type

[**RegisterMagicLinkResponse**](RegisterMagicLinkResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="registerOneTimePasscode"></a>
# **registerOneTimePasscode**
> OneTimePasscodeResponse registerOneTimePasscode(appId, registerOneTimePasscodeRequest)

Register with OTP

Create a user and send a registration email or SMS to the user. The user will receive an email or text with a one-time passcode to complete their registration.

### Example
```kotlin
// Import classes:
//import id.passage.client.infrastructure.*
//import id.passage.android.model.*

val apiInstance = RegisterAPI()
val appId : kotlin.String = appId_example // kotlin.String | App ID
val registerOneTimePasscodeRequest : RegisterOneTimePasscodeRequest =  // RegisterOneTimePasscodeRequest | User Data
try {
    val result : OneTimePasscodeResponse = apiInstance.registerOneTimePasscode(appId, registerOneTimePasscodeRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling RegisterAPI#registerOneTimePasscode")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling RegisterAPI#registerOneTimePasscode")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **appId** | **kotlin.String**| App ID |
 **registerOneTimePasscodeRequest** | [**RegisterOneTimePasscodeRequest**](RegisterOneTimePasscodeRequest.md)| User Data |

### Return type

[**OneTimePasscodeResponse**](OneTimePasscodeResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="registerWebauthnFinish"></a>
# **registerWebauthnFinish**
> AuthResponse registerWebauthnFinish(appId, registerWebAuthnFinishRequest)

Finish WebAuthn Registration

Complete a WebAuthn registration and authenticate the user. This endpoint accepts and verifies the response from &#x60;navigator.credential.create()&#x60; and returns an authentication token for the user.

### Example
```kotlin
// Import classes:
//import id.passage.client.infrastructure.*
//import id.passage.android.model.*

val apiInstance = RegisterAPI()
val appId : kotlin.String = appId_example // kotlin.String | App ID
val registerWebAuthnFinishRequest : RegisterWebAuthnFinishRequest =  // RegisterWebAuthnFinishRequest | WebAuthn Response Data
try {
    val result : AuthResponse = apiInstance.registerWebauthnFinish(appId, registerWebAuthnFinishRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling RegisterAPI#registerWebauthnFinish")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling RegisterAPI#registerWebauthnFinish")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **appId** | **kotlin.String**| App ID |
 **registerWebAuthnFinishRequest** | [**RegisterWebAuthnFinishRequest**](RegisterWebAuthnFinishRequest.md)| WebAuthn Response Data |

### Return type

[**AuthResponse**](AuthResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="registerWebauthnFinishWithTransaction"></a>
# **registerWebauthnFinishWithTransaction**
> Nonce registerWebauthnFinishWithTransaction(appId, registerWebAuthnFinishWithTransactionRequest)

Finish WebAuthn registration with a transaction

Complete a WebAuthn registration and authenticate the user via a transaction. This endpoint accepts and verifies the response from &#x60;navigator.credential.create()&#x60; and returns a nonce meant to be exchanged for an authentication token for the user.

### Example
```kotlin
// Import classes:
//import id.passage.client.infrastructure.*
//import id.passage.android.model.*

val apiInstance = RegisterAPI()
val appId : kotlin.String = appId_example // kotlin.String | App ID
val registerWebAuthnFinishWithTransactionRequest : RegisterWebAuthnFinishWithTransactionRequest =  // RegisterWebAuthnFinishWithTransactionRequest | 
try {
    val result : Nonce = apiInstance.registerWebauthnFinishWithTransaction(appId, registerWebAuthnFinishWithTransactionRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling RegisterAPI#registerWebauthnFinishWithTransaction")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling RegisterAPI#registerWebauthnFinishWithTransaction")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **appId** | **kotlin.String**| App ID |
 **registerWebAuthnFinishWithTransactionRequest** | [**RegisterWebAuthnFinishWithTransactionRequest**](RegisterWebAuthnFinishWithTransactionRequest.md)|  |

### Return type

[**Nonce**](Nonce.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="registerWebauthnStart"></a>
# **registerWebauthnStart**
> RegisterWebAuthnStartResponse registerWebauthnStart(appId, registerWebAuthnStartRequest)

Start WebAuthn Register

Initiate a WebAuthn registration and create the user. This endpoint creates a WebAuthn credential creation challenge that is used to perform the registration ceremony from the browser.

### Example
```kotlin
// Import classes:
//import id.passage.client.infrastructure.*
//import id.passage.android.model.*

val apiInstance = RegisterAPI()
val appId : kotlin.String = appId_example // kotlin.String | App ID
val registerWebAuthnStartRequest : RegisterWebAuthnStartRequest =  // RegisterWebAuthnStartRequest | User Data
try {
    val result : RegisterWebAuthnStartResponse = apiInstance.registerWebauthnStart(appId, registerWebAuthnStartRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling RegisterAPI#registerWebauthnStart")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling RegisterAPI#registerWebauthnStart")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **appId** | **kotlin.String**| App ID |
 **registerWebAuthnStartRequest** | [**RegisterWebAuthnStartRequest**](RegisterWebAuthnStartRequest.md)| User Data |

### Return type

[**RegisterWebAuthnStartResponse**](RegisterWebAuthnStartResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="registerWebauthnStartWithTransaction"></a>
# **registerWebauthnStartWithTransaction**
> RegisterWebAuthnStartWithTransactionResponse registerWebauthnStartWithTransaction(appId, registerWebAuthnStartWithTransactionRequest)

Start WebAuthn registration with a transaction

Initiate a WebAuthn registration and create the user via a transaction. This endpoint creates a WebAuthn credential creation challenge that is used to perform the registration ceremony from the browser.

### Example
```kotlin
// Import classes:
//import id.passage.client.infrastructure.*
//import id.passage.android.model.*

val apiInstance = RegisterAPI()
val appId : kotlin.String = appId_example // kotlin.String | App ID
val registerWebAuthnStartWithTransactionRequest : RegisterWebAuthnStartWithTransactionRequest =  // RegisterWebAuthnStartWithTransactionRequest | 
try {
    val result : RegisterWebAuthnStartWithTransactionResponse = apiInstance.registerWebauthnStartWithTransaction(appId, registerWebAuthnStartWithTransactionRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling RegisterAPI#registerWebauthnStartWithTransaction")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling RegisterAPI#registerWebauthnStartWithTransaction")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **appId** | **kotlin.String**| App ID |
 **registerWebAuthnStartWithTransactionRequest** | [**RegisterWebAuthnStartWithTransactionRequest**](RegisterWebAuthnStartWithTransactionRequest.md)|  |

### Return type

[**RegisterWebAuthnStartWithTransactionResponse**](RegisterWebAuthnStartWithTransactionResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

