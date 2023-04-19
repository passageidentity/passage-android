# OTPAPI

All URIs are relative to *https://virtserver.swaggerhub.com/passage_swagger/auth-gw/v1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**activateOneTimePasscode**](OTPAPI.md#activateOneTimePasscode) | **POST** /apps/{app_id}/otp/activate | Authenticate OTP


<a name="activateOneTimePasscode"></a>
# **activateOneTimePasscode**
> AuthResponse activateOneTimePasscode(appId, activateOneTimePasscodeRequest)

Authenticate OTP

Authenticate a one-time passcode for a user. This endpoint checks that the one-time passcode is valid, then returns an authentication token for the user.

### Example
```kotlin
// Import classes:
//import id.passage.client.infrastructure.*
//import id.passage.android.model.*

val apiInstance = OTPAPI()
val appId : kotlin.String = appId_example // kotlin.String | App ID
val activateOneTimePasscodeRequest : ActivateOneTimePasscodeRequest =  // ActivateOneTimePasscodeRequest | User Data
try {
    val result : AuthResponse = apiInstance.activateOneTimePasscode(appId, activateOneTimePasscodeRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling OTPAPI#activateOneTimePasscode")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling OTPAPI#activateOneTimePasscode")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **appId** | **kotlin.String**| App ID |
 **activateOneTimePasscodeRequest** | [**ActivateOneTimePasscodeRequest**](ActivateOneTimePasscodeRequest.md)| User Data |

### Return type

[**AuthResponse**](AuthResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

