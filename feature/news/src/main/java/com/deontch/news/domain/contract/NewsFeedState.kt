package com.deontch.news.domain.contract

import com.deontch.base.contract.BaseState
import com.deontch.base.model.MessageState
import com.deontch.common.ImmutableList
import com.deontch.common.emptyImmutableList
import com.deontch.news.presentation.model.NewsFeedUiModel

internal data class NewsFeedState(
    override val isLoading: Boolean,
    override val errorState: MessageState?,
    val searchQuery: String,
    val isLoadingMore: Boolean,
    val newsFeedList: ImmutableList<NewsFeedUiModel>,
    val searchQueryResponse: ImmutableList<NewsFeedUiModel>,
) : BaseState {
    companion object {
        val initialState = NewsFeedState(
            isLoading = false,
            isLoadingMore = false,
            errorState = null,
            newsFeedList = emptyImmutableList(),
            searchQueryResponse = emptyImmutableList(),
            searchQuery = ""
        )
    }
}

