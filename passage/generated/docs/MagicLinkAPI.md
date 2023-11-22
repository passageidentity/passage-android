# MagicLinkAPI

All URIs are relative to *https://auth.passage.id/v1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**activateMagicLink**](MagicLinkAPI.md#activateMagicLink) | **PATCH** /apps/{app_id}/magic-link/activate | Authenticate Magic Link
[**magicLinkStatus**](MagicLinkAPI.md#magicLinkStatus) | **POST** /apps/{app_id}/magic-link/status | Magic Link Status


<a name="activateMagicLink"></a>
# **activateMagicLink**
> AuthResponse activateMagicLink(appId, activateMagicLinkRequest)

Authenticate Magic Link

Authenticate a magic link for a user. This endpoint checks that the magic link is valid, then returns an authentication token for the user.

### Example
```kotlin
// Import classes:
//import id.passage.client.infrastructure.*
//import id.passage.android.model.*

val apiInstance = MagicLinkAPI()
val appId : kotlin.String = appId_example // kotlin.String | App ID
val activateMagicLinkRequest : ActivateMagicLinkRequest =  // ActivateMagicLinkRequest | User Data
try {
    val result : AuthResponse = apiInstance.activateMagicLink(appId, activateMagicLinkRequest)
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
 **activateMagicLinkRequest** | [**ActivateMagicLinkRequest**](ActivateMagicLinkRequest.md)| User Data |

### Return type

[**AuthResponse**](AuthResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="magicLinkStatus"></a>
# **magicLinkStatus**
> AuthResponse magicLinkStatus(appId, getMagicLinkStatusRequest)

Magic Link Status

Check if a magic link has been activated yet or not. Once the magic link has been activated, this endpoint will return an authentication token for the user. This endpoint can be used to initiate a login on one device and then poll and wait for the login to complete on another device.

### Example
```kotlin
// Import classes:
//import id.passage.client.infrastructure.*
//import id.passage.android.model.*

val apiInstance = MagicLinkAPI()
val appId : kotlin.String = appId_example // kotlin.String | App ID
val getMagicLinkStatusRequest : GetMagicLinkStatusRequest =  // GetMagicLinkStatusRequest | Magic Link ID
try {
    val result : AuthResponse = apiInstance.magicLinkStatus(appId, getMagicLinkStatusRequest)
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
 **getMagicLinkStatusRequest** | [**GetMagicLinkStatusRequest**](GetMagicLinkStatusRequest.md)| Magic Link ID |

### Return type

[**AuthResponse**](AuthResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

