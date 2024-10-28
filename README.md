# News App
Samachar is an Android news app built to demonstrate the MVVM architecture in Kotlin. It utilizes Jetpack Compose, Dagger Hilt, Retrofit, RoomDB, Coroutines, and Flow

## Major highlights

- Instant Search: Implemented using Kotlin Flow with debounce, distinctUntilChanged, and filter operators.
- Headlines Filter: Filter news headlines by country, language, and source.
- Offline Mode: Access news content even when offline.
- Bookmark Headlines: Save and access your favorite news articles for later.
- Dependency Injection: Manage dependencies cleanly and efficiently using Hilt.
- Paginated Data Loading: Efficiently load news articles with pagination support.
- API Key Management: API key handled securely as a header in the interceptor.
