package com.deontch.news.presentation.component

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.deontch.feature.news.R
import com.deontch.news.presentation.model.NewsFeedUiModel
import com.deontch.ui.theme.Dimens.ArticleCardSize
import com.deontch.ui.theme.Dimens.ExtraSmallPadding
import com.deontch.ui.theme.Dimens.SmallIconSize

@Composable
internal fun ArticleCard(
    modifier: Modifier = Modifier,
    article: NewsFeedUiModel,
    onClick: (String) -> Unit
) {
    val context = LocalContext.current
    Row(
        modifier = modifier
            .clickable { onClick.invoke(article.articleId) }
            .testTag(stringResource(R.string.articlecard)),
    ) {
        AsyncImage(
            modifier = Modifier
                .size(ArticleCardSize)
                .clip(MaterialTheme.shapes.medium),
            model = ImageRequest.Builder(context)
                .data(article.imageUrl)
                .error(R.drawable.ic_launcher_background)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(ExtraSmallPadding))
        Column(
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .padding(horizontal = ExtraSmallPadding)
                .height(ArticleCardSize)
        ) {
            Text(
                text = article.title,
                style = MaterialTheme.typography.bodyMedium.copy(),
                color = colorResource(id = R.color.text_title),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.testTag(stringResource(R.string.title_tag))
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = formatCreators(article.creator),
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    color = colorResource(id = R.color.body),
                    modifier = Modifier.weight(1f),
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.width(ExtraSmallPadding))
                Icon(
                    painter = painterResource(id = R.drawable.ic_time),
                    contentDescription = stringResource(R.string.thumbnail_icon),
                    modifier = Modifier.size(SmallIconSize),
                    tint = colorResource(id = R.color.body)
                )
                Spacer(modifier = Modifier.width(ExtraSmallPadding))
                Text(
                    text = article.pubDate,
                    style = MaterialTheme.typography.labelSmall,
                    color = colorResource(id = R.color.body),
                    modifier = Modifier.weight(1f),
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

private fun formatCreators(creators: List<String>): String {
    return when {
        creators.isEmpty() -> "Unknown" // Handle empty list case
        creators.size == 1 -> creators[0]
        creators.size == 2 -> "${creators[0]} and ${creators[1]}"
        else -> {
            val firstPart = creators.dropLast(1).joinToString(", ")
            val lastPart = creators.last()
            "$firstPart, and $lastPart"
        }
    }
}


@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ArticleCardPreview() {
    MaterialTheme {
        ArticleCard(
            article = NewsFeedUiModel(
                articleId = "1",
                title = "Title",
                description = "Description",
                creator = listOf("Creator"),
                pubDate = "2021-09-01",
                imageUrl = "https://www.example.com/image.jpg",
                category = listOf("Category"),
                keywords = listOf("Category"),
            ),
            onClick = {}
        )
    }
}
