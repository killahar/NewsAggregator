package com.example.newsaggregator.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.interaction.HoverInteraction
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.newsaggregator.ui.screens.bookmark.BookmarkScreen
import com.example.newsaggregator.ui.screens.news.NewsFeedScreen
import com.example.newsaggregator.ui.screens.news.SearchScreen
import com.example.newsaggregator.ui.screens.news.card.actions.web.WebViewScreen

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun RootNavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Graph.MAIN
    ) {
        navigation(
            startDestination = Graph.NEWS,
            route = Graph.MAIN
        ) {
            composable(route = Graph.NEWS) {
                EnterAnimation {
                    NewsFeedScreen(navController)
                }
            }
            composable(route = Graph.BOOKMARK) {
                EnterAnimation {
                    BookmarkScreen(navController)
                }
            }
            composable(route = Graph.SEARCH) {
                EnterAnimation {
                    SearchScreen(navController)
                }
            }
            composable(
                route = "webview/{url}",
                arguments = listOf(navArgument("url") { type = NavType.StringType })
            ) { backStackEntry ->
                val url = backStackEntry.arguments?.getString("url") ?: ""
                WebViewScreen(link = url, navController)
            }
        }
    }
}

object Graph {
    const val NEWS = "news_feed"
    const val BOOKMARK = "bookmark"
    const val SEARCH = "search"
    const val MAIN = "main"
}

@Composable
fun EnterAnimation(content: @Composable () -> Unit) {
    AnimatedVisibility(
        visibleState = MutableTransitionState(
            initialState = false
        ).apply { targetState = true },
        modifier = Modifier,
        enter = slideInVertically(
            initialOffsetY = { -40 }
        ) + expandVertically(
            expandFrom = Alignment.Top
        ) + fadeIn(initialAlpha = 0.3f),
        exit = slideOutVertically() + shrinkVertically() + fadeOut(),
    ) {
        content()
    }
}