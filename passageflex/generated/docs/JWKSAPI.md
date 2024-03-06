# JWKSAPI

All URIs are relative to *https://auth.passage.id/v1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getJwks**](JWKSAPI.md#getJwks) | **GET** /apps/{app_id}/.well-known/jwks.json | Get JWKS


<a name="getJwks"></a>
# **getJwks**
> JWKResponse getJwks(appId)

Get JWKS

Get JWKS for an app. KIDs in the JWT can be used to match the appropriate JWK, and use the JWK&#39;s public key to verify the JWT.

### Example
```kotlin
// Import classes:
//import id.passage.passageflex.client.infrastructure.*
//import id.passage.android.passageflex.model.*

val apiInstance = JWKSAPI()
val appId : kotlin.String = appId_example // kotlin.String | App ID
try {
    val result : JWKResponse = apiInstance.getJwks(appId)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling JWKSAPI#getJwks")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling JWKSAPI#getJwks")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **appId** | **kotlin.String**| App ID |

### Return type

[**JWKResponse**](JWKResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

