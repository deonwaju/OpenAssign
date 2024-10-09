package com.deontch.common.data

import com.deontch.base.providers.DispatcherProvider
import com.deontch.common.Constants.GET_NEWS_ERROR_LOG
import com.deontch.common.Constants.LOAD_MORE_NEWS_ERROR_LOG
import com.deontch.common.Constants.NO_INTERNET
import com.deontch.common.Constants.SEARCH_NEWS_ERROR_LOG
import com.deontch.localdata.dao.NewsDao
import com.deontch.models.NewsFeedDomainModel
import com.deontch.network.provider.NetworkStateProvider
import com.deontch.network.service.NewsFeedApi
import com.deontch.news.domain.NewsFeedRepository
import com.deontch.news.domain.mappers.toEntity
import com.deontch.news.domain.mappers.toUiModel
import com.deontch.ui.extensions.safeReturnableOperation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

internal class NewsFeedRepositoryImpl @Inject constructor(
    private val apiService: NewsFeedApi,
    private val newsFeedDao: NewsDao,
    private val dispatcherProvider: DispatcherProvider,
    private val networkStateProvider: NetworkStateProvider
) : NewsFeedRepository {

    private var nextPage: String = ""
    private var isLastPage = false

    override fun getNewsFeed(): Flow<List<NewsFeedDomainModel>> = flow {

        val newsList = safeReturnableOperation(
            operation = {
                resetPagination()
                if (!networkStateProvider.isConnected) {
                    newsFeedDao.getNews().ifEmpty {
                        throw Exception(NO_INTERNET)
                    }
                } else {
                    val apiResponse = apiService.getNewsFeed()
                    nextPage = apiResponse.nextPage
                    isLastPage = apiResponse.results.isEmpty()
                    val domainModel = apiResponse.toEntity()
                    newsFeedDao.insertNews(domainModel)
                    domainModel
                }
            },
            actionOnException = {
                val exception = if (!networkStateProvider.isConnected) {
                    Exception(NO_INTERNET)
                } else {
                    it!!
                }
                throw exception
            },
            exceptionMessage = GET_NEWS_ERROR_LOG
        )

        emit(newsList ?: emptyList())

    }.flowOn(dispatcherProvider.io)

    override fun searchNewsFeed(query: String): Flow<List<NewsFeedDomainModel>> = flow {

        val newsList = safeReturnableOperation(
            operation = {
                val searchDataResult = newsFeedDao.searchNews(query).ifEmpty {
                    apiService.getNewsFeed(searchParam = query).toEntity()
                }
                searchDataResult

            },
            actionOnException = {
                throw if (!networkStateProvider.isConnected) {
                    Exception(NO_INTERNET)
                } else {
                    it!!
                }
            },
            exceptionMessage = SEARCH_NEWS_ERROR_LOG
        )

        emit(newsList ?: emptyList())

    }.flowOn(dispatcherProvider.io)

    override fun getNewsDetail(id: String): Flow<NewsFeedDomainModel> = flow {
        emit(newsFeedDao.getNewsDetail(id))
    }.flowOn(dispatcherProvider.io)


    override fun loadMoreNews() : Flow<List<NewsFeedDomainModel>> = flow {

        val newsList = safeReturnableOperation(
            operation = {
                if (isLastPage) emptyList<NewsFeedDomainModel>().toUiModel()
                val moreNews = apiService.getNewsFeed(nextPage = nextPage)
                isLastPage = moreNews.results.isEmpty()
                val domainModel = moreNews.toEntity()
                newsFeedDao.insertNews(domainModel)
                moreNews
            },
            actionOnException = {
                throw if (!networkStateProvider.isConnected) {
                    Exception(NO_INTERNET)
                } else {
                    it!!
                }
            },
            exceptionMessage = LOAD_MORE_NEWS_ERROR_LOG
        )

        emit(newsList?.toEntity() ?: emptyList())
    }

    private fun resetPagination() {
        nextPage = ""
        isLastPage = false
    }
}
