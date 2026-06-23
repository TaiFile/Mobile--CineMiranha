package br.ufscar.cinemiranha.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape

private val Background  = Color(0xFF1F2024)
private val Surface     = Color(0xFF2F3036)
private val AccentRed   = Color(0xFFBF0903)
private val TextPrimary = Color(0xFFFAFAFA)
private val TextSecond  = Color(0xFF8F9098)
private val Divider     = Color(0xFF494A50)
private val AgeBadge    = Color(0xFFFF8C00)

private val colorScheme = darkColorScheme(
    primary           = AccentRed,
    onPrimary         = TextPrimary,
    secondary         = TextSecond,
    background        = Background,
    onBackground      = TextPrimary,
    surface           = Surface,
    onSurface         = TextPrimary,
    outline           = Divider,
    surfaceContainer  = Surface,
    tertiary          = AgeBadge,
)

private val typography = Typography(
    titleLarge  = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold),
    titleMedium = TextStyle(fontSize = 17.sp, fontWeight = FontWeight.Bold),
    titleSmall  = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Bold),
    bodyLarge   = TextStyle(fontSize = 14.sp),
    bodyMedium  = TextStyle(fontSize = 13.sp),
    bodySmall   = TextStyle(fontSize = 12.sp),
    labelLarge  = TextStyle(fontSize = 11.sp),
    labelSmall  = TextStyle(fontSize = 10.sp),
)

private val shapes = Shapes(
    extraSmall = RoundedCornerShape(Dimens.RadiusXS),
    small      = RoundedCornerShape(Dimens.RadiusS),
    medium     = RoundedCornerShape(Dimens.RadiusM),
)

@Composable
fun CineMiranhaTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = colorScheme,
        typography  = typography,
        shapes      = shapes,
        content     = content
    )
}
