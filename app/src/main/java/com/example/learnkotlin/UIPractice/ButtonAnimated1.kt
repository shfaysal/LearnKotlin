package com.example.learnkotlin.UIPractice

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun DemoAnimatedBorder() {
    val colors = listOf(Color(0xFF34C759), Color(0xFF007AFF), Color(0xFFFF2D55)) // iOS-like attractive gradient colors
    val infiniteTransition = rememberInfiniteTransition() // Create infinite transition for animation
    val offset by infiniteTransition.animateFloat(
        initialValue = 0f, // Starting offset value
        targetValue = 1f, // Target offset value
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = LinearEasing
            ), // Animation duration and easing
            repeatMode = RepeatMode.Restart // Reverse animation on repeat
        )
    )

    val brush = remember(offset) {
        object : ShaderBrush() {
            override fun createShader(size: androidx.compose.ui.geometry.Size): Shader { // Create shader based on size
                val widthOffset = size.width * offset // Calculate width offset
                val heightOffset = size.height * offset // Calculate height offset
                return LinearGradientShader(
                    colors = colors, // Apply the attractive iOS-like color list
                    from = Offset(widthOffset, heightOffset), // Starting point of gradient
                    to = Offset(
                        widthOffset + size.width,
                        heightOffset + size.height
                    ), // Ending point of gradient
                    tileMode = TileMode.Mirror // Mirror the gradient effect
                )
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black) // Set black background for entire scaffold
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)// Set box dimensions
                .align(Alignment.Center) // Center the box
                .clip(RoundedCornerShape(24.dp)) // Apply rounded corners
                .border(
                    width = 2.5.dp,
                    brush = brush,
                    shape = RoundedCornerShape(24.dp)
                ) // Add animated border
        )
    }
}