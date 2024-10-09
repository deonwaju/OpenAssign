package com.deontch.news.domain.mappers

import com.deontch.models.NewsFeedDomainModel
import com.deontch.testfakedatafactory.TestFakeDataFactory.fakeNewsFeedResponse
import io.kotest.matchers.types.shouldBeInstanceOf
import org.junit.Test

class NewsFeedRepositoryMapperTest {
    @Test
    fun `given a list of NewsFeedResponse when mapped to domain entity, then return a list of NewsFeedDomainModel`() {
        val newsFeedResponseList = fakeNewsFeedResponse

        val result = newsFeedResponseList.toEntity()

        assert(result.size == newsFeedResponseList.results.size)
        result.forEachIndexed { index, newsFeedDomainModel ->
            assert(newsFeedDomainModel.title == newsFeedResponseList.results[index].title)
            assert(newsFeedDomainModel.description == newsFeedResponseList.results[index].description)
            assert(newsFeedDomainModel.duplicate == newsFeedResponseList.results[index].duplicate)
            assert(newsFeedDomainModel.sourceName == newsFeedResponseList.results[index].source_name)
            assert(newsFeedDomainModel.pubDate == newsFeedResponseList.results[index].pubDate)
            assert(newsFeedDomainModel.articleId == newsFeedResponseList.results[index].article_id)
        }

        result.forEach {
            it.shouldBeInstanceOf<NewsFeedDomainModel>()
        }



    }
}
