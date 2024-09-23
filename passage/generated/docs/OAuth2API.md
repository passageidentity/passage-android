# OAuth2API

All URIs are relative to *https://auth.passage.id/v1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**appleOauth2Callback**](OAuth2API.md#appleOauth2Callback) | **POST** /apps/{app_id}/social/oauth2_callback | Handle Apple&#39;s OAuth2 callback
[**appleOauth2CallbackDefaultDev**](OAuth2API.md#appleOauth2CallbackDefaultDev) | **POST** /social/oauth2_callback | Handle Apple&#39;s OAuth2 callback for the default developer credentials
[**currentuserSocialLinkAccount**](OAuth2API.md#currentuserSocialLinkAccount) | **GET** /apps/{app_id}/currentuser/social/link_account | Link an existing account to an OAuth2 connection.
[**exchangeSocialIdToken**](OAuth2API.md#exchangeSocialIdToken) | **POST** /apps/{app_id}/social/id_token | Exchange native mobile identity token for an auth token.
[**exchangeSocialToken**](OAuth2API.md#exchangeSocialToken) | **GET** /apps/{app_id}/social/token | Exchange OAuth2 connection data for an auth token.
[**getAuthorize**](OAuth2API.md#getAuthorize) | **GET** /apps/{app_id}/social/authorize | Kick off OAuth2 flow
[**oauth2Callback**](OAuth2API.md#oauth2Callback) | **GET** /apps/{app_id}/social/oauth2_callback | Handle OAuth2 callback
[**oauth2CallbackDefaultDev**](OAuth2API.md#oauth2CallbackDefaultDev) | **GET** /social/oauth2_callback | Handle OAuth2 callback for the default developer credentials


<a name="appleOauth2Callback"></a>
# **appleOauth2Callback**
> appleOauth2Callback(appId, state, code, idToken, user, error)

Handle Apple&#39;s OAuth2 callback

### Example
```kotlin
// Import classes:
//import id.passage.client.infrastructure.*
//import id.passage.android.model.*

val apiInstance = OAuth2API()
val appId : kotlin.String = appId_example // kotlin.String | App ID
val state : kotlin.String = state_example // kotlin.String | The state contained in the authorization request.
val code : kotlin.String = code_example // kotlin.String | A single-use authorization grant code that’s valid for five minutes.
val idToken : kotlin.String = idToken_example // kotlin.String | A JWT containing the user’s identity information.
val user : kotlin.String = user_example // kotlin.String | A JSON string containing the data requested in the scope property.
val error : kotlin.String = error_example // kotlin.String | The error returned by Apple.
try {
    apiInstance.appleOauth2Callback(appId, state, code, idToken, user, error)
} catch (e: ClientException) {
    println("4xx response calling OAuth2API#appleOauth2Callback")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling OAuth2API#appleOauth2Callback")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **appId** | **kotlin.String**| App ID |
 **state** | **kotlin.String**| The state contained in the authorization request. |
 **code** | **kotlin.String**| A single-use authorization grant code that’s valid for five minutes. | [optional]
 **idToken** | **kotlin.String**| A JWT containing the user’s identity information. | [optional]
 **user** | **kotlin.String**| A JSON string containing the data requested in the scope property. | [optional]
 **error** | **kotlin.String**| The error returned by Apple. | [optional] [enum: user_cancelled_authorize]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/x-www-form-urlencoded
 - **Accept**: application/json

<a name="appleOauth2CallbackDefaultDev"></a>
# **appleOauth2CallbackDefaultDev**
> appleOauth2CallbackDefaultDev(state, code, idToken, user, error)

Handle Apple&#39;s OAuth2 callback for the default developer credentials

### Example
```kotlin
// Import classes:
//import id.passage.client.infrastructure.*
//import id.passage.android.model.*

val apiInstance = OAuth2API()
val state : kotlin.String = state_example // kotlin.String | The state contained in the authorization request.
val code : kotlin.String = code_example // kotlin.String | A single-use authorization grant code that’s valid for five minutes.
val idToken : kotlin.String = idToken_example // kotlin.String | A JWT containing the user’s identity information.
val user : kotlin.String = user_example // kotlin.String | A JSON string containing the data requested in the scope property.
val error : kotlin.String = error_example // kotlin.String | The error returned by Apple.
try {
    apiInstance.appleOauth2CallbackDefaultDev(state, code, idToken, user, error)
} catch (e: ClientException) {
    println("4xx response calling OAuth2API#appleOauth2CallbackDefaultDev")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling OAuth2API#appleOauth2CallbackDefaultDev")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **state** | **kotlin.String**| The state contained in the authorization request. |
 **code** | **kotlin.String**| A single-use authorization grant code that’s valid for five minutes. | [optional]
 **idToken** | **kotlin.String**| A JWT containing the user’s identity information. | [optional]
 **user** | **kotlin.String**| A JSON string containing the data requested in the scope property. | [optional]
 **error** | **kotlin.String**| The error returned by Apple. | [optional] [enum: user_cancelled_authorize]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/x-www-form-urlencoded
 - **Accept**: application/json

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

<a name="exchangeSocialIdToken"></a>
# **exchangeSocialIdToken**
> AuthResponse exchangeSocialIdToken(appId, idTokenRequest)

Exchange native mobile identity token for an auth token.

### Example
```kotlin
// Import classes:
//import id.passage.client.infrastructure.*
//import id.passage.android.model.*

val apiInstance = OAuth2API()
val appId : kotlin.String = appId_example // kotlin.String | App ID
val idTokenRequest : IdTokenRequest =  // IdTokenRequest | 
try {
    val result : AuthResponse = apiInstance.exchangeSocialIdToken(appId, idTokenRequest)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling OAuth2API#exchangeSocialIdToken")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling OAuth2API#exchangeSocialIdToken")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **appId** | **kotlin.String**| App ID |
 **idTokenRequest** | [**IdTokenRequest**](IdTokenRequest.md)|  |

### Return type

[**AuthResponse**](AuthResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
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
> getAuthorize(appId, redirectUri, codeChallenge, codeChallengeMethod, connectionType, state, loginHint)

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
val connectionType : kotlin.String = connectionType_example // kotlin.String | connection type; google, github, apple, or passage to login with
val state : kotlin.String = state_example // kotlin.String | The state to pass through to the redirect URI.
val loginHint : kotlin.String = loginHint_example // kotlin.String | Hint to the Authorization Server about the login identifier the end-user might use to log in.
try {
    apiInstance.getAuthorize(appId, redirectUri, codeChallenge, codeChallengeMethod, connectionType, state, loginHint)
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
 **connectionType** | **kotlin.String**| connection type; google, github, apple, or passage to login with | [enum: apple, github, google, passage]
 **state** | **kotlin.String**| The state to pass through to the redirect URI. | [optional]
 **loginHint** | **kotlin.String**| Hint to the Authorization Server about the login identifier the end-user might use to log in. | [optional]

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
val error : kotlin.String = error_example // kotlin.String | The error returned by the OAuth2 provider.
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
 **error** | **kotlin.String**| The error returned by the OAuth2 provider. | [optional] [enum: interaction_required, login_required, account_selection_required, consent_required, invalid_request_uri, invalid_request_object, request_not_supported, request_uri_not_supported, registration_not_supported]
 **errorDescription** | **kotlin.String**| The error description returned by the OAuth2 provider. | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="oauth2CallbackDefaultDev"></a>
# **oauth2CallbackDefaultDev**
> oauth2CallbackDefaultDev(code, state, error, errorDescription)

Handle OAuth2 callback for the default developer credentials

### Example
```kotlin
// Import classes:
//import id.passage.client.infrastructure.*
//import id.passage.android.model.*

val apiInstance = OAuth2API()
val code : kotlin.String = code_example // kotlin.String | The authorization code returned by the OAuth2 provider.
val state : kotlin.String = state_example // kotlin.String | The state returned by the OAuth2 provider.
val error : kotlin.String = error_example // kotlin.String | The error returned by the OAuth2 provider.
val errorDescription : kotlin.String = errorDescription_example // kotlin.String | The error description returned by the OAuth2 provider.
try {
    apiInstance.oauth2CallbackDefaultDev(code, state, error, errorDescription)
} catch (e: ClientException) {
    println("4xx response calling OAuth2API#oauth2CallbackDefaultDev")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling OAuth2API#oauth2CallbackDefaultDev")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **code** | **kotlin.String**| The authorization code returned by the OAuth2 provider. |
 **state** | **kotlin.String**| The state returned by the OAuth2 provider. | [optional]
 **error** | **kotlin.String**| The error returned by the OAuth2 provider. | [optional] [enum: interaction_required, login_required, account_selection_required, consent_required, invalid_request_uri, invalid_request_object, request_not_supported, request_uri_not_supported, registration_not_supported]
 **errorDescription** | **kotlin.String**| The error description returned by the OAuth2 provider. | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

