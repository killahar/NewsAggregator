package com.example.newsaggregator.ui.navigation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.newsaggregator.data.rss.RssFeed
import com.example.newsaggregator.ui.network.HandleInternetConnection

@Composable
fun MainScreen(feed: RssFeed) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { padding ->
        HandleInternetConnection()
        Box(modifier = Modifier.padding(padding)) {
            RootNavigationGraph(navController = navController)
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {

    val screens = BottomItemContent.entries.toTypedArray()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val shouldShowBottomBar = screens.any { screen ->
        currentDestination?.hierarchy?.any { it.route == screen.route } == true
    }

    if (shouldShowBottomBar) {
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.surface,
            tonalElevation = 100.dp
        ) {
            screens.forEach { screen ->

                val selected = currentDestination?.hierarchy?.any {
                    it.route == screen.route
                } == true

                NavigationBarItem(
                    selected = selected,
                    onClick = {
                        if (!selected) {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    icon = {
                        BottomBarItem(selected, screen.title)
                    },
                    colors = NavigationBarItemColors(
                        selectedIconColor = Color.Unspecified,
                        unselectedIconColor = Color.Unspecified,
                        selectedTextColor = Color.Transparent,
                        unselectedTextColor = Color.Transparent,
                        selectedIndicatorColor = Color.Transparent,
                        disabledIconColor = Color.Unspecified,
                        disabledTextColor = Color.Unspecified,
                    )
                )
            }
        }
    }
}

@Composable
fun BottomBarItem(
    selected: Boolean,
    text: String
) {
    val animatedSize by animateFloatAsState(
        targetValue = if (selected) 24f else 18f,
        animationSpec = tween(durationMillis = 800),
        label = "fontSizeAnimation"
    )

    val verticalOffset by animateFloatAsState(
        targetValue = if (selected) (-6f) else 0f,
        animationSpec = tween(durationMillis = 500, delayMillis = 500),
        label = "offsetAnimation"
    )

    val animatedColor by animateColorAsState(
        targetValue = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
        animationSpec = tween(durationMillis = 500),
        label = "colorAnimation"
    )

    Text(
        text = text,
        modifier = Modifier
            .fillMaxWidth()
            .offset(y = verticalOffset.dp),
        textAlign = TextAlign.Center,
        style = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = animatedSize.sp,
            color = animatedColor
        )
    )
}
