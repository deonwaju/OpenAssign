package com.deontch.newsdetail.domain.contract

import com.deontch.base.contract.SideEffect
import com.deontch.base.model.MessageState
import com.deontch.news.domain.contract.NewsFeedSideEffect

interface NewsDetailSideEffect : SideEffect {
    data class ShowErrorMessage(val errorMessageState: MessageState) : NewsFeedSideEffect
}