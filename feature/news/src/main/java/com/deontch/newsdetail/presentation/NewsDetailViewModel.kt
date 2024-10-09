package com.deontch.newsdetail.presentation

import com.deontch.base.contract.BaseViewModel
import com.deontch.base.contract.ViewEvent
import com.deontch.base.model.MessageState
import com.deontch.base.providers.DispatcherProvider
import com.deontch.base.providers.StringProvider
import com.deontch.common.cleanMessage
import com.deontch.feature.news.R
import com.deontch.models.NewsFeedDomainModel
import com.deontch.news.domain.NewsFeedRepository
import com.deontch.newsdetail.domain.contract.NewsDetailSideEffect
import com.deontch.newsdetail.domain.contract.NewsDetailState
import com.deontch.newsdetail.domain.contract.NewsDetailViewAction
import com.deontch.newsdetail.domain.mapper.toDetailUiModel
import com.deontch.ui.extensions.collectBy
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
internal class NewsDetailViewModel @Inject constructor(
    private val newsRepository: NewsFeedRepository,
    dispatcherProvider: DispatcherProvider,
    private val stringProvider: StringProvider
) : BaseViewModel<NewsDetailState, NewsDetailViewAction>(
    NewsDetailState.initialState, dispatcherProvider
) {
    override fun onViewAction(viewAction: NewsDetailViewAction) {
        when (viewAction) {
            is NewsDetailViewAction.GetNewsDetail -> getNewsDetail(viewAction.id)
        }
    }

    private fun getNewsDetail(id: String) {
        launch {
            newsRepository.getNewsDetail(id).collectBy(
                onStart = ::onLoading,
                onEach = ::processNewsDetail,
                onError = ::onError
            )
        }
    }

    private fun processNewsDetail(newsDetail: NewsFeedDomainModel) {
        updateState { state ->
            state.copy(
                isLoading = false,
                newsDetail = newsDetail.toDetailUiModel()
            )
        }
    }

    private fun onLoading() {
        updateState { state ->
            state.copy(
                isLoading = true,
            )
        }
    }

    private fun onError(error: Throwable) {
        updateState { state ->
            state.copy(
                isLoading = false,
            )
        }

        val errorMessage = error.message?.cleanMessage().orEmpty()

        dispatchViewEvent(
            ViewEvent.Effect(
                NewsDetailSideEffect.ShowErrorMessage(
                    errorMessageState = MessageState.Inline(errorMessage)
                )
            )
        )

    }
}