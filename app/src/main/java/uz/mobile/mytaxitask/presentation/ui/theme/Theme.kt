package uz.mobile.mytaxitask.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = dark_primary_background,
    secondary = dark_secondary_background,
    onPrimary = dark_content_primary,
    onSecondary = dark_content_secondary,
    primaryContainer = dark_button_primary,
    errorContainer = dark_error_container,
    onErrorContainer = dark_onError_container,
    outline = dark_outline,
    onTertiary = dark_onTertiary,
    onSecondaryContainer = dark_onSecondaryContainer,
    onSurface = dark_onSurface
)

private val LightColorScheme = lightColorScheme(
    primary = light_primary_background,
    secondary = light_secondary_background,
    onPrimary = light_content_primary,
    onSecondary = light_content_secondary,
    primaryContainer = light_button_primary,
    errorContainer = light_error_container,
    onErrorContainer = light_onError_container,
    outline = light_outline,
    onTertiary = light_onTertiary,
    onSecondaryContainer = light_onSecondaryContainer,
    onSurface = light_onSurface
)

@Composable
fun MyTaxiAndroidTaskTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}