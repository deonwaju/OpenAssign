package com.deontch.news.domain.mappers

import com.deontch.common.ImmutableList
import com.deontch.common.toImmutableList
import com.deontch.models.NewsFeedDomainModel
import com.deontch.news.presentation.model.NewsFeedUiModel


internal fun List<NewsFeedDomainModel>.toUiModel(): ImmutableList<NewsFeedUiModel> =
    map { it.toUiModel() }.toImmutableList()


internal fun NewsFeedDomainModel.toUiModel(): NewsFeedUiModel {
    return with(this) {
        NewsFeedUiModel(
            category = category.orEmpty(),
            creator = creator.orEmpty(),
            description = description.orEmpty(),
            imageUrl = imageUrl.orEmpty(),
            keywords = keywords.orEmpty(),
            pubDate = pubDate.orEmpty(),
            title = title.orEmpty(),
            articleId = articleId
        )
    }
}
