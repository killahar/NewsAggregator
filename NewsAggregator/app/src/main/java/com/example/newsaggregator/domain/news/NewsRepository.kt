package com.example.newsaggregator.domain.news

import com.example.newsaggregator.domain.news.model.NewsModel

interface NewsRepository {
    suspend fun getNews(): List<NewsModel>
}
