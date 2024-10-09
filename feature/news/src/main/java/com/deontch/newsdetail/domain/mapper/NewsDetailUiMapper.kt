package com.deontch.newsdetail.domain.mapper

import com.deontch.models.NewsFeedDomainModel
import com.deontch.newsdetail.presentation.model.NewsDetailUiModel


internal fun NewsFeedDomainModel.toDetailUiModel(): NewsDetailUiModel {
    return with(this) {
        NewsDetailUiModel(
            articleId = articleId.orEmpty(),
            category = category.orEmpty(),
            creator = creator.orEmpty(),
            description = description.orEmpty(),
            imageUrl = imageUrl.orEmpty(),
            keywords = keywords.orEmpty(),
            pubDate = pubDate.orEmpty(),
            title = title.orEmpty(),
            link = link.orEmpty(),
            sourceUrl = sourceUrl.orEmpty(),
            sourceIcon = sourceIcon.orEmpty(),
            sourceName = sourceName.orEmpty(),
        )
    }
}
