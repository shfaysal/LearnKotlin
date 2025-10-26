package com.example.learnkotlin

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AnimatedBrushBorderBox(
    modifier: Modifier = Modifier,
    strokeWidth: Dp = 5.dp,
    cornerRadius: Dp = 20.dp,
    animationSpeed: Float = 600f, // lower = slower
    content: @Composable BoxScope.() -> Unit
) {
    // Infinite animation to "shift" the brush position
    val infiniteTransition = rememberInfiniteTransition(label = "brushShift")
    val shift by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = animationSpeed.toInt(), easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "brushShiftAnim"
    )

    Box(
        modifier = modifier.drawBehind {
            val sw = strokeWidth.toPx()
            val inset = sw / 2f
            val rect = Rect(
                inset,
                inset,
                size.width - inset,
                size.height - inset
            )
            val roundRect = RoundRect(rect, cornerRadius.toPx(), cornerRadius.toPx())

            // Animate the brush offset to make it appear like the color is moving
            val brush = Brush.linearGradient(
                colors = listOf(
                    Color(0xFF00BCD4),
                    Color(0xFF8BC34A),
                    Color(0xFFFFC107),
                    Color(0xFF00BCD4)
                ),
                start = Offset(shift % size.width, 0f),
                end = Offset((shift % size.width) + size.width, size.height)
            )

            drawPath(
                path = Path().apply { addRoundRect(roundRect) },
                brush = brush,
                style = Stroke(width = sw, cap = StrokeCap.Round)
            )
        }
    ) {
        Box(
            modifier = Modifier.padding(strokeWidth * 2),
            content = content
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AnimatedBrushBorderBoxPreview() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            AnimatedBrushBorderBox(
                modifier = Modifier.fillMaxWidth().height(100.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0x1100BCD4)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Loading...",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Black
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewAnimatedBorderCard() {
    AnimatedBorderCard(
        modifier = Modifier.padding(20.dp).fillMaxWidth().height(80.dp)
    ) {
        Box(
            modifier = Modifier,
            contentAlignment = Alignment.Center
        ){
            Text(
                text = "Animated Border Card",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black
            )
        }
    }
}
@Composable
fun AnimatedBorderCard(
    modifier: Modifier = Modifier,
//    shape: Shape = RoundedCornerShape(8.dp),
    borderWidth: Dp = 2.dp,
    gradientColors: List<Color> = listOf(Color.Magenta, Color.Cyan),
    animationDuration: Int = 200,
    content: @Composable () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "animatedBorder")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(animationDuration, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = "borderAngle"
    )

    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .padding(borderWidth) // Padding to make space for the border
            .drawWithContent {
                // Draw the animated border
                rotate(angle) {
                    drawCircle(
                        brush = Brush.sweepGradient(gradientColors),
                        radius = size.width / 2, // Adjust as needed for desired effect
                        blendMode = BlendMode.SrcIn // Or other blend modes
                    )
                }
                drawContent() // Draw the card's content
            },
        shape = RoundedCornerShape(8.dp),
        // Other Card/Surface properties
    ) {
        content()
    }
}
