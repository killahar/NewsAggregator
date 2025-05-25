package com.example.newsaggregator.ui.screens.news

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.newsaggregator.ui.screens.news.card.NewsCard

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun NewsFeedScreen(navController: NavHostController) {
    val viewModel: NewsViewModel = hiltViewModel()
    val newsList = viewModel.newsList
    val longPressedCardId by viewModel.longPressedCardId

    val listState = rememberLazyListState()

    LaunchedEffect(longPressedCardId) {
        longPressedCardId?.let { selectedId ->
            val index = newsList.indexOfFirst { it.id == selectedId }
            if (index != -1) {
                val targetOffset =
                    listState.layoutInfo.visibleItemsInfo.firstOrNull { it.index == index }?.offset
                        ?: 0
                val scrollDistance = targetOffset - 100
                val durationMillis = 800
                listState.animateScrollBy(
                    scrollDistance.toFloat(),
                    animationSpec = tween(durationMillis)
                )
            }
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            state = listState,
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp)
        ) {
            items(newsList.size) { index ->
                val news = newsList[index]

                NewsCard(
                    news = news,
                    onLongPress = {
                        viewModel.toggleCardLongPressed(news.id)
                    },
                    onExpandToggle = {
                        viewModel.toggleExpanded(news.id)
                    },
                    navController = navController
                )
            }
        }
    }
}


@Composable
fun BookmarkScreen(navController: NavHostController) {
    Text(
        modifier = Modifier.padding(20.dp),
        text = "Bookmark Screen.",
        style = MaterialTheme.typography.displayMedium,
        color = MaterialTheme.colorScheme.onBackground
    )
}

@Composable
fun SearchScreen(navController: NavHostController) {
    Text(
        modifier = Modifier.padding(20.dp),
        text = "Search Screen.",
        style = MaterialTheme.typography.displayMedium,
        color = MaterialTheme.colorScheme.onBackground
    )
}