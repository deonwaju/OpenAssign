package com.deontch.newsdetail.domain.contract

import com.deontch.base.contract.BaseViewAction

interface NewsDetailViewAction : BaseViewAction {
    data class GetNewsDetail(val id: String) : NewsDetailViewAction
}