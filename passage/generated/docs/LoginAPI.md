# LoginAPI

All URIs are relative to *https://auth.passage.id/v1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**loginMagicLink**](LoginAPI.md#loginMagicLink) | **POST** /apps/{app_id}/login/magic-link | Login with Magic Link
[**loginOneTimePasscode**](LoginAPI.md#loginOneTimePasscode) | **POST** /apps/{app_id}/login/otp | Login with OTP
[**loginWebauthnFinish**](LoginAPI.md#loginWebauthnFinish) | **POST** /apps/{app_id}/login/webauthn/finish | Finish WebAuthn Login
[**loginWebauthnStart**](LoginAPI.md#loginWebauthnStart) | **POST** /apps/{app_id}/login/webauthn/start | Start WebAuthn Login


<a name="loginMagicLink"></a>
# **loginMagicLink**
> LoginMagicLinkResponse loginMagicLink(appId, loginMagicLinkRequest)

Login with Magic Link

Send a login email or SMS to the user. The user will receive an email or text with a link to complete their login.

### Example
```kotlin
// Import classes:
//import id.passage.client.infrastructure.*
//import id.passage.android.model.*

val apiInstance = LoginAPI()
val appId : kotlin.String = appId_example // kotlin.String | App ID
val loginMagicLinkRequest : LoginMagicLinkRequest =  // LoginMagicLinkRequest | User Data
try {
    val result : LoginMagicLinkResponse = apiInstance.loginMagicLink(appId, loginMagicLinkRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling LoginAPI#loginMagicLink")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling LoginAPI#loginMagicLink")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **appId** | **kotlin.String**| App ID |
 **loginMagicLinkRequest** | [**LoginMagicLinkRequest**](LoginMagicLinkRequest.md)| User Data |

### Return type

[**LoginMagicLinkResponse**](LoginMagicLinkResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="loginOneTimePasscode"></a>
# **loginOneTimePasscode**
> OneTimePasscodeResponse loginOneTimePasscode(appId, loginOneTimePasscodeRequest)

Login with OTP

Send a login email or SMS to the user. The user will receive an email or text with a one-time passcode to complete their login.

### Example
```kotlin
// Import classes:
//import id.passage.client.infrastructure.*
//import id.passage.android.model.*

val apiInstance = LoginAPI()
val appId : kotlin.String = appId_example // kotlin.String | App ID
val loginOneTimePasscodeRequest : LoginOneTimePasscodeRequest =  // LoginOneTimePasscodeRequest | User Data
try {
    val result : OneTimePasscodeResponse = apiInstance.loginOneTimePasscode(appId, loginOneTimePasscodeRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling LoginAPI#loginOneTimePasscode")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling LoginAPI#loginOneTimePasscode")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **appId** | **kotlin.String**| App ID |
 **loginOneTimePasscodeRequest** | [**LoginOneTimePasscodeRequest**](LoginOneTimePasscodeRequest.md)| User Data |

### Return type

[**OneTimePasscodeResponse**](OneTimePasscodeResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="loginWebauthnFinish"></a>
# **loginWebauthnFinish**
> AuthResponse loginWebauthnFinish(appId, loginWebAuthnFinishRequest)

Finish WebAuthn Login

Complete a WebAuthn login and authenticate the user. This endpoint accepts and verifies the response from &#x60;navigator.credential.get()&#x60; and returns an authentication token for the user.

### Example
```kotlin
// Import classes:
//import id.passage.client.infrastructure.*
//import id.passage.android.model.*

val apiInstance = LoginAPI()
val appId : kotlin.String = appId_example // kotlin.String | App ID
val loginWebAuthnFinishRequest : LoginWebAuthnFinishRequest =  // LoginWebAuthnFinishRequest | User Data
try {
    val result : AuthResponse = apiInstance.loginWebauthnFinish(appId, loginWebAuthnFinishRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling LoginAPI#loginWebauthnFinish")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling LoginAPI#loginWebauthnFinish")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **appId** | **kotlin.String**| App ID |
 **loginWebAuthnFinishRequest** | [**LoginWebAuthnFinishRequest**](LoginWebAuthnFinishRequest.md)| User Data |

### Return type

[**AuthResponse**](AuthResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="loginWebauthnStart"></a>
# **loginWebauthnStart**
> LoginWebAuthnStartResponse loginWebauthnStart(appId, loginWebAuthnStartRequest)

Start WebAuthn Login

Initiate a WebAuthn login for a user. This endpoint creates a WebAuthn credential assertion challenge that is used to perform the login ceremony from the browser.

### Example
```kotlin
// Import classes:
//import id.passage.client.infrastructure.*
//import id.passage.android.model.*

val apiInstance = LoginAPI()
val appId : kotlin.String = appId_example // kotlin.String | App ID
val loginWebAuthnStartRequest : LoginWebAuthnStartRequest =  // LoginWebAuthnStartRequest | User Data
try {
    val result : LoginWebAuthnStartResponse = apiInstance.loginWebauthnStart(appId, loginWebAuthnStartRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling LoginAPI#loginWebauthnStart")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling LoginAPI#loginWebauthnStart")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **appId** | **kotlin.String**| App ID |
 **loginWebAuthnStartRequest** | [**LoginWebAuthnStartRequest**](LoginWebAuthnStartRequest.md)| User Data | [optional]

### Return type

[**LoginWebAuthnStartResponse**](LoginWebAuthnStartResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

