package com.deontch.common.di

import com.deontch.base.providers.DispatcherProvider
import com.deontch.localdata.dao.NewsDao
import com.deontch.network.service.NewsFeedApi
import com.deontch.common.data.NewsFeedRepositoryImpl
import com.deontch.network.provider.NetworkStateProvider
import com.deontch.news.domain.NewsFeedRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
internal object RepositoryModule {
    @Provides
    @Singleton
    fun provideNewsFeedRepository(
        apiService: NewsFeedApi,
        newsDao: NewsDao,
        dispatcherProvider: DispatcherProvider,
        networkStateProvider: NetworkStateProvider
    ): NewsFeedRepository {
        return com.deontch.common.data.NewsFeedRepositoryImpl(
            apiService,
            newsDao,
            dispatcherProvider,
            networkStateProvider
        )
    }
}

