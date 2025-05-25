package com.example.newsaggregator.ui.screens.bookmark

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun BookmarkScreen(navController: NavHostController) {
    Text(
        modifier = Modifier.padding(20.dp),
        text = "Bookmark Screen.",
        style = MaterialTheme.typography.displayMedium,
        color = MaterialTheme.colorScheme.onBackground
    )
}