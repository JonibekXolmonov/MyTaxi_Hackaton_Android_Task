package uz.mobile.mytaxitask.presentation.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp

@Composable
fun VisibilityAnimationToStart(
    visible: Boolean,
    density: Density = LocalDensity.current,
    content: @Composable () -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = slideInHorizontally {
            with(density) { -20.dp.roundToPx() }
        } + fadeIn(
            initialAlpha = 0.3f
        ),
        exit = slideOutHorizontally() + fadeOut()
    ) {
        content()
    }
}

@Composable
fun VisibilityAnimationToEnd(
    visible: Boolean,
    density: Density = LocalDensity.current,
    content: @Composable () -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = slideInHorizontally {
            with(density) { 20.dp.roundToPx() }
        } + fadeIn(
            initialAlpha = 0.3f
        ),
        exit = slideOutHorizontally {
            with(density) { 20.dp.roundToPx() }
        } + fadeOut()
    ) {
        content()
    }
}