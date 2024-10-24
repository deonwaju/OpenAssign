package com.deontch.newsdetail.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.deontch.feature.news.R
import com.deontch.ui.extensions.clickable


@Composable
internal fun NewsImageSection(
    imageUrl: String,
    onImageClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(16.dp))
    ) {
        AsyncImage(
            model = ImageRequest
                .Builder(LocalContext.current)
                .data(imageUrl)
                .build(),
            contentScale = ContentScale.Inside,
            contentDescription = stringResource(R.string.news_image),
            modifier = Modifier
                .clickable { onImageClick() }
                .wrapContentHeight()
                .background(color = Color.White, RoundedCornerShape(16.dp))
        )
    }
}
