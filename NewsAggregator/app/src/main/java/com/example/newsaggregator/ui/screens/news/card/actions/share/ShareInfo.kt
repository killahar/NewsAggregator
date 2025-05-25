package com.example.newsaggregator.ui.screens.news.card.actions.share

data class ShareInfo(
    private val imageUrl: String,
    private val tittle: String,
    private val description: String,
) {
    fun text() = "$tittle\n\n$description\n\n$imageUrl"
    fun imageUrl() = imageUrl
}