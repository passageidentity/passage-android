# MagicLinkAPI

All URIs are relative to *https://virtserver.swaggerhub.com/passage_swagger/auth-gw/v1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**activateMagicLink**](MagicLinkAPI.md#activateMagicLink) | **PATCH** /apps/{app_id}/magic-link/activate/ | Authenticate Magic Link
[**activateMagicLinkWebauthnLoginFinish**](MagicLinkAPI.md#activateMagicLinkWebauthnLoginFinish) | **POST** /apps/{app_id}/magic-link/webauthn/login/finish | Finish a WebAuthn registration, initiated by a magic link.
[**activateMagicLinkWebauthnLoginStart**](MagicLinkAPI.md#activateMagicLinkWebauthnLoginStart) | **POST** /apps/{app_id}/magic-link/webauthn/login/start/ | Authenticate the user via magic link, then initiate a WebAuthn login.
[**activateMagicLinkWebauthnNewDeviceFinish**](MagicLinkAPI.md#activateMagicLinkWebauthnNewDeviceFinish) | **POST** /apps/{app_id}/magic-link/webauthn/new/finish | Finish WebAuthn registration that was initiated from a magic link.
[**activateMagicLinkWebauthnNewDeviceStart**](MagicLinkAPI.md#activateMagicLinkWebauthnNewDeviceStart) | **POST** /apps/{app_id}/magic-link/webauthn/new/start | Authenticate the user via magic link, then initiate a WebAuthn registration.
[**magicLinkStatus**](MagicLinkAPI.md#magicLinkStatus) | **POST** /apps/{app_id}/magic-link/status/ | Magic Link Status


<a name="activateMagicLink"></a>
# **activateMagicLink**
> ApiAuthResponse activateMagicLink(appId, user)

Authenticate Magic Link

Authenticate a magic link for a user. This endpoint checks that the magic link is valid, then returns an authentication token for the user.

### Example
```kotlin
// Import classes:
//import id.passage.client.infrastructure.*
//import id.passage.android.model.*

val apiInstance = MagicLinkAPI()
val appId : kotlin.String = appId_example // kotlin.String | App ID
val user : ApiactivateMagicLinkRequest =  // ApiactivateMagicLinkRequest | User Data
try {
    val result : ApiAuthResponse = apiInstance.activateMagicLink(appId, user)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling MagicLinkAPI#activateMagicLink")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling MagicLinkAPI#activateMagicLink")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **appId** | **kotlin.String**| App ID |
 **user** | [**ApiactivateMagicLinkRequest**](ApiactivateMagicLinkRequest.md)| User Data |

### Return type

[**ApiAuthResponse**](ApiAuthResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="activateMagicLinkWebauthnLoginFinish"></a>
# **activateMagicLinkWebauthnLoginFinish**
> AuthResponse1 activateMagicLinkWebauthnLoginFinish(appId, magicLinkLoginWebAuthnFinishRequest)

Finish a WebAuthn registration, initiated by a magic link.

### Example
```kotlin
// Import classes:
//import id.passage.client.infrastructure.*
//import id.passage.android.model.*

val apiInstance = MagicLinkAPI()
val appId : kotlin.String = appId_example // kotlin.String | App ID
val magicLinkLoginWebAuthnFinishRequest : MagicLinkLoginWebAuthnFinishRequest =  // MagicLinkLoginWebAuthnFinishRequest | User Data
try {
    val result : AuthResponse1 = apiInstance.activateMagicLinkWebauthnLoginFinish(appId, magicLinkLoginWebAuthnFinishRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling MagicLinkAPI#activateMagicLinkWebauthnLoginFinish")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling MagicLinkAPI#activateMagicLinkWebauthnLoginFinish")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **appId** | **kotlin.String**| App ID |
 **magicLinkLoginWebAuthnFinishRequest** | [**MagicLinkLoginWebAuthnFinishRequest**](MagicLinkLoginWebAuthnFinishRequest.md)| User Data |

### Return type

[**AuthResponse1**](AuthResponse1.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="activateMagicLinkWebauthnLoginStart"></a>
# **activateMagicLinkWebauthnLoginStart**
> ApimagicLinkLoginWebAuthnStartResponse activateMagicLinkWebauthnLoginStart(appId, user)

Authenticate the user via magic link, then initiate a WebAuthn login.

### Example
```kotlin
// Import classes:
//import id.passage.client.infrastructure.*
//import id.passage.android.model.*

val apiInstance = MagicLinkAPI()
val appId : kotlin.String = appId_example // kotlin.String | App ID
val user : ApiactivateMagicLinkRequest =  // ApiactivateMagicLinkRequest | User Data
try {
    val result : ApimagicLinkLoginWebAuthnStartResponse = apiInstance.activateMagicLinkWebauthnLoginStart(appId, user)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling MagicLinkAPI#activateMagicLinkWebauthnLoginStart")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling MagicLinkAPI#activateMagicLinkWebauthnLoginStart")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **appId** | **kotlin.String**| App ID |
 **user** | [**ApiactivateMagicLinkRequest**](ApiactivateMagicLinkRequest.md)| User Data |

### Return type

[**ApimagicLinkLoginWebAuthnStartResponse**](ApimagicLinkLoginWebAuthnStartResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="activateMagicLinkWebauthnNewDeviceFinish"></a>
# **activateMagicLinkWebauthnNewDeviceFinish**
> AuthResponse1 activateMagicLinkWebauthnNewDeviceFinish(appId, magicLinkNewDeviceWebAuthnFinishRequest)

Finish WebAuthn registration that was initiated from a magic link.

### Example
```kotlin
// Import classes:
//import id.passage.client.infrastructure.*
//import id.passage.android.model.*

val apiInstance = MagicLinkAPI()
val appId : kotlin.String = appId_example // kotlin.String | App ID
val magicLinkNewDeviceWebAuthnFinishRequest : MagicLinkNewDeviceWebAuthnFinishRequest =  // MagicLinkNewDeviceWebAuthnFinishRequest | User Data
try {
    val result : AuthResponse1 = apiInstance.activateMagicLinkWebauthnNewDeviceFinish(appId, magicLinkNewDeviceWebAuthnFinishRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling MagicLinkAPI#activateMagicLinkWebauthnNewDeviceFinish")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling MagicLinkAPI#activateMagicLinkWebauthnNewDeviceFinish")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **appId** | **kotlin.String**| App ID |
 **magicLinkNewDeviceWebAuthnFinishRequest** | [**MagicLinkNewDeviceWebAuthnFinishRequest**](MagicLinkNewDeviceWebAuthnFinishRequest.md)| User Data |

### Return type

[**AuthResponse1**](AuthResponse1.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="activateMagicLinkWebauthnNewDeviceStart"></a>
# **activateMagicLinkWebauthnNewDeviceStart**
> MagicLinkNewDeviceWebAuthnStartResponse activateMagicLinkWebauthnNewDeviceStart(appId, activateMagicLinkRequest)

Authenticate the user via magic link, then initiate a WebAuthn registration.

### Example
```kotlin
// Import classes:
//import id.passage.client.infrastructure.*
//import id.passage.android.model.*

val apiInstance = MagicLinkAPI()
val appId : kotlin.String = appId_example // kotlin.String | App ID
val activateMagicLinkRequest : ActivateMagicLinkRequest =  // ActivateMagicLinkRequest | User Data
try {
    val result : MagicLinkNewDeviceWebAuthnStartResponse = apiInstance.activateMagicLinkWebauthnNewDeviceStart(appId, activateMagicLinkRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling MagicLinkAPI#activateMagicLinkWebauthnNewDeviceStart")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling MagicLinkAPI#activateMagicLinkWebauthnNewDeviceStart")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **appId** | **kotlin.String**| App ID |
 **activateMagicLinkRequest** | [**ActivateMagicLinkRequest**](ActivateMagicLinkRequest.md)| User Data |

### Return type

[**MagicLinkNewDeviceWebAuthnStartResponse**](MagicLinkNewDeviceWebAuthnStartResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="magicLinkStatus"></a>
# **magicLinkStatus**
> ApiAuthResponse magicLinkStatus(appId, user)

Magic Link Status

Check if a magic link has been activated yet or not. Once the magic link has been activated, this endpoint will return an authentication token for the user. This endpoint can be used to initiate a login in one device and then poll and wait for the login to complete on another device.

### Example
```kotlin
// Import classes:
//import id.passage.client.infrastructure.*
//import id.passage.android.model.*

val apiInstance = MagicLinkAPI()
val appId : kotlin.String = appId_example // kotlin.String | App ID
val user : ApigetMagicLinkStatusRequest =  // ApigetMagicLinkStatusRequest | Magic Link ID
try {
    val result : ApiAuthResponse = apiInstance.magicLinkStatus(appId, user)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling MagicLinkAPI#magicLinkStatus")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling MagicLinkAPI#magicLinkStatus")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **appId** | **kotlin.String**| App ID |
 **user** | [**ApigetMagicLinkStatusRequest**](ApigetMagicLinkStatusRequest.md)| Magic Link ID |

### Return type

[**ApiAuthResponse**](ApiAuthResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

