package com.example.newsaggregator.ui.navigation

enum class BottomItemContent(val title: String, val route: String) {
    NewsFeed("News Feed", Graph.NEWS),
    Bookmark("Bookmark", Graph.BOOKMARK),
    Search("Search", Graph.SEARCH)
}