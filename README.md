![passage-android](https://storage.googleapis.com/passage-docs/github-md-assets/passage-android.png)

[![Android](https://img.shields.io/badge/Android-3DDC84?logo=android&logoColor=white)](#) [![Kotlin](https://img.shields.io/badge/Kotlin-%237F52FF.svg?logo=kotlin&logoColor=white)](#) ![GitHub License](https://img.shields.io/github/license/passageidentity/passage-android)
![Static Badge](https://img.shields.io/badge/Built_by_1Password-grey?logo=1password)

## About

[Passage by 1Password](https://1password.com/product/passage) unlocks the passwordless future with a simpler, more secure passkey authentication experience. Passage handles the complexities of the [WebAuthn API](https://blog.1password.com/what-is-webauthn/), and allows you to implement passkeys with ease.

Use [Passkey Flex](https://docs.passage.id/flex) to add passkeys to an existing authentication experience.

Use [Passkey Complete](https://docs.passage.id/complete) as a standalone passwordless auth solution.

Use [Passkey Ready](https://docs.passage.id/passkey-ready) to determine if your users are ready for passkeys.

### In passage-android

Use passage-android to implement [passage-android/passage](https://github.com/passageidentity/passage-android/passage) in your Android application to authenticate requests and manage users, [passage-android/passageflex](https://github.com/passageidentity/passage-android/passageflex) in your existing auth to use passkeys for registration, authentication, or added security on sensitive user actions, and [passage-android/authentikit](https://github.com/passageidentity/passage-android/authentikit) to gather data on your users' passkey readiness.

| Product                                                                                                                                  | Compatible |
| ---------------------------------------------------------------------------------------------------------------------------------------- | ---------- |
| ![Passkey Flex](https://storage.googleapis.com/passage-docs/github-md-assets/passage-passkey-flex-icon.png) Passkey **Flex**             | ✅         |
| ![Passkey Complete](https://storage.googleapis.com/passage-docs/github-md-assets/passage-passkey-complete-icon.png) Passkey **Complete** | ✅         |
| ![Passkey Ready](https://storage.googleapis.com/passage-docs/github-md-assets/passage-passkey-ready-icon.png) Passkey **Ready**          | ✅         |

## Getting Started

### Passkey Complete

#### Check Prerequisites

<p>
 You'll need a free Passage account and a Passkey Complete app set up in <a href="https://console.passage.id/">Passage Console</a> to get started. <br />
 <sub><a href="https://docs.passage.id/home#passage-console">Learn more about Passage Console →</a></sub>
</p>

#### Install

```gradle
implementation 'id.passage.android:passage:2.0.1'
```

#### Initialize

```kotlin
val passage = Passage(activity, "YOUR_PASSAGE_APP_ID")
```

#### Go Passwordless

Find all core functions, user management details, and more implementation guidance on our [Passkey Complete Android Documentation](https://docs.passage.id/complete/android/add-passage) page.

### Passkey Flex

#### Check Prerequisites

<p>
 You'll need a free Passage account and a Passkey Flex app set up in <a href="https://console.passage.id/">Passage Console</a> to get started. <br />
 <sub><a href="https://docs.passage.id/home#passage-console">Learn more about Passage Console →</a></sub>
</p>

#### Install

```gradle
implementation 'id.passage.android:passageflex:0.2.0'
```

#### Initialize

```kotlin
val passageFlex = PassageFlex(activity, "YOUR_PASSAGE_APP_ID")
```

#### Go Passwordless

Find all core functions, user management details, and more implementation guidance on our [Passkey Flex Android Documentation](https://docs.passage.id/flex/android/config) page.

### Authentikit

#### Check Prerequisites

<p>
 You'll need a free Passage account and a Passkey Complete or Passkey Flex app set up in <a href="https://console.passage.id/">Passage Console</a> to get started. <br />
 <sub><a href="https://docs.passage.id/home#passage-console">Learn more about Passage Console →</a></sub>
</p>

#### Install

```gradle
implementation 'id.passage.android:authentikit:0.1.0'
```

#### Initialize

```kotlin
val authentikit = Authentikit(this, "YOUR_CLIENT_SIDE_KEY")
```

#### Go Passwordless

Find all core functions and more implementation guidance on our [Passkey Ready Documentation](https://docs.passage.id/passkey-ready) page.

## Support & Feedback

We are here to help! Find additional docs, the best ways to get in touch with our team, and more within our [support resources](https://github.com/passageidentity/.github/blob/main/SUPPORT.md).

<br />

---

<p align="center">
    <picture>
      <source media="(prefers-color-scheme: dark)" srcset="https://storage.googleapis.com/passage-docs/github-md-assets/passage-by-1password-dark.png">
      <source media="(prefers-color-scheme: light)" srcset="https://storage.googleapis.com/passage-docs/github-md-assets/passage-by-1password-light.png">
      <img alt="Passage by 1Password Logo" src="https://storage.googleapis.com/passage-docs/github-md-assets/passage-by-1password-light.png">
    </picture>
</p>

<p align="center">
    <sub>Passage is a product by <a href="https://1password.com/product/passage">1Password</a>, the global leader in access management solutions with nearly 150k business customers.</sub><br />
    <sub>This project is licensed under the MIT license. See the <a href="LICENSE">LICENSE</a> file for more info.</sub>
</p>
