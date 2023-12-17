package com.laivinieks.georemind.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Primary,
    onPrimary = Text,
    secondary = Secondary,
    background = DarkBackground,
    onBackground = Text,
    tertiary = Accent1,
    onTertiaryContainer = Accent2,
    onTertiary = Text,
    onError = Success,
    error = Error,
    onPrimaryContainer = Text // Text color
)

// Dark Theme Colors
private val LightColorScheme = lightColorScheme(
    primary = DarkPrimary,
    onPrimary = Text,
    secondary = DarkSecondary,
    background = Background,
    onBackground = DarkText,
    tertiary = DarkAccent1,
    onTertiary = Text,
    onTertiaryContainer = Accent2,
    onError = DarkSuccess,
    error = DarkError,
    onPrimaryContainer = DarkText // Text color
)

//private val DarkColorScheme = darkColorScheme(
//    primary = Purple80,
//    secondary = PurpleGrey80,
//    tertiary = Pink80
//)
//
//private val LightColorScheme = lightColorScheme(
//    primary = Purple40,
//    secondary = PurpleGrey40,
//    tertiary = Pink40
//
//    /* Other default colors to override
//    background = Color(0xFFFFFBFE),
//    surface = Color(0xFFFFFBFE),
//    onPrimary = Color.White,
//    onSecondary = Color.White,
//    onTertiary = Color.White,
//    onBackground = Color(0xFF1C1B1F),
//    onSurface = Color(0xFF1C1B1F),
//    */
//)


val OnLightCustomColorsPalette = CustomColorsPalette(
    noteColor1 = MarkColor1,
    noteColor2 = MarkColor2,
    noteColor3 = MarkColor3,
    noteColor4 = MarkColor4,
    noteColor5 = MarkColor5,
    noteColor6 = MarkColor6

)
val OnDarkCustomColorsPalette = CustomColorsPalette(
    noteColor1 = DMarkColor1,
    noteColor2 = DMarkColor2,
    noteColor3 = DMarkColor3,
    noteColor4 = DMarkColor4,
    noteColor5 = DMarkColor5,
    noteColor6 = DMarkColor6

)

@Composable
fun GeoRemindTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
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

    val customColorsPalette =
        if (darkTheme) OnDarkCustomColorsPalette
        else OnLightCustomColorsPalette


    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            window.navigationBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }
    CompositionLocalProvider(
        LocalCustomColorsPalette provides customColorsPalette
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content,

            )
    }
}