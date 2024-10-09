package com.deontch.newsdetail.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.deontch.feature.news.R
import com.deontch.ui.components.CenteredColumn
import com.deontch.ui.utils.boldTexStyle

@Composable
fun CenteredText(
    buttonText: String,
    text: String, onCenteredTextAction: (() -> Unit)? = null
) {
    CenteredColumn {
        Text(
            text = text, modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Center,
            style = boldTexStyle(size = 14)
        )



        if (onCenteredTextAction != null) {
            Button(onClick = onCenteredTextAction) {
                Text(text = buttonText)
            }
        }
    }
}