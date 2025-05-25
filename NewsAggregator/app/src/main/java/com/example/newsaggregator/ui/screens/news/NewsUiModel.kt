package com.example.newsaggregator.ui.screens.news

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.ZonedDateTime

data class NewsUiModel @RequiresApi(Build.VERSION_CODES.O) constructor(
    val id: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val date: String,
    val author: String,
    val link: String,
    val isLongPressed: Boolean = false,
    val isExpanded: Boolean = false,
    val parsedDate: ZonedDateTime = ZonedDateTime.now()
)

