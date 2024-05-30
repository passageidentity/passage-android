<img src="https://storage.googleapis.com/passage-docs/passage-logo-gradient.svg" alt="Passage logo" style="width:250px;"/>

### Native passkey authentication for your Android app
## Welcome!
Integrating passkey technology can be really hard. That's why we built the Passage Android SDK - to make passkey authentication easy for you and your users, all in a native Android experience.

<img width="1069" alt="Screenshot 2023-05-15 at 5 42 31 PM" src="https://github.com/passageidentity/passage-android/assets/16176400/fc1acb9f-0eb7-4a8f-99b9-55be4459bfee">

<br>
<br>

## Usage

### Passkey Complete

You can import the Passage library by including this in your app's `build.gradle` file dependencies:
```gradle
implementation 'id.passage.android:passage:1.7.5'
```

And you can use it like this:
```kotlin
val passage = Passage(activity, "YOUR_APP_ID")
passage.loginWithPasskey()
```
<br>

### Passkey Flex

You can import the PassageFlex library by including this in your app's `build.gradle` file dependencies:
```gradle
implementation 'id.passage.android:passageflex:0.1.0'
```

And you can use it like this:
```kotlin
val nonce = PassageFlex.Passkey.authenticate()
```
<!--
<br>

### Authentikit

You can import the Authentikit library by including this in your app's `build.gradle` file dependencies:
```gradle
implementation 'id.passage.android:authentikit:0.1.0'
```

And you can use it like this:
```kotlin
val authentikit = Authentikit(this, "YOUR_CLIENT_SIDE_KEY")
authentikit.passkey.evaluateReadiness()
```
-->

## Documentation
To get started using Passage in your Android app, please visit our [Passage Docs](https://docs.passage.id/mobile/android/).
