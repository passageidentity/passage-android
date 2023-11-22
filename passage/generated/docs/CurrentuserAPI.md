# CurrentuserAPI

All URIs are relative to *https://auth.passage.id/v1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteCurrentuserDevice**](CurrentuserAPI.md#deleteCurrentuserDevice) | **DELETE** /apps/{app_id}/currentuser/devices/{device_id} | Revoke Device
[**getCurrentuser**](CurrentuserAPI.md#getCurrentuser) | **GET** /apps/{app_id}/currentuser | Get Current User
[**getCurrentuserDevices**](CurrentuserAPI.md#getCurrentuserDevices) | **GET** /apps/{app_id}/currentuser/devices | List Devices
[**getCurrentuserMetadata**](CurrentuserAPI.md#getCurrentuserMetadata) | **GET** /apps/{app_id}/currentuser/user-metadata | Get user&#39;s metadata
[**postCurrentuserAddDeviceFinish**](CurrentuserAPI.md#postCurrentuserAddDeviceFinish) | **POST** /apps/{app_id}/currentuser/devices/finish | Finish WebAuthn Add Device
[**postCurrentuserAddDeviceStart**](CurrentuserAPI.md#postCurrentuserAddDeviceStart) | **POST** /apps/{app_id}/currentuser/devices/start | Start WebAuthn Add Device
[**updateCurrentuserDevice**](CurrentuserAPI.md#updateCurrentuserDevice) | **PATCH** /apps/{app_id}/currentuser/devices/{device_id} | Update Device
[**updateCurrentuserMetadata**](CurrentuserAPI.md#updateCurrentuserMetadata) | **PATCH** /apps/{app_id}/currentuser/user-metadata | Update user&#39;s metadata
[**updateEmailCurrentuser**](CurrentuserAPI.md#updateEmailCurrentuser) | **PATCH** /apps/{app_id}/currentuser/email | Change Email
[**updatePhoneCurrentuser**](CurrentuserAPI.md#updatePhoneCurrentuser) | **PATCH** /apps/{app_id}/currentuser/phone | Change Phone


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
> CurrentUserResponse getCurrentuser(appId)

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
    val result : CurrentUserResponse = apiInstance.getCurrentuser(appId)
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

[**CurrentUserResponse**](CurrentUserResponse.md)

### Authorization


Configure bearerAuth:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getCurrentuserDevices"></a>
# **getCurrentuserDevices**
> CurrentUserDevices getCurrentuserDevices(appId)

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
    val result : CurrentUserDevices = apiInstance.getCurrentuserDevices(appId)
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

[**CurrentUserDevices**](CurrentUserDevices.md)

### Authorization


Configure bearerAuth:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getCurrentuserMetadata"></a>
# **getCurrentuserMetadata**
> UserMetadataResponse getCurrentuserMetadata(appId)

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
    val result : UserMetadataResponse = apiInstance.getCurrentuserMetadata(appId)
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

[**UserMetadataResponse**](UserMetadataResponse.md)

### Authorization


Configure bearerAuth:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="postCurrentuserAddDeviceFinish"></a>
# **postCurrentuserAddDeviceFinish**
> CurrentUserDevice postCurrentuserAddDeviceFinish(appId, addDeviceFinishRequest)

Finish WebAuthn Add Device

Complete a WebAuthn add device operation for the current user. This endpoint accepts and verifies the response from &#x60;navigator.credential.create()&#x60; and returns the created device for the user. User must be authenticated via a bearer token.

### Example
```kotlin
// Import classes:
//import id.passage.client.infrastructure.*
//import id.passage.android.model.*

val apiInstance = CurrentuserAPI()
val appId : kotlin.String = appId_example // kotlin.String | App ID
val addDeviceFinishRequest : AddDeviceFinishRequest =  // AddDeviceFinishRequest | WebAuthn Response Data
try {
    val result : CurrentUserDevice = apiInstance.postCurrentuserAddDeviceFinish(appId, addDeviceFinishRequest)
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
 **addDeviceFinishRequest** | [**AddDeviceFinishRequest**](AddDeviceFinishRequest.md)| WebAuthn Response Data |

### Return type

[**CurrentUserDevice**](CurrentUserDevice.md)

### Authorization


Configure bearerAuth:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="postCurrentuserAddDeviceStart"></a>
# **postCurrentuserAddDeviceStart**
> AddDeviceStartResponse postCurrentuserAddDeviceStart(appId, currentUserDevicesStartRequest)

Start WebAuthn Add Device

Initiate a WebAuthn add device operation for the current user. This endpoint creates a WebAuthn credential creation challenge that is used to perform the registration ceremony from the browser. User must be authenticated via a bearer token.

### Example
```kotlin
// Import classes:
//import id.passage.client.infrastructure.*
//import id.passage.android.model.*

val apiInstance = CurrentuserAPI()
val appId : kotlin.String = appId_example // kotlin.String | App ID
val currentUserDevicesStartRequest : CurrentUserDevicesStartRequest =  // CurrentUserDevicesStartRequest | WebAuthn Start Response Data
try {
    val result : AddDeviceStartResponse = apiInstance.postCurrentuserAddDeviceStart(appId, currentUserDevicesStartRequest)
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
 **currentUserDevicesStartRequest** | [**CurrentUserDevicesStartRequest**](CurrentUserDevicesStartRequest.md)| WebAuthn Start Response Data | [optional]

### Return type

[**AddDeviceStartResponse**](AddDeviceStartResponse.md)

### Authorization


Configure bearerAuth:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="updateCurrentuserDevice"></a>
# **updateCurrentuserDevice**
> CurrentUserDevice updateCurrentuserDevice(appId, deviceId, updateDeviceRequest)

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
val updateDeviceRequest : UpdateDeviceRequest =  // UpdateDeviceRequest | Friendly Name
try {
    val result : CurrentUserDevice = apiInstance.updateCurrentuserDevice(appId, deviceId, updateDeviceRequest)
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
 **updateDeviceRequest** | [**UpdateDeviceRequest**](UpdateDeviceRequest.md)| Friendly Name |

### Return type

[**CurrentUserDevice**](CurrentUserDevice.md)

### Authorization


Configure bearerAuth:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateCurrentuserMetadata"></a>
# **updateCurrentuserMetadata**
> CurrentUserResponse updateCurrentuserMetadata(appId, updateMetadataRequest)

Update user&#39;s metadata

Update the metadata for the current user. Only valid metadata fields are accepted. Invalid metadata fields that are present will abort the update. User must be authenticated via a bearer token.

### Example
```kotlin
// Import classes:
//import id.passage.client.infrastructure.*
//import id.passage.android.model.*

val apiInstance = CurrentuserAPI()
val appId : kotlin.String = appId_example // kotlin.String | App ID
val updateMetadataRequest : UpdateMetadataRequest =  // UpdateMetadataRequest | User Metadata
try {
    val result : CurrentUserResponse = apiInstance.updateCurrentuserMetadata(appId, updateMetadataRequest)
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
 **updateMetadataRequest** | [**UpdateMetadataRequest**](UpdateMetadataRequest.md)| User Metadata |

### Return type

[**CurrentUserResponse**](CurrentUserResponse.md)

### Authorization


Configure bearerAuth:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateEmailCurrentuser"></a>
# **updateEmailCurrentuser**
> MagicLinkResponse updateEmailCurrentuser(appId, updateUserEmailRequest)

Change Email

Initiate an email change for the authenticated user. An email change requires verification, so an email will be sent to the user which they must verify before the email change takes effect.

### Example
```kotlin
// Import classes:
//import id.passage.client.infrastructure.*
//import id.passage.android.model.*

val apiInstance = CurrentuserAPI()
val appId : kotlin.String = appId_example // kotlin.String | App ID
val updateUserEmailRequest : UpdateUserEmailRequest =  // UpdateUserEmailRequest | email
try {
    val result : MagicLinkResponse = apiInstance.updateEmailCurrentuser(appId, updateUserEmailRequest)
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
 **updateUserEmailRequest** | [**UpdateUserEmailRequest**](UpdateUserEmailRequest.md)| email |

### Return type

[**MagicLinkResponse**](MagicLinkResponse.md)

### Authorization


Configure bearerAuth:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updatePhoneCurrentuser"></a>
# **updatePhoneCurrentuser**
> MagicLinkResponse updatePhoneCurrentuser(appId, updateUserPhoneRequest)

Change Phone

Initiate a phone number change for the authenticated user. An phone number change requires verification, so an SMS with a link will be sent to the user which they must verify before the phone number change takes effect.

### Example
```kotlin
// Import classes:
//import id.passage.client.infrastructure.*
//import id.passage.android.model.*

val apiInstance = CurrentuserAPI()
val appId : kotlin.String = appId_example // kotlin.String | App ID
val updateUserPhoneRequest : UpdateUserPhoneRequest =  // UpdateUserPhoneRequest | phone
try {
    val result : MagicLinkResponse = apiInstance.updatePhoneCurrentuser(appId, updateUserPhoneRequest)
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
 **updateUserPhoneRequest** | [**UpdateUserPhoneRequest**](UpdateUserPhoneRequest.md)| phone |

### Return type

[**MagicLinkResponse**](MagicLinkResponse.md)

### Authorization


Configure bearerAuth:
    ApiClient.accessToken = ""

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

