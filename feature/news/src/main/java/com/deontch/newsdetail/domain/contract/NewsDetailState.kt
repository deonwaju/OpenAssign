package com.deontch.newsdetail.domain.contract

import com.deontch.base.contract.BaseState
import com.deontch.base.model.MessageState
import com.deontch.newsdetail.presentation.model.NewsDetailUiModel

internal data class NewsDetailState(
    override val isLoading: Boolean,
    override val errorState: MessageState?,
    val newsDetail: NewsDetailUiModel?
) : BaseState {

    companion object {
        val initialState = NewsDetailState(
            isLoading = false,
            errorState = null,
            newsDetail = null
        )
    }
}