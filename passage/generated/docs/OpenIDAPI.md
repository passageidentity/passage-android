# OpenIDAPI

All URIs are relative to *https://auth.passage.id/v1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getOpenIdConfiguration**](OpenIDAPI.md#getOpenIdConfiguration) | **GET** /apps/{app_id}/.well-known/openid-configuration | Get OpenID Configuration


<a name="getOpenIdConfiguration"></a>
# **getOpenIdConfiguration**
> OpenIdConfiguration getOpenIdConfiguration(appId)

Get OpenID Configuration

Get OpenID Configuration for an app.

### Example
```kotlin
// Import classes:
//import id.passage.client.infrastructure.*
//import id.passage.android.model.*

val apiInstance = OpenIDAPI()
val appId : kotlin.String = appId_example // kotlin.String | App ID
try {
    val result : OpenIdConfiguration = apiInstance.getOpenIdConfiguration(appId)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling OpenIDAPI#getOpenIdConfiguration")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling OpenIDAPI#getOpenIdConfiguration")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **appId** | **kotlin.String**| App ID |

### Return type

[**OpenIdConfiguration**](OpenIdConfiguration.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

