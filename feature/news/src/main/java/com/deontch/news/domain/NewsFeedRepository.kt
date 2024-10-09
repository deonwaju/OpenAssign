package com.deontch.news.domain

import com.deontch.models.NewsFeedDomainModel
import kotlinx.coroutines.flow.Flow

internal interface NewsFeedRepository {
    fun getNewsFeed(): Flow<List<NewsFeedDomainModel>>

    fun loadMoreNews(): Flow<List<NewsFeedDomainModel>>

    fun searchNewsFeed(query: String) : Flow<List<NewsFeedDomainModel>>

    fun getNewsDetail(id: String): Flow<NewsFeedDomainModel>
}