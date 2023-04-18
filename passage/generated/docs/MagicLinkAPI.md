# MagicLinkAPI

All URIs are relative to *http://auth.passage.id/v1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**activateMagicLink**](MagicLinkAPI.md#activateMagicLink) | **PATCH** /apps/{app_id}/magic-link/activate/ | Authenticate Magic Link
[**activateMagicLinkWebauthnLoginFinish**](MagicLinkAPI.md#activateMagicLinkWebauthnLoginFinish) | **POST** /apps/{app_id}/magic-link/webauthn/login/finish/ | Finish a WebAuthn registration, initiated by a magic link.
[**activateMagicLinkWebauthnLoginStart**](MagicLinkAPI.md#activateMagicLinkWebauthnLoginStart) | **POST** /apps/{app_id}/magic-link/webauthn/login/start/ | Authenticate the user via magic link, then initiate a WebAuthn login.
[**activateMagicLinkWebauthnNewDeviceFinish**](MagicLinkAPI.md#activateMagicLinkWebauthnNewDeviceFinish) | **POST** /apps/{app_id}/magic-link/webauthn/new/finish/ | Finish WebAuthn registration that was initiated from a magic link.
[**activateMagicLinkWebauthnNewDeviceStart**](MagicLinkAPI.md#activateMagicLinkWebauthnNewDeviceStart) | **POST** /apps/{app_id}/magic-link/webauthn/new/start/ | Authenticate the user via magic link, then initiate a WebAuthn registration.
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
> ApiAuthResponse activateMagicLinkWebauthnLoginFinish(appId, user)

Finish a WebAuthn registration, initiated by a magic link.

### Example
```kotlin
// Import classes:
//import id.passage.client.infrastructure.*
//import id.passage.android.model.*

val apiInstance = MagicLinkAPI()
val appId : kotlin.String = appId_example // kotlin.String | App ID
val user : ApimagicLinkLoginWebAuthnFinishRequest =  // ApimagicLinkLoginWebAuthnFinishRequest | User Data
try {
    val result : ApiAuthResponse = apiInstance.activateMagicLinkWebauthnLoginFinish(appId, user)
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
 **user** | [**ApimagicLinkLoginWebAuthnFinishRequest**](ApimagicLinkLoginWebAuthnFinishRequest.md)| User Data |

### Return type

[**ApiAuthResponse**](ApiAuthResponse.md)

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
> ApiAuthResponse activateMagicLinkWebauthnNewDeviceFinish(appId, user)

Finish WebAuthn registration that was initiated from a magic link.

### Example
```kotlin
// Import classes:
//import id.passage.client.infrastructure.*
//import id.passage.android.model.*

val apiInstance = MagicLinkAPI()
val appId : kotlin.String = appId_example // kotlin.String | App ID
val user : ApimagicLinkNewDeviceWebAuthnFinishRequest =  // ApimagicLinkNewDeviceWebAuthnFinishRequest | User Data
try {
    val result : ApiAuthResponse = apiInstance.activateMagicLinkWebauthnNewDeviceFinish(appId, user)
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
 **user** | [**ApimagicLinkNewDeviceWebAuthnFinishRequest**](ApimagicLinkNewDeviceWebAuthnFinishRequest.md)| User Data |

### Return type

[**ApiAuthResponse**](ApiAuthResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="activateMagicLinkWebauthnNewDeviceStart"></a>
# **activateMagicLinkWebauthnNewDeviceStart**
> ApimagicLinkNewDeviceWebAuthnStartResponse activateMagicLinkWebauthnNewDeviceStart(appId, user)

Authenticate the user via magic link, then initiate a WebAuthn registration.

### Example
```kotlin
// Import classes:
//import id.passage.client.infrastructure.*
//import id.passage.android.model.*

val apiInstance = MagicLinkAPI()
val appId : kotlin.String = appId_example // kotlin.String | App ID
val user : ApiactivateMagicLinkRequest =  // ApiactivateMagicLinkRequest | User Data
try {
    val result : ApimagicLinkNewDeviceWebAuthnStartResponse = apiInstance.activateMagicLinkWebauthnNewDeviceStart(appId, user)
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
 **user** | [**ApiactivateMagicLinkRequest**](ApiactivateMagicLinkRequest.md)| User Data |

### Return type

[**ApimagicLinkNewDeviceWebAuthnStartResponse**](ApimagicLinkNewDeviceWebAuthnStartResponse.md)

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

