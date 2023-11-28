# UsersAPI

All URIs are relative to *https://auth.passage.id/v1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**checkUserIdentifier**](UsersAPI.md#checkUserIdentifier) | **GET** /apps/{app_id}/users | Get User
[**createUser**](UsersAPI.md#createUser) | **POST** /apps/{app_id}/users | Create User


<a name="checkUserIdentifier"></a>
# **checkUserIdentifier**
> UserResponse checkUserIdentifier(appId, identifier)

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
    val result : UserResponse = apiInstance.checkUserIdentifier(appId, identifier)
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

[**UserResponse**](UserResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="createUser"></a>
# **createUser**
> UserResponse createUser(appId, createUserParams)

Create User

Create a user

### Example
```kotlin
// Import classes:
//import id.passage.client.infrastructure.*
//import id.passage.android.model.*

val apiInstance = UsersAPI()
val appId : kotlin.String = appId_example // kotlin.String | App ID
val createUserParams : CreateUserParams =  // CreateUserParams | user options
try {
    val result : UserResponse = apiInstance.createUser(appId, createUserParams)
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
 **createUserParams** | [**CreateUserParams**](CreateUserParams.md)| user options |

### Return type

[**UserResponse**](UserResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

