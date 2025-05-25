package com.example.newsaggregator.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.newsaggregator.data.rss.RssFeed
import com.example.newsaggregator.ui.navigation.MainScreen
import com.example.newsaggregator.ui.theme.NewsAggregatorTheme
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.hilt.android.AndroidEntryPoint
import nl.adaptivity.xmlutil.serialization.XML
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

private val retrofit = Retrofit.Builder()
    .baseUrl("https://www.theguardian.com")
    .addConverterFactory(
        XML.asConverterFactory(
            "application/xml; charset=UTF8".toMediaType()
        )
    ).build()

private val guardian = retrofit.create(RssFeed::class.java)

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsAggregatorTheme {
                MainScreen(guardian)
            }
        }
    }
}

//@Composable
//fun MainScreen(
//    feed: RssFeed,
//) {
//    /* val scope = rememberCoroutineScope()
//    Button(
//        onClick = {
//            Log.d("happy", "done")
//            scope.launch {
//                val r = feed.getRss()
//                r.channel.items.forEach {
//                    Log.d("link", it.link)
//                    Log.d("guid", it.guid)
//                    Log.d("dcDate", it.dcDate)
//                    Log.d("pubDate", it.pubDate)
//                }
//            }
//        }
//    ) {} */
//
//    Column(modifier = Modifier.fillMaxSize()) {
//        HandleInternetConnection()
//        RootNavigationGraph(navController = rememberNavController())
//    }
//}