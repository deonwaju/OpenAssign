package com.deontch

import com.deontch.common.data.NewsFeedRepositoryImpl
import com.deontch.localdata.dao.NewsDao
import com.deontch.models.NewsFeedDomainModel
import com.deontch.network.provider.NetworkStateProvider
import com.deontch.network.service.NewsFeedApi
import com.deontch.base.providers.DispatcherProvider
import com.deontch.network.model.NewsFeedResponse
import com.deontch.news.domain.mappers.toEntity
import io.mockk.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals

class NewsFeedRepositoryImplTest {

    private lateinit var repository: NewsFeedRepositoryImpl
    private val apiService: NewsFeedApi = mockk()
    private val newsFeedDao: NewsDao = mockk()
    private val dispatcherProvider: DispatcherProvider = mockk()
    private val networkStateProvider: NetworkStateProvider = mockk()

    private fun createMockNewsFeedDomainModel(
        articleId: String = "123",
        aiOrg: String? = "Mock AI Org",
        aiRegion: String? = "Mock Region",
        aiTag: String? = "Mock Tag",
        category: List<String>? = listOf("Technology", "Health"),
        content: String? = "Mock content for the article.",
        country: List<String>? = listOf("USA"),
        creator: List<String>? = listOf("John Doe"),
        description: String? = "Mock description of the article.",
        duplicate: Boolean? = false,
        imageUrl: String? = "http://example.com/image.jpg",
        keywords: List<String>? = listOf("keyword1", "keyword2"),
        language: String? = "en",
        link: String? = "http://example.com/article",
        pubDate: String? = "2024-10-09T00:00:00Z",
        pubDateTZ: String? = "UTC",
        sentiment: String? = "Positive",
        sentimentStats: String? = "80% Positive, 20% Negative",
        sourceIcon: String? = "http://example.com/icon.png",
        sourceId: String? = "source_1",
        sourceName: String? = "Mock Source",
        sourcePriority: Int? = 1,
        sourceUrl: String? = "http://example.com/source",
        title: String? = "Mock Article Title"
    ): NewsFeedDomainModel {
        return NewsFeedDomainModel(
            articleId,
            aiOrg,
            aiRegion,
            aiTag,
            category,
            content,
            country,
            creator,
            description,
            duplicate,
            imageUrl,
            keywords,
            language,
            link,
            pubDate,
            pubDateTZ,
            sentiment,
            sentimentStats,
            sourceIcon,
            sourceId,
            sourceName,
            sourcePriority,
            sourceUrl,
            title
        )
    }

    @Before
    fun setUp() {
        repository = NewsFeedRepositoryImpl(apiService, newsFeedDao, dispatcherProvider, networkStateProvider)

        every { dispatcherProvider.io } returns kotlinx.coroutines.Dispatchers.Unconfined
    }

    @Test
    fun `getNewsFeed returns data from DAO when not connected and DAO has data`() = runBlockingTest {
        val mockDomainModel = listOf(createMockNewsFeedDomainModel())

        every { networkStateProvider.isConnected } returns false
        coEvery { newsFeedDao.getNews() } returns mockDomainModel

        val result = repository.getNewsFeed().toList()

        assertEquals(mockDomainModel, result[0])
        coVerify { newsFeedDao.getNews() }
        coVerify(exactly = 0) { apiService.getNewsFeed() }
    }

    @Test(expected = Exception::class)
    fun `getNewsFeed throws exception when not connected and DAO is empty`() = runBlockingTest {
        every { networkStateProvider.isConnected } returns false
        coEvery { newsFeedDao.getNews() } returns emptyList()

        repository.getNewsFeed().collect()
    }

    @Test
    fun `searchNewsFeed returns data from DAO when available`() = runBlockingTest {
        val mockQuery = "Mock Article Title"
        val mockDomainModel = listOf(createMockNewsFeedDomainModel()) // Populate with mock domain models

        coEvery { newsFeedDao.searchNews(mockQuery) } returns mockDomainModel

        val result = repository.searchNewsFeed(mockQuery).toList()

        assertEquals(mockDomainModel, result[0])
        coVerify { newsFeedDao.searchNews(mockQuery) }
    }
}
