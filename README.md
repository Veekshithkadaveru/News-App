# News App
News App is an Android news app built to demonstrate the MVVM architecture in Kotlin. It utilizes Jetpack Compose, Dagger Hilt, Retrofit, RoomDB, Coroutines, and Flow

## Major highlights

- Instant Search: Implemented using Kotlin Flow with debounce, distinctUntilChanged, and filter operators.
- Headlines Filter: Filter news headlines by country, language, and source.
- Offline Mode: Access news content even when offline.
- Bookmark Headlines: Save and access your favorite news articles for later.
- Dependency Injection: Manage dependencies cleanly and efficiently using Hilt.
- Paginated Data Loading: Efficiently load news articles with pagination support.
- API Key Management: API key handled securely as a header in the interceptor.

## API Key Setup

This project uses the NewsAPI to fetch news articles. Some users have encountered the `429 Too Many Requests` error due to API rate limits. To avoid this issue, it's recommended that each developer uses their own API key.

### Steps to Configure Your API Key

1. **Obtain an API Key**:
    - Visit the [NewsAPI](https://newsapi.org/) website.
    - Sign up and get your free API key.

2. **Link the API Key in the Code**:
    - Replace the current API Key with your own key in the `build.gradle` file.

3. **Rebuild the Project**:
    - Sync your project with Gradle files and rebuild the project to apply the changes.

By following these steps and using your own API key, you can avoid hitting the API rate limits and ensure smooth functioning of the app.

## Tech stack & Open-source libraries
- **Minimum SDK level 26**
- [**Kotlin**](https://kotlinlang.org/) - Modern, concise, and safe programming language.
- [**Dagger Hilt**](https://developer.android.com/training/dependency-injection/hilt-android) - Dagger Hilt is a dependency injection library for Android that reduces the boilerplate of doing manual dependency injection in your project.
- [**Kotlin Serialization**](https://github.com/Kotlin/kotlinx.serialization) - A compiler plugin that automatically produces visitor code for classes, and a runtime library that uses generated code to serialize objects without reflection.
- [**Coroutines**](https://kotlinlang.org/docs/coroutines-overview.html) - Asynchronous or non-blocking programming is an important part of the development landscape. Coroutines is our recommended solution for asynchronous programming on Android.
- [**Flow**](https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/) - An asynchronous data stream that sequentially emits values and completes normally or with an exception.
- [**Retrofit**](https://square.github.io/retrofit/) - A type-safe HTTP client for Android and Java.
- [**RoomDB**](https://developer.android.com/training/data-storage/room) - The Room persistence library provides an abstraction layer over SQLite to allow fluent database access while harnessing the full power of SQLite.
- [**Jetpack Compose**](https://developer.android.com/develop/ui/compose) - Jetpack Compose is Android’s recommended modern toolkit for building native UI. It simplifies and accelerates UI development on Android.
- [**Coil**](https://coil-kt.github.io/coil/) - An image loading library for Android backed by Kotlin Coroutines.
- [**Material 3**](https://github.com/material-components/material-components-android) - Material design components for building View layouts and Animations.

## App UI

| ![Screenshot_20241030_184046](https://github.com/user-attachments/assets/5e98201c-104b-46fd-9034-96c824bb14f3) | ![Screenshot_20241030_183830](https://github.com/user-attachments/assets/9f02e426-3e21-4895-9e73-75b00f765bd9) | ![Screenshot_20241030_183954](https://github.com/user-attachments/assets/baa0b722-6183-46c1-a16b-f5e7b715c001) | 
| --- | --- | --- |
| ![Screenshot_20241030_184011](https://github.com/user-attachments/assets/5ab72c3b-d18a-44bc-b5e8-b53bd2c0d5d7) | ![Screenshot_20241030_184026](https://github.com/user-attachments/assets/c2994754-570a-4bed-b971-66a97706706f)| ![Screenshot_20241030_185158](https://github.com/user-attachments/assets/bb657d3d-7ed1-4df7-bf4d-9ed038d38c64)


