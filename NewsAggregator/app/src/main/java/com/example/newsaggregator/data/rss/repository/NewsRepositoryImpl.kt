package com.example.newsaggregator.data.rss.repository

import com.example.newsaggregator.data.rss.RssFeed
import com.example.newsaggregator.domain.news.NewsRepository
import com.example.newsaggregator.domain.news.model.NewsModel
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import java.util.UUID
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val rssFeed: RssFeed
) : NewsRepository {


    override suspend fun getNews(): List<NewsModel> {
        val rssDto = rssFeed.getRss()
        return rssDto.channel.items.map {
            val cleanedDescription = cleanHtml(it.description)
            val extractedLink = extractLinkFromHtml(it.description)

            NewsModel(
                id = createId(it.title, it.pubDate),
                title = it.title,
                description = cleanedDescription,
                imageUrl = it.contents.maxByOrNull { content -> content.width?.toIntOrNull() ?: 0 }?.url ?: "",
                date = it.pubDate,
                author = it.dcCreator,
                link = extractedLink ?: it.link,
            )
        }
    }

    private fun createId(title: String, date: String): String = UUID.nameUUIDFromBytes((title + date).toByteArray()).toString()

    private fun cleanHtml(html: String): String {
        val doc: Document = Jsoup.parse(html)
        return doc.select("p")
            .joinToString("\n\n") { it.text() }
    }

    private fun extractLinkFromHtml(html: String): String? {
        val doc: Document = Jsoup.parse(html)
        val linkElement: Element? = doc.select("a[href]").first()
        return linkElement?.attr("href")
    }
}