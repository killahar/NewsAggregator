package com.example.newsaggregator.ui.screens.news

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsaggregator.domain.news.model.NewsModel
import com.example.newsaggregator.domain.news.usecase.GetNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase
) : ViewModel() {

    private val _newsList = mutableStateListOf<NewsUiModel>()
    val newsList: List<NewsUiModel> get() = _newsList

    private val _longPressedCardId = mutableStateOf<String?>(null)
    val longPressedCardId: State<String?> = _longPressedCardId

    init {
        viewModelScope.launch {
            try {
                val news = getNewsUseCase()
                    .map { it.toUiModel() }
                    .sortedByDescending { it.parsedDate }

                _newsList.addAll(news)
            } catch (e: Exception) {
                Log.e("RSS", "Error fetching RSS", e)
            }
        }
    }


    fun toggleCardLongPressed(id: String) {
        _longPressedCardId.value = if (_longPressedCardId.value == id) null else id
        _newsList.replaceAll { it.copy(isLongPressed = it.id == _longPressedCardId.value) }
    }

    fun toggleExpanded(id: String) {
        _newsList.replaceAll {
            if (it.id == id) it.copy(isExpanded = !it.isExpanded) else it
        }
    }


    private fun NewsModel.toUiModel(): NewsUiModel {
        val formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH)
        val parsedDate = ZonedDateTime.parse(date, formatter)

        val now = ZonedDateTime.now(ZoneOffset.UTC)
        val displayDate = when {
            parsedDate.toLocalDate().isEqual(now.toLocalDate()) ->
                "today, ${parsedDate.format(DateTimeFormatter.ofPattern("HH:mm", Locale.ENGLISH))} GMT"

            parsedDate.toLocalDate().isEqual(now.minusDays(1).toLocalDate()) ->
                "yesterday, ${parsedDate.format(DateTimeFormatter.ofPattern("HH:mm", Locale.ENGLISH))} GMT"

            parsedDate.year == now.year ->
                parsedDate.format(DateTimeFormatter.ofPattern("dd MMM, HH:mm", Locale.ENGLISH)) + " GMT"

            else ->
                parsedDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm", Locale.ENGLISH)) + " GMT"
        }

        return NewsUiModel(
            id = id,
            title = title,
            description = description,
            imageUrl = imageUrl,
            date = displayDate,
            author = author,
            link = link,
            parsedDate = parsedDate
        )
    }
}