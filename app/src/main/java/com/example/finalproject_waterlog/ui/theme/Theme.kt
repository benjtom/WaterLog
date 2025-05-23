package com.example.finalproject_waterlog.ui.theme
//
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = AquaBlue,
    onPrimary = Color.Black,

    secondary = PetalPink,
    onSecondary = Color.Black,

    tertiary = GardenGreen,
    onTertiary = Color.Black,

    background = DarkBackground,
    onBackground = OnDarkBackground,

    surface = DarkBackground,
    onSurface = OnDarkBackground,
)

private val LightColorScheme = lightColorScheme(

    primary = AquaBlue,
    onPrimary = Color.Black,

    secondary = PetalPink,
    onSecondary = Color.Black,

    tertiary = GardenGreen,
    onTertiary = Color.Black,

    background = LightBackground,
    onBackground = OnLightBackground,

    surface = LightBackground,
    onSurface = OnLightBackground,
)

@Composable
fun MyAppTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (useDarkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}