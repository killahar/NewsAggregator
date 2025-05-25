package com.example.newsaggregator.domain.news.usecase

import com.example.newsaggregator.domain.news.NewsRepository
import com.example.newsaggregator.domain.news.model.NewsModel
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    suspend operator fun invoke(): List<NewsModel> {
        return repository.getNews()
    }
}