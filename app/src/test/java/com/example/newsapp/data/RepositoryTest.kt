package com.example.newsapp.data

import app.cash.turbine.test
import com.example.newsapp.TestDispatcherProvider
import com.example.newsapp.data.local.DatabaseService
import com.example.newsapp.data.remote.NetworkService
import com.example.newsapp.data.remote.model.Headline
import com.example.newsapp.data.remote.model.Source
import com.example.newsapp.data.repository.Repository
import com.example.newsapp.utils.DispatcherProvider
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.doThrow
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class RepositoryTest {

    @Mock
    private lateinit var networkService: NetworkService

    @Mock
    private lateinit var databaseService: DatabaseService

    private lateinit var dispatcherProvider: DispatcherProvider

    private lateinit var mainRepository: Repository

    @Before
    fun setup() {
        dispatcherProvider = TestDispatcherProvider()
        mainRepository = Repository(
            networkService = networkService,
            databaseService = databaseService,
            dispatcherProvider = dispatcherProvider
        )
    }

    @Test
    fun getTopHeadlines_whenNetworkServiceResponseSuccess_shouldReturnSuccess() {

        runTest {

            val headline = Headline(
                title = "title",
                description = "description",
                url = "url",
                imageUrl = "urlToImage",
                source = Source(sourceId = "sourceId", sourceName = "sourceName")
            )

            val headlines = mutableListOf<Headline>()

            headlines.add(headline)

            doReturn(flowOf(headlines)).`when`(databaseService).getCachedHeadlines()

            runCatching {
                mainRepository.getCachedHeadlines().test {
                    assertEquals(flowOf(headlines), awaitItem())
                    cancelAndIgnoreRemainingEvents()
                }
            }

            verify(databaseService, times(1)).getCachedHeadlines()
        }
    }

    @Test
    fun getCachedHeadlines_whenNetworkServiceResponseError_shouldReturnError() {

        runTest {

            val errorMessage = "Error Message For You"

            doThrow(RuntimeException(errorMessage)).`when`(databaseService).getCachedHeadlines()

            runCatching {
                mainRepository.getCachedHeadlines().test {
                    assertEquals(errorMessage, awaitError().message)
                    cancelAndIgnoreRemainingEvents()
                }
            }

            verify(databaseService, times(1)).getCachedHeadlines()
        }
    }
}