# TokensAPI

All URIs are relative to *https://virtserver.swaggerhub.com/passage_swagger/auth-gw/v1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**refreshAuthToken**](TokensAPI.md#refreshAuthToken) | **POST** /apps/{app_id}/tokens/ | Creates new auth and refresh token
[**revokeRefreshToken**](TokensAPI.md#revokeRefreshToken) | **DELETE** /apps/{app_id}/tokens/ | Revokes refresh token


<a name="refreshAuthToken"></a>
# **refreshAuthToken**
> ApiAuthResponse refreshAuthToken(appId, token)

Creates new auth and refresh token

Creates and returns a new auth token and a new refresh token

### Example
```kotlin
// Import classes:
//import id.passage.client.infrastructure.*
//import id.passage.android.model.*

val apiInstance = TokensAPI()
val appId : kotlin.String = appId_example // kotlin.String | App ID
val token : ApirefreshAuthTokenRequest =  // ApirefreshAuthTokenRequest | Refresh token
try {
    val result : ApiAuthResponse = apiInstance.refreshAuthToken(appId, token)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling TokensAPI#refreshAuthToken")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling TokensAPI#refreshAuthToken")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **appId** | **kotlin.String**| App ID |
 **token** | [**ApirefreshAuthTokenRequest**](ApirefreshAuthTokenRequest.md)| Refresh token |

### Return type

[**ApiAuthResponse**](ApiAuthResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="revokeRefreshToken"></a>
# **revokeRefreshToken**
> revokeRefreshToken(appId, refreshToken)

Revokes refresh token

Revokes the refresh token

### Example
```kotlin
// Import classes:
//import id.passage.client.infrastructure.*
//import id.passage.android.model.*

val apiInstance = TokensAPI()
val appId : kotlin.String = appId_example // kotlin.String | App ID
val refreshToken : kotlin.String = refreshToken_example // kotlin.String | Refresh token
try {
    apiInstance.revokeRefreshToken(appId, refreshToken)
} catch (e: ClientException) {
    println("4xx response calling TokensAPI#revokeRefreshToken")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling TokensAPI#revokeRefreshToken")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **appId** | **kotlin.String**| App ID |
 **refreshToken** | **kotlin.String**| Refresh token |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

