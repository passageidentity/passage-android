# UsersAPI

All URIs are relative to *http://auth.passage.id/v1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**checkUserIdentifier**](UsersAPI.md#checkUserIdentifier) | **GET** /apps/{app_id}/users/ | Get User
[**createUser**](UsersAPI.md#createUser) | **POST** /apps/{app_id}/users/ | Create User


<a name="checkUserIdentifier"></a>
# **checkUserIdentifier**
> ApiUserResponse checkUserIdentifier(appId, identifier)

Get User

Get user information, if the user exists. This endpoint can be used to determine whether a user has an existing account and if they should login or register.

### Example
```kotlin
// Import classes:
//import id.passage.client.infrastructure.*
//import id.passage.android.model.*

val apiInstance = UsersAPI()
val appId : kotlin.String = appId_example // kotlin.String | App ID
val identifier : kotlin.String = identifier_example // kotlin.String | email or phone number
try {
    val result : ApiUserResponse = apiInstance.checkUserIdentifier(appId, identifier)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling UsersAPI#checkUserIdentifier")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling UsersAPI#checkUserIdentifier")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **appId** | **kotlin.String**| App ID |
 **identifier** | **kotlin.String**| email or phone number |

### Return type

[**ApiUserResponse**](ApiUserResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="createUser"></a>
# **createUser**
> ApiUserResponse createUser(appId, user)

Create User

Create a user

### Example
```kotlin
// Import classes:
//import id.passage.client.infrastructure.*
//import id.passage.android.model.*

val apiInstance = UsersAPI()
val appId : kotlin.String = appId_example // kotlin.String | App ID
val user : ModelsCreateUserParams =  // ModelsCreateUserParams | user options
try {
    val result : ApiUserResponse = apiInstance.createUser(appId, user)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling UsersAPI#createUser")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling UsersAPI#createUser")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **appId** | **kotlin.String**| App ID |
 **user** | [**ModelsCreateUserParams**](ModelsCreateUserParams.md)| user options |

### Return type

[**ApiUserResponse**](ApiUserResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

