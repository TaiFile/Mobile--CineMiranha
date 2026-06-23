package br.ufscar.cinemiranha.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
private val Background  = Color(0xFF1F2024)
private val Surface     = Color(0xFF2F3036)
private val AccentRed   = Color(0xFFBF0903)
private val TextPrimary = Color(0xFFFAFAFA)
private val TextSecond  = Color(0xFF8F9098)
private val Divider     = Color(0xFF494A50)
private val AgeBadge    = Color(0xFFFF8C00)

private val colorScheme = darkColorScheme(
    primary = AccentRed,
    onPrimary = TextPrimary,
    background = Background,
    onBackground = TextPrimary,
    surface = Surface,
    onSurface = TextPrimary,
    secondary = TextSecond,
    outline = Divider,
    surfaceContainer = Surface,
    tertiary = AgeBadge
)

@Composable
fun CineMiranhaTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = colorScheme,
        content     = content
    )
}