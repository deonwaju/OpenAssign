package com.deontch.news.domain.contract

import com.deontch.base.contract.SideEffect
import com.deontch.base.model.MessageState

internal interface NewsFeedSideEffect : SideEffect {
    data class ShowErrorMessage(val errorMessageState: MessageState) : NewsFeedSideEffect
}