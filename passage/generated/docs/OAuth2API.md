# OAuth2API

All URIs are relative to *https://auth.passage.id/v1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**currentuserSocialLinkAccount**](OAuth2API.md#currentuserSocialLinkAccount) | **GET** /apps/{app_id}/currentuser/social/link_account | Link an existing account to an OAuth2 connection.
[**exchangeSocialToken**](OAuth2API.md#exchangeSocialToken) | **GET** /apps/{app_id}/social/token | Exchange OAuth2 connection data for an auth token.
[**getAuthorize**](OAuth2API.md#getAuthorize) | **GET** /apps/{app_id}/social/authorize | Kick off OAuth2 flow
[**oauth2Callback**](OAuth2API.md#oauth2Callback) | **GET** /apps/{app_id}/social/oauth2_callback | Handle OAuth2 callback


<a name="currentuserSocialLinkAccount"></a>
# **currentuserSocialLinkAccount**
> currentuserSocialLinkAccount(appId, code, verifier)

Link an existing account to an OAuth2 connection.

### Example
```kotlin
// Import classes:
//import id.passage.client.infrastructure.*
//import id.passage.android.model.*

val apiInstance = OAuth2API()
val appId : kotlin.String = appId_example // kotlin.String | App ID
val code : kotlin.String = code_example // kotlin.String | The code given from the OAuth2 redirect
val verifier : kotlin.String = verifier_example // kotlin.String | The verifier the client originally sent to the OAuth2 provider
try {
    apiInstance.currentuserSocialLinkAccount(appId, code, verifier)
} catch (e: ClientException) {
    println("4xx response calling OAuth2API#currentuserSocialLinkAccount")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling OAuth2API#currentuserSocialLinkAccount")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **appId** | **kotlin.String**| App ID |
 **code** | **kotlin.String**| The code given from the OAuth2 redirect |
 **verifier** | **kotlin.String**| The verifier the client originally sent to the OAuth2 provider |

### Return type

null (empty response body)

### Authorization


Configure bearerAuth:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="exchangeSocialToken"></a>
# **exchangeSocialToken**
> AuthResponse exchangeSocialToken(appId, code, verifier)

Exchange OAuth2 connection data for an auth token.

### Example
```kotlin
// Import classes:
//import id.passage.client.infrastructure.*
//import id.passage.android.model.*

val apiInstance = OAuth2API()
val appId : kotlin.String = appId_example // kotlin.String | App ID
val code : kotlin.String = code_example // kotlin.String | The code given from the OAuth2 redirect
val verifier : kotlin.String = verifier_example // kotlin.String | The verifier the client originally sent to the OAuth2 provider
try {
    val result : AuthResponse = apiInstance.exchangeSocialToken(appId, code, verifier)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling OAuth2API#exchangeSocialToken")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling OAuth2API#exchangeSocialToken")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **appId** | **kotlin.String**| App ID |
 **code** | **kotlin.String**| The code given from the OAuth2 redirect |
 **verifier** | **kotlin.String**| The verifier the client originally sent to the OAuth2 provider |

### Return type

[**AuthResponse**](AuthResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getAuthorize"></a>
# **getAuthorize**
> getAuthorize(appId, redirectUri, codeChallenge, codeChallengeMethod, connectionType, state)

Kick off OAuth2 flow

Kick off OAuth2 flow with connection provider request params described in https://openid.net/specs/openid-connect-core-1_0.html#AuthRequest

### Example
```kotlin
// Import classes:
//import id.passage.client.infrastructure.*
//import id.passage.android.model.*

val apiInstance = OAuth2API()
val appId : kotlin.String = appId_example // kotlin.String | App ID
val redirectUri : kotlin.String = redirectUri_example // kotlin.String | The URL to redirect to after the OAuth2 flow is complete.
val codeChallenge : kotlin.String = codeChallenge_example // kotlin.String | Code challenge.
val codeChallengeMethod : kotlin.String = codeChallengeMethod_example // kotlin.String | Code challenge method.
val connectionType : OAuth2ConnectionType =  // OAuth2ConnectionType | connection type; google, github, apple, or passage to login with
val state : kotlin.String = state_example // kotlin.String | The state to pass through to the redirect URI.
try {
    apiInstance.getAuthorize(appId, redirectUri, codeChallenge, codeChallengeMethod, connectionType, state)
} catch (e: ClientException) {
    println("4xx response calling OAuth2API#getAuthorize")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling OAuth2API#getAuthorize")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **appId** | **kotlin.String**| App ID |
 **redirectUri** | **kotlin.String**| The URL to redirect to after the OAuth2 flow is complete. |
 **codeChallenge** | **kotlin.String**| Code challenge. |
 **codeChallengeMethod** | **kotlin.String**| Code challenge method. |
 **connectionType** | [**OAuth2ConnectionType**](.md)| connection type; google, github, apple, or passage to login with | [enum: apple, github, google, passage]
 **state** | **kotlin.String**| The state to pass through to the redirect URI. | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="oauth2Callback"></a>
# **oauth2Callback**
> oauth2Callback(appId, code, state, error, errorDescription)

Handle OAuth2 callback

### Example
```kotlin
// Import classes:
//import id.passage.client.infrastructure.*
//import id.passage.android.model.*

val apiInstance = OAuth2API()
val appId : kotlin.String = appId_example // kotlin.String | App ID
val code : kotlin.String = code_example // kotlin.String | The authorization code returned by the OAuth2 provider.
val state : kotlin.String = state_example // kotlin.String | The state returned by the OAuth2 provider.
val error : AuthErrorCode =  // AuthErrorCode | The error returned by the OAuth2 provider.
val errorDescription : kotlin.String = errorDescription_example // kotlin.String | The error description returned by the OAuth2 provider.
try {
    apiInstance.oauth2Callback(appId, code, state, error, errorDescription)
} catch (e: ClientException) {
    println("4xx response calling OAuth2API#oauth2Callback")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling OAuth2API#oauth2Callback")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **appId** | **kotlin.String**| App ID |
 **code** | **kotlin.String**| The authorization code returned by the OAuth2 provider. |
 **state** | **kotlin.String**| The state returned by the OAuth2 provider. | [optional]
 **error** | [**AuthErrorCode**](.md)| The error returned by the OAuth2 provider. | [optional] [enum: interaction_required, login_required, account_selection_required, consent_required, invalid_request_uri, invalid_request_object, request_not_supported, request_uri_not_supported, registration_not_supported]
 **errorDescription** | **kotlin.String**| The error description returned by the OAuth2 provider. | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

