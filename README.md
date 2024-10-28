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
