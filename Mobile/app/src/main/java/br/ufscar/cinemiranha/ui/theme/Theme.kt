package br.ufscar.cinemiranha.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val colorScheme = darkColorScheme(
    background       = Color(0xFF1F2024),
    surface          = Color(0xFF1F2024),
    surfaceContainer = Color(0xFF1F2024),
    primary          = Color(0xFFBF0903),
    onPrimary        = Color(0xFFFAFAFA),
    onBackground     = Color(0xFFFAFAFA),
    onSurface        = Color(0xFFFAFAFA),
)

@Composable
fun CineMiranhaTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = colorScheme,
        content     = content
    )
}
