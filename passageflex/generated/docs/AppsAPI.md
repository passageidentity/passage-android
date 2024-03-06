# AppsAPI

All URIs are relative to *https://auth.passage.id/v1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getApp**](AppsAPI.md#getApp) | **GET** /apps/{app_id} | Get App


<a name="getApp"></a>
# **getApp**
> GetAppResponse getApp(appId)

Get App

Get information about an application.

### Example
```kotlin
// Import classes:
//import id.passage.passageflex.client.infrastructure.*
//import id.passage.android.passageflex.model.*

val apiInstance = AppsAPI()
val appId : kotlin.String = appId_example // kotlin.String | App ID
try {
    val result : GetAppResponse = apiInstance.getApp(appId)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AppsAPI#getApp")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AppsAPI#getApp")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **appId** | **kotlin.String**| App ID |

### Return type

[**GetAppResponse**](GetAppResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

