# CurrentuserAPI

All URIs are relative to *https://virtserver.swaggerhub.com/passage_swagger/auth-gw/v1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteCurrentuserDevice**](CurrentuserAPI.md#deleteCurrentuserDevice) | **DELETE** /apps/{app_id}/currentuser/devices/{device_id}/ | Revoke Device
[**getCurrentuser**](CurrentuserAPI.md#getCurrentuser) | **GET** /apps/{app_id}/currentuser/ | Get Current User
[**getCurrentuserDevices**](CurrentuserAPI.md#getCurrentuserDevices) | **GET** /apps/{app_id}/currentuser/devices/ | List Devices
[**getCurrentuserMetadata**](CurrentuserAPI.md#getCurrentuserMetadata) | **GET** /apps/{app_id}/currentuser/user-metadata/ | Get user&#39;s metadata
[**postCurrentuserAddDeviceFinish**](CurrentuserAPI.md#postCurrentuserAddDeviceFinish) | **POST** /apps/{app_id}/currentuser/devices/finish | Finish WebAuthn Add Device
[**postCurrentuserAddDeviceStart**](CurrentuserAPI.md#postCurrentuserAddDeviceStart) | **POST** /apps/{app_id}/currentuser/devices/start/ | Start WebAuthn Add Device
[**updateCurrentuserDevice**](CurrentuserAPI.md#updateCurrentuserDevice) | **PATCH** /apps/{app_id}/currentuser/devices/{device_id}/ | Update Device
[**updateCurrentuserMetadata**](CurrentuserAPI.md#updateCurrentuserMetadata) | **PATCH** /apps/{app_id}/currentuser/user-metadata/ | Update user&#39;s metadata
[**updateEmailCurrentuser**](CurrentuserAPI.md#updateEmailCurrentuser) | **PATCH** /apps/{app_id}/currentuser/email/ | Change Email
[**updatePhoneCurrentuser**](CurrentuserAPI.md#updatePhoneCurrentuser) | **PATCH** /apps/{app_id}/currentuser/phone/ | Change Phone


<a name="deleteCurrentuserDevice"></a>
# **deleteCurrentuserDevice**
> deleteCurrentuserDevice(appId, deviceId)

Revoke Device

Revoke a device by ID for the current user. User must be authenticated via a bearer token.

### Example
```kotlin
// Import classes:
//import id.passage.client.infrastructure.*
//import id.passage.android.model.*

val apiInstance = CurrentuserAPI()
val appId : kotlin.String = appId_example // kotlin.String | App ID
val deviceId : kotlin.String = deviceId_example // kotlin.String | Device ID
try {
    apiInstance.deleteCurrentuserDevice(appId, deviceId)
} catch (e: ClientException) {
    println("4xx response calling CurrentuserAPI#deleteCurrentuserDevice")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling CurrentuserAPI#deleteCurrentuserDevice")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **appId** | **kotlin.String**| App ID |
 **deviceId** | **kotlin.String**| Device ID |

### Return type

null (empty response body)

### Authorization


Configure bearerAuth:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getCurrentuser"></a>
# **getCurrentuser**
> ModelsCurrentUser getCurrentuser(appId)

Get Current User

Get information about a user that is currently authenticated via bearer token.

### Example
```kotlin
// Import classes:
//import id.passage.client.infrastructure.*
//import id.passage.android.model.*

val apiInstance = CurrentuserAPI()
val appId : kotlin.String = appId_example // kotlin.String | App ID
try {
    val result : ModelsCurrentUser = apiInstance.getCurrentuser(appId)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling CurrentuserAPI#getCurrentuser")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling CurrentuserAPI#getCurrentuser")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **appId** | **kotlin.String**| App ID |

### Return type

[**ModelsCurrentUser**](ModelsCurrentUser.md)

### Authorization


Configure bearerAuth:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getCurrentuserDevices"></a>
# **getCurrentuserDevices**
> ApiCurrentUserDevices getCurrentuserDevices(appId)

List Devices

List all WebAuthn devices for the authenticated user. User must be authenticated via bearer token.

### Example
```kotlin
// Import classes:
//import id.passage.client.infrastructure.*
//import id.passage.android.model.*

val apiInstance = CurrentuserAPI()
val appId : kotlin.String = appId_example // kotlin.String | App ID
try {
    val result : ApiCurrentUserDevices = apiInstance.getCurrentuserDevices(appId)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling CurrentuserAPI#getCurrentuserDevices")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling CurrentuserAPI#getCurrentuserDevices")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **appId** | **kotlin.String**| App ID |

### Return type

[**ApiCurrentUserDevices**](ApiCurrentUserDevices.md)

### Authorization


Configure bearerAuth:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getCurrentuserMetadata"></a>
# **getCurrentuserMetadata**
> ModelsCurrentUser getCurrentuserMetadata(appId)

Get user&#39;s metadata

Get the user-metadata for the current user.

### Example
```kotlin
// Import classes:
//import id.passage.client.infrastructure.*
//import id.passage.android.model.*

val apiInstance = CurrentuserAPI()
val appId : kotlin.String = appId_example // kotlin.String | App ID
try {
    val result : ModelsCurrentUser = apiInstance.getCurrentuserMetadata(appId)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling CurrentuserAPI#getCurrentuserMetadata")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling CurrentuserAPI#getCurrentuserMetadata")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **appId** | **kotlin.String**| App ID |

### Return type

[**ModelsCurrentUser**](ModelsCurrentUser.md)

### Authorization


Configure bearerAuth:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="postCurrentuserAddDeviceFinish"></a>
# **postCurrentuserAddDeviceFinish**
> ApiCurrentUserDevice postCurrentuserAddDeviceFinish(appId, user)

Finish WebAuthn Add Device

Complete a WebAuthn add device operation for the current user. This endpoint accepts and verifies the response from &#x60;navigator.credential.create()&#x60; and returns the created device for the user. User must be authenticated via a bearer token.

### Example
```kotlin
// Import classes:
//import id.passage.client.infrastructure.*
//import id.passage.android.model.*

val apiInstance = CurrentuserAPI()
val appId : kotlin.String = appId_example // kotlin.String | App ID
val user : ApiaddDeviceFinishRequest =  // ApiaddDeviceFinishRequest | WebAuthn Response Data
try {
    val result : ApiCurrentUserDevice = apiInstance.postCurrentuserAddDeviceFinish(appId, user)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling CurrentuserAPI#postCurrentuserAddDeviceFinish")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling CurrentuserAPI#postCurrentuserAddDeviceFinish")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **appId** | **kotlin.String**| App ID |
 **user** | [**ApiaddDeviceFinishRequest**](ApiaddDeviceFinishRequest.md)| WebAuthn Response Data |

### Return type

[**ApiCurrentUserDevice**](ApiCurrentUserDevice.md)

### Authorization


Configure bearerAuth:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="postCurrentuserAddDeviceStart"></a>
# **postCurrentuserAddDeviceStart**
> ApiaddDeviceStartResponse postCurrentuserAddDeviceStart(appId)

Start WebAuthn Add Device

Initiate a WebAuthn add device operation for the current user. This endpoint creates a WebAuthn credential creation challenge that is used to perform the registration ceremony from the browser. User must be authenticated via a bearer token.

### Example
```kotlin
// Import classes:
//import id.passage.client.infrastructure.*
//import id.passage.android.model.*

val apiInstance = CurrentuserAPI()
val appId : kotlin.String = appId_example // kotlin.String | App ID
try {
    val result : ApiaddDeviceStartResponse = apiInstance.postCurrentuserAddDeviceStart(appId)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling CurrentuserAPI#postCurrentuserAddDeviceStart")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling CurrentuserAPI#postCurrentuserAddDeviceStart")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **appId** | **kotlin.String**| App ID |

### Return type

[**ApiaddDeviceStartResponse**](ApiaddDeviceStartResponse.md)

### Authorization


Configure bearerAuth:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="updateCurrentuserDevice"></a>
# **updateCurrentuserDevice**
> ApiCurrentUserDevice updateCurrentuserDevice(appId, deviceId, friendlyName)

Update Device

Update a device by ID for the current user. Currently the only field that can be updated is the friendly name. User must be authenticated via a bearer token.

### Example
```kotlin
// Import classes:
//import id.passage.client.infrastructure.*
//import id.passage.android.model.*

val apiInstance = CurrentuserAPI()
val appId : kotlin.String = appId_example // kotlin.String | App ID
val deviceId : kotlin.String = deviceId_example // kotlin.String | Device ID
val friendlyName : ApiupdateDeviceRequest =  // ApiupdateDeviceRequest | Friendly Name
try {
    val result : ApiCurrentUserDevice = apiInstance.updateCurrentuserDevice(appId, deviceId, friendlyName)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling CurrentuserAPI#updateCurrentuserDevice")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling CurrentuserAPI#updateCurrentuserDevice")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **appId** | **kotlin.String**| App ID |
 **deviceId** | **kotlin.String**| Device ID |
 **friendlyName** | [**ApiupdateDeviceRequest**](ApiupdateDeviceRequest.md)| Friendly Name |

### Return type

[**ApiCurrentUserDevice**](ApiCurrentUserDevice.md)

### Authorization


Configure bearerAuth:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="updateCurrentuserMetadata"></a>
# **updateCurrentuserMetadata**
> ModelsCurrentUser updateCurrentuserMetadata(appId, userMetadata)

Update user&#39;s metadata

Update the metadata for the current user. Only valid metadata fields are accepted. Invalid metadata fields that are present will abort the update. User must be authenticated via a bearer token.

### Example
```kotlin
// Import classes:
//import id.passage.client.infrastructure.*
//import id.passage.android.model.*

val apiInstance = CurrentuserAPI()
val appId : kotlin.String = appId_example // kotlin.String | App ID
val userMetadata : ApiupdateMetadataRequest =  // ApiupdateMetadataRequest | User Metadata
try {
    val result : ModelsCurrentUser = apiInstance.updateCurrentuserMetadata(appId, userMetadata)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling CurrentuserAPI#updateCurrentuserMetadata")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling CurrentuserAPI#updateCurrentuserMetadata")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **appId** | **kotlin.String**| App ID |
 **userMetadata** | [**ApiupdateMetadataRequest**](ApiupdateMetadataRequest.md)| User Metadata |

### Return type

[**ModelsCurrentUser**](ModelsCurrentUser.md)

### Authorization


Configure bearerAuth:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="updateEmailCurrentuser"></a>
# **updateEmailCurrentuser**
> ApiMagicLinkResponse updateEmailCurrentuser(appId, user)

Change Email

Initiate an email change for the authenticated user. An email change requires verification, so an email will be sent to the user which they must verify before the email change takes effect.

### Example
```kotlin
// Import classes:
//import id.passage.client.infrastructure.*
//import id.passage.android.model.*

val apiInstance = CurrentuserAPI()
val appId : kotlin.String = appId_example // kotlin.String | App ID
val user : UserUpdateUserEmailRequest =  // UserUpdateUserEmailRequest | email
try {
    val result : ApiMagicLinkResponse = apiInstance.updateEmailCurrentuser(appId, user)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling CurrentuserAPI#updateEmailCurrentuser")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling CurrentuserAPI#updateEmailCurrentuser")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **appId** | **kotlin.String**| App ID |
 **user** | [**UserUpdateUserEmailRequest**](UserUpdateUserEmailRequest.md)| email |

### Return type

[**ApiMagicLinkResponse**](ApiMagicLinkResponse.md)

### Authorization


Configure bearerAuth:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="updatePhoneCurrentuser"></a>
# **updatePhoneCurrentuser**
> ApiMagicLinkResponse updatePhoneCurrentuser(appId, user)

Change Phone

Initiate a phone number change for the authenticated user. An phone number change requires verification, so an SMS with a link will be sent to the user which they must verify before the phone number change takes effect.

### Example
```kotlin
// Import classes:
//import id.passage.client.infrastructure.*
//import id.passage.android.model.*

val apiInstance = CurrentuserAPI()
val appId : kotlin.String = appId_example // kotlin.String | App ID
val user : UserUpdateUserPhoneRequest =  // UserUpdateUserPhoneRequest | phone
try {
    val result : ApiMagicLinkResponse = apiInstance.updatePhoneCurrentuser(appId, user)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling CurrentuserAPI#updatePhoneCurrentuser")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling CurrentuserAPI#updatePhoneCurrentuser")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **appId** | **kotlin.String**| App ID |
 **user** | [**UserUpdateUserPhoneRequest**](UserUpdateUserPhoneRequest.md)| phone |

### Return type

[**ApiMagicLinkResponse**](ApiMagicLinkResponse.md)

### Authorization


Configure bearerAuth:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

