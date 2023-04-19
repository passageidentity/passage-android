# LoginAPI

All URIs are relative to *https://virtserver.swaggerhub.com/passage_swagger/auth-gw/v1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**loginMagicLink**](LoginAPI.md#loginMagicLink) | **POST** /apps/{app_id}/login/magic-link/ | Login with Magic Link
[**loginOneTimePasscode**](LoginAPI.md#loginOneTimePasscode) | **POST** /apps/{app_id}/login/otp | Login with OTP
[**loginWebauthnFinish**](LoginAPI.md#loginWebauthnFinish) | **POST** /apps/{app_id}/login/webauthn/finish/ | Finish WebAuthn Login
[**loginWebauthnStart**](LoginAPI.md#loginWebauthnStart) | **POST** /apps/{app_id}/login/webauthn/start/ | Start WebAuthn Login


<a name="loginMagicLink"></a>
# **loginMagicLink**
> ApiloginMagicLinkResponse loginMagicLink(appId, user)

Login with Magic Link

Send a login email or SMS to the user. The user will receive an email or text with a link to complete their login.

### Example
```kotlin
// Import classes:
//import id.passage.client.infrastructure.*
//import id.passage.android.model.*

val apiInstance = LoginAPI()
val appId : kotlin.String = appId_example // kotlin.String | App ID
val user : ApiloginMagicLinkRequest =  // ApiloginMagicLinkRequest | User Data
try {
    val result : ApiloginMagicLinkResponse = apiInstance.loginMagicLink(appId, user)
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
 **user** | [**ApiloginMagicLinkRequest**](ApiloginMagicLinkRequest.md)| User Data |

### Return type

[**ApiloginMagicLinkResponse**](ApiloginMagicLinkResponse.md)

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
> ApiAuthResponse loginWebauthnFinish(appId, user)

Finish WebAuthn Login

Complete a WebAuthn login and authenticate the user. This endpoint accepts and verifies the response from &#x60;navigator.credential.get()&#x60; and returns an authentication token for the user.

### Example
```kotlin
// Import classes:
//import id.passage.client.infrastructure.*
//import id.passage.android.model.*

val apiInstance = LoginAPI()
val appId : kotlin.String = appId_example // kotlin.String | App ID
val user : ApiloginWebAuthnFinishRequest =  // ApiloginWebAuthnFinishRequest | User Data
try {
    val result : ApiAuthResponse = apiInstance.loginWebauthnFinish(appId, user)
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
 **user** | [**ApiloginWebAuthnFinishRequest**](ApiloginWebAuthnFinishRequest.md)| User Data |

### Return type

[**ApiAuthResponse**](ApiAuthResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="loginWebauthnStart"></a>
# **loginWebauthnStart**
> ApiloginWebAuthnStartResponse loginWebauthnStart(appId, user)

Start WebAuthn Login

Initiate a WebAuthn login for a user. This endpoint creates a WebAuthn credential assertion challenge that is used to perform the login ceremony from the browser.

### Example
```kotlin
// Import classes:
//import id.passage.client.infrastructure.*
//import id.passage.android.model.*

val apiInstance = LoginAPI()
val appId : kotlin.String = appId_example // kotlin.String | App ID
val user : ApiloginWebAuthnStartRequest =  // ApiloginWebAuthnStartRequest | User Data
try {
    val result : ApiloginWebAuthnStartResponse = apiInstance.loginWebauthnStart(appId, user)
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
 **user** | [**ApiloginWebAuthnStartRequest**](ApiloginWebAuthnStartRequest.md)| User Data |

### Return type

[**ApiloginWebAuthnStartResponse**](ApiloginWebAuthnStartResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

