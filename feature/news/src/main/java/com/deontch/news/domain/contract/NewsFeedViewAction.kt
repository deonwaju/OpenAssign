package com.deontch.news.domain.contract

import com.deontch.base.contract.BaseViewAction

internal interface NewsFeedViewAction : BaseViewAction {
    data object GetNewsFeed: NewsFeedViewAction
    data object LoadMoreNews: NewsFeedViewAction
    data class UpdateSearchQuery(val query: String): NewsFeedViewAction
    data object SearchNewsFeed: NewsFeedViewAction
}