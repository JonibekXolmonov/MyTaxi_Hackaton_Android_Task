package uz.mobile.mytaxitask.presentation.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import uz.mobile.mytaxitask.presentation.ui.theme.immutableDark

@Composable
fun TabItem(
    modifier: Modifier = Modifier,
    text: String,
    isActive: Boolean,
    activeContainerColor: Color,
    inActiveContainerColor: Color,
    activeTextColor: Color,
    inActiveTextColor: Color,
    textStyle: TextStyle,
    onTabSelected: () -> Unit,
) {

    val containerColor: Color by animateColorAsState(
        targetValue = if (isActive) {
            activeContainerColor
        } else {
            inActiveContainerColor
        },
        animationSpec = tween(easing = LinearEasing),
        label = "tab_container_color_animation",
    )

    Box(
        modifier = modifier
            .fillMaxHeight()
            .clip(MaterialTheme.shapes.small)
            .background(containerColor)
            .clickable {
                onTabSelected()
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = textStyle.copy(color = if (isActive) activeTextColor else inActiveTextColor)
        )
    }
}

@Composable
fun CustomTab(
    selectedItemIndex: Int,
    items: List<String>,
    modifier: Modifier = Modifier,
    onClick: (index: Int) -> Unit,
) {

    Box(
        modifier = modifier
            .clip(MaterialTheme.shapes.large)
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .clip(MaterialTheme.shapes.large)
                .padding(4.dp),
        ) {
            items.mapIndexed { index, text ->
                val isSelected = index == selectedItemIndex
                TabItem(
                    modifier = Modifier.weight(1f),
                    isActive = isSelected,
                    onTabSelected = {
                        onClick(index)
                    },
                    text = text,
                    activeContainerColor = if (selectedItemIndex == 0) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.primaryContainer,
                    inActiveContainerColor = MaterialTheme.colorScheme.primary,
                    activeTextColor = if (selectedItemIndex == 0) MaterialTheme.colorScheme.onErrorContainer else immutableDark,
                    inActiveTextColor = if (selectedItemIndex == 0) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onPrimary,
                    textStyle = if (index == 0) MaterialTheme.typography.displayMedium else MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}