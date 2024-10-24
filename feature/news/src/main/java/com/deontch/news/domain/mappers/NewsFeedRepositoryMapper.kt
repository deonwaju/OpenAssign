package com.deontch.news.domain.mappers

import com.deontch.network.model.NewsFeedApiResult
import com.deontch.network.model.NewsFeedResponse
import com.deontch.models.NewsFeedDomainModel

internal fun NewsFeedResponse.toEntity(): List<NewsFeedDomainModel> {
    return results.map { it.toEntity() }
}


internal fun NewsFeedApiResult.toEntity(): NewsFeedDomainModel {
    return with(this) {
        NewsFeedDomainModel(
            aiOrg = ai_org,
            aiRegion = ai_region,
            aiTag = ai_tag,
            articleId = article_id,
            category = category,
            content = content,
            country = country,
            creator = creator,
            description = description,
            duplicate = duplicate,
            imageUrl = image_url,
            keywords = keywords,
            language = language,
            link = link,
            pubDate = pubDate,
            pubDateTZ = pubDateTZ,
            sentiment = sentiment,
            sentimentStats = sentiment_stats,
            sourceIcon = source_icon,
            sourceId = source_id,
            sourceName = source_name,
            sourcePriority = source_priority,
            sourceUrl = source_url,
            title = title,
        )
    }
}
