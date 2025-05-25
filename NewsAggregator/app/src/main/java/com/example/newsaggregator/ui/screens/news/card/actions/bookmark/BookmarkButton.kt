package com.example.newsaggregator.ui.screens.news.card.actions.bookmark

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.newsaggregator.R

@Composable
fun BookmarkButton(isVisible: Boolean) {

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(animationSpec = tween(durationMillis = 500, delayMillis = 1000)),
        exit = fadeOut(animationSpec = tween(durationMillis = 500))
    ) {
        IconButton(onClick = {  }) {
            Icon(
                painter = painterResource(R.drawable.bookmark),
                contentDescription = "Save",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}