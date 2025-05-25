package com.example.newsaggregator.ui.screens.news.card.actions.share

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.newsaggregator.R

@Composable
fun ShareButton(
    shareInfo: ShareInfo,
    isVisible: Boolean
) {
    val context = LocalContext.current

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(animationSpec = tween(durationMillis = 500, delayMillis = 1000)),
        exit = fadeOut(animationSpec = tween(durationMillis = 500))
    ) {
        IconButton(onClick = {
            ShareHelper.share(
                context = context,
                imageUrl = shareInfo.imageUrl(),
                text = shareInfo.text()
            )
        }) {
            Icon(
                painter = painterResource(R.drawable.share),
                contentDescription = stringResource(R.string.share),
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}
