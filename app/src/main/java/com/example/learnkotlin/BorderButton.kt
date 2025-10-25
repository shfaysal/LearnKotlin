package com.example.learnkotlin

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay


@Preview(showBackground = true)
@Composable
fun PreviewProgressBorderBox() {
    var progress by remember { mutableStateOf(0f) }

    // Loop the progress for preview animation
    LaunchedEffect(Unit) {
        while (true) {
            progress += 0.01f
            if (progress > 1f) progress = 0f
            delay(30)
        }
    }

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            ProgressBorderBox(
                modifier = Modifier.size(200.dp),
                progress = progress,
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF00BCD4),
                        Color(0xFF8BC34A),
                        Color(0xFFFFC107)
                    )
                ),
                strokeWidth = 5.dp,
                cornerRadius = 20.dp
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0x1100BCD4)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${(progress * 100).toInt()}%",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Black
                    )
                }
            }
        }
    }
}



/**
 * Draws a rounded-rect progress border using a Brush.
 *
 * @param progress 0f..1f fraction of the perimeter to show.
 * @param strokeWidth Border thickness (default 5.dp).
 * @param brush Brush used to stroke the border (can be solid or gradient).
 * @param cornerRadius Corner radius for the rounded rectangle.
 */
@Composable
fun ProgressBorderBox(
    modifier: Modifier = Modifier,
    progress: Float,
    strokeWidth: Dp = 5.dp,
    brush: Brush,
    cornerRadius: Dp = 12.dp,
    contentPadding: Dp = 8.dp,
    content: @Composable BoxScope.() -> Unit
) {
    val clamped = progress.coerceIn(0f, 1f)

    Box(
        modifier = modifier.drawBehind {
            val sw = strokeWidth.toPx()
            val r = cornerRadius.toPx().coerceAtMost(minOf(size.width, size.height) / 2f)
            // Inset so the stroke stays fully inside the bounds
            val inset = sw / 2f
            val rect = Rect(
                Offset(inset, inset),
                size.copy(width = size.width - sw, height = size.height - sw)
            )

            // Rounded-rect path
            val path = Path().apply {
                addRoundRect(RoundRect(rect, r, r))
            }

            // Perimeter of rounded rectangle:
            // straight segments + full circle worth of corners (4 × quarter circle)
            val w = rect.width
            val h = rect.height
            val perimeter = 2f * (w + h - 4f * r) + (2f * PI.toFloat() * r)

            // Show one "dash" whose length = progress * perimeter, then a gap for the rest.
            val dash = PathEffect.dashPathEffect(
                floatArrayOf(perimeter * clamped, perimeter),
                /* phase = */ 0f
            )

            drawPath(
                path = path,
                brush = brush,
                style = Stroke(width = sw, pathEffect = dash)
            )
        }
    ) {
        // Keep content away from the stroke
        Box(Modifier.padding(contentPadding)) {
            content()
        }
    }
}

/* ---------- Example usage with animation ---------- */

@Composable
fun SampleProgressBorder(
    modifier: Modifier = Modifier,
    targetProgress: Float,
    brush: Brush
) {
    // Smoothly animate to the new progress
    val animated by animateFloatAsState(
        targetValue = targetProgress.coerceIn(0f, 1f),
        animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
        label = "borderProgress"
    )

    ProgressBorderBox(
        modifier = modifier,
        progress = animated,
        strokeWidth = 5.dp,
        brush = brush,
        cornerRadius = 14.dp
    ) {
        // Your content goes here
    }
}
