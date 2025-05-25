package com.example.newsaggregator.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFE0E1DD),
    secondary = Color(0xFF5E728A),
    tertiary = Color(0xFF415A77),
    background = Color(0xFF1B263B),
    surface = Color(0xE60D1B2A),
    onBackground = Color(0xFFE0E1DD),
    onSurface = Color(0xFFBFC9D9)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFFE0E1DD),
    secondary = Color(0xFF5E728A),
    tertiary = Color(0xFF415A77),
    background = Color(0xFFE0E1DD),
    surface = Color(0xBF5E728A),
    onBackground = Color(0xFF0D1B2A),
    onSurface = Color(0xFF1B263B)
)


@Composable
fun NewsAggregatorTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {

    val colorScheme = when {
//        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//            val context = LocalContext.current
//            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val typography = AppTypography

    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,
        content = content
    )
}
