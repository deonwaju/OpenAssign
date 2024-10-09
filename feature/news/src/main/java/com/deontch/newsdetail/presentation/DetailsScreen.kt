package com.deontch.newsdetail.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.deontch.feature.news.R
import com.deontch.newsdetail.presentation.components.ZoomableImage
import com.deontch.newsdetail.presentation.model.NewsDetailUiModel
import com.deontch.ui.theme.Dimens.MediumPadding1
import com.deontch.ui.theme.Dimens.SimpleProductCardHeight
import com.deontch.ui.theme.Dimens.SimpleProductCardWidth

@Composable
internal fun DetailsScreen(
    article: NewsDetailUiModel,
    isFullScreen: Boolean,
    onImageClick: (Boolean) -> Unit
) = with(article) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        AsyncImage(
            modifier = Modifier
                .wrapContentHeight()
                .size(height = SimpleProductCardWidth, width = SimpleProductCardHeight)
                .fillMaxWidth()
                .clickable { onImageClick(true) }
                .clip(MaterialTheme.shapes.medium),
            model = ImageRequest.Builder(context = context)
                .data(article.imageUrl).build(),
            contentDescription = null,
            contentScale = ContentScale.Inside
        )
        Spacer(modifier = Modifier.height(MediumPadding1))
        Text(
            text = article.title,
            style = MaterialTheme.typography.titleMedium,
            color = colorResource(id = R.color.text_title),
            modifier = Modifier.padding(vertical = MediumPadding1)
        )
        Text(
            text = article.description,
            style = MaterialTheme.typography.bodyLarge,
            color = colorResource(id = R.color.body)
        )
    }

    AnimatedVisibility(
        visible = isFullScreen,
        enter = fadeIn(animationSpec = tween(durationMillis = 300)) + scaleIn(initialScale = 0.9f),
        exit = fadeOut(animationSpec = tween(durationMillis = 300)) + scaleOut(targetScale = 0.9f),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.8f))
                .clickable { onImageClick(false) },
            contentAlignment = Alignment.Center
        ) {
            ZoomableImage(
                imageUrl = imageUrl,
                cornerRadius = 16
            )
        }
    }
}
