package com.example.newsaggregator.ui.screens.news.card

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.example.newsaggregator.ui.screens.news.NewsUiModel
import com.example.newsaggregator.ui.screens.news.card.actions.bookmark.BookmarkButton
import com.example.newsaggregator.ui.screens.news.card.actions.share.ShareButton
import com.example.newsaggregator.ui.screens.news.card.actions.share.ShareInfo
import com.example.newsaggregator.ui.screens.news.card.actions.web.WebButton

@Composable
fun NewsCard(
    news: NewsUiModel,
    onLongPress: (String) -> Unit,
    onExpandToggle: (String) -> Unit,
    navController: NavHostController
) {

    Card(
        modifier = Modifier
            .padding(8.dp)
            .pointerInput(news.id) {
                detectTapGestures(onLongPress = { onLongPress(news.id) })
            },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                Image(
                    imageUrl = news.imageUrl,
                    isLongPressed = news.isLongPressed
                )

                Spacer(modifier = Modifier.height(8.dp))

                Body(
                    news = news,
                    onLongPress = { onLongPress(news.id)},
                    onExpandToggle = {onExpandToggle(news.id)}
                )

                Spacer(modifier = Modifier.height(8.dp))

                InformationRow(
                    author = news.author,
                    date = news.date)
            }

            Row(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ShareButton(
                    shareInfo = ShareInfo(
                        imageUrl = news.imageUrl,
                        tittle = news.title,
                        description = news.description
                    ),
                    isVisible = news.isLongPressed
                )
                BookmarkButton(
                    isVisible = news.isLongPressed
                )
                WebButton(
                    isVisible = news.isLongPressed,
                    navigate = { navController.navigate("webview/${Uri.encode(news.link)}") }
                )
            }
        }
    }
}

@Composable
fun Body(
    news: NewsUiModel,
    onLongPress: () -> Unit,
    onExpandToggle: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = news.title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = {
                            onLongPress()
                        },
                        onTap = {
                            onExpandToggle()
                        }
                    )
                }
                .animateContentSize()
        ) {
            Text(
                text = news.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = if (news.isExpanded) Int.MAX_VALUE else 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun Image(imageUrl: String, isLongPressed: Boolean) {
    val animatedSize by animateFloatAsState(
        targetValue = if (isLongPressed) 300f else 180f,
        animationSpec = tween(durationMillis = 1000, delayMillis = 500),
        label = "sizeAnimation"
    )

    val alpha by animateFloatAsState(
        targetValue = if (isLongPressed) 1f else 0f,
        animationSpec = tween(durationMillis = 2500),
        label = "alphaAnimation"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
            .clip(RoundedCornerShape(16.dp))
            .height(animatedSize.dp)
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop,
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.surface.copy(alpha = 0.7f),
                            Color.Transparent
                        )
                    ),
                    alpha = alpha
                )
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun InformationRow(author: String, date: String) {
    var showFullAuthor by remember { mutableStateOf(false) }
    var isOverflowing by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier.weight(1f).padding(end = 8.dp).let {
                    if (isOverflowing) {
                        it.pointerInput(Unit) {
                            detectTapGestures(onPress = { showFullAuthor = !showFullAuthor })
                        }
                    } else it
                }) {
            AnimatedContent(
                targetState = showFullAuthor && isOverflowing, transitionSpec = {
                    fadeIn(tween(200)) with fadeOut(tween(200))
                }, label = "AuthorTransition"
            ) { expanded ->
                if (expanded) {
                    Surface(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .shadow(4.dp, RoundedCornerShape(6.dp)),
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = author,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                            modifier = Modifier.padding(6.dp),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                } else {
                    Text(
                        text = author,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        style = MaterialTheme.typography.labelMedium,
                        fontStyle = FontStyle.Italic,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        onTextLayout = { textLayoutResult ->
                            isOverflowing = textLayoutResult.hasVisualOverflow
                        })
                }
            }
        }

        Text(
            text = date,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun PreviewNewsCard() {
    NewsCard(
        news = NewsUiModel(
            id = "12323545235",
            title = "Заголовок новости. Заголовок новости. Заголовок новости. Заголовок новости.",
            description = "Описание новости. Описание новости. Описание новости. Описание новости. " + "Описание новости. Описание новости. Описание новости. Описание новости." + "Описание новости. Описание новости. Описание новости. Описание новости.",
            imageUrl = "https://lh3.googleusercontent.com/a/ACg8ocKyWpvLQBbHFl99GmHTyoE_NbWRG_wmTxh-JNAq0tKQYksgmyUu=s432-c-no",
            date = "19 мая 2025",
            author = "Иван Иванов",
            isLongPressed = true,
            link = ""
        ),
        onLongPress = {},
        onExpandToggle = {},
        navController = rememberNavController()
    )
}