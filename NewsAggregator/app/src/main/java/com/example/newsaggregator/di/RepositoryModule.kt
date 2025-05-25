package com.example.newsaggregator.di

import com.example.newsaggregator.data.rss.repository.NewsRepositoryImpl
import com.example.newsaggregator.data.rss.RssFeed
import com.example.newsaggregator.domain.news.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideNewsRepository(
        rssFeed: RssFeed
    ): NewsRepository {
        return NewsRepositoryImpl(rssFeed)
    }
}