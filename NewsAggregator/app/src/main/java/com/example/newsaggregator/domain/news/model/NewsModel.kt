package com.example.newsaggregator.domain.news.model

data class NewsModel(
    val id: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val date: String,
    val author: String,
    val link: String
)