# PasskeyReadinessAPI

All URIs are relative to *https://auth.passage.id/v1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createPasskeyReadiness**](PasskeyReadinessAPI.md#createPasskeyReadiness) | **POST** /analytics/passkey-readiness | Create Passkey Readiness Analytics


<a name="createPasskeyReadiness"></a>
# **createPasskeyReadiness**
> createPasskeyReadiness(createPasskeyReadinessRequest, userAgent, origin, deviceOS, deviceOSVersion, appIdentifier)

Create Passkey Readiness Analytics

Sends device&#39;s WebAuthn passkey readiness metrics

### Example
```kotlin
// Import classes:
//import id.passage.client.infrastructure.*
//import id.passage.android.model.*

val apiInstance = PasskeyReadinessAPI()
val createPasskeyReadinessRequest : CreatePasskeyReadinessRequest =  // CreatePasskeyReadinessRequest | Passkey readiness metrics
val userAgent : kotlin.String = userAgent_example // kotlin.String | 
val origin : kotlin.String = origin_example // kotlin.String | url for passkey readiness
val deviceOS : kotlin.String = deviceOS_example // kotlin.String | device os - mobile only
val deviceOSVersion : kotlin.String = deviceOSVersion_example // kotlin.String | device os version - mobile only
val appIdentifier : kotlin.String = appIdentifier_example // kotlin.String | app identifier - mobile only
try {
    apiInstance.createPasskeyReadiness(createPasskeyReadinessRequest, userAgent, origin, deviceOS, deviceOSVersion, appIdentifier)
} catch (e: ClientException) {
    println("4xx response calling PasskeyReadinessAPI#createPasskeyReadiness")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling PasskeyReadinessAPI#createPasskeyReadiness")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **createPasskeyReadinessRequest** | [**CreatePasskeyReadinessRequest**](CreatePasskeyReadinessRequest.md)| Passkey readiness metrics |
 **userAgent** | **kotlin.String**|  | [optional]
 **origin** | **kotlin.String**| url for passkey readiness | [optional]
 **deviceOS** | **kotlin.String**| device os - mobile only | [optional]
 **deviceOSVersion** | **kotlin.String**| device os version - mobile only | [optional]
 **appIdentifier** | **kotlin.String**| app identifier - mobile only | [optional]

### Return type

null (empty response body)

### Authorization


Configure ApiKeyAuth:
    ApiClient.apiKey["X-API-KEY"] = ""
    ApiClient.apiKeyPrefix["X-API-KEY"] = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

