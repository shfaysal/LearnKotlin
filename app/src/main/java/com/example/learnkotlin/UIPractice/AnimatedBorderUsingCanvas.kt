package com.example.learnkotlin.UIPractice

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AnimatedBorderButtonUsingCanvas(){

    val colorBg = Color.White
    val colors =
        listOf(
            Color(0xFFFF595A),
            Color(0xFFFFC766),
            Color(0xFF35A07F),
//            Color(0xFF35A07F),
//            Color(0xFFFFC766),
//            Color(0xFFFF595A)
        )

    val brush = Brush.sweepGradient(colors)

    val infiniteTransition = rememberInfiniteTransition(label = "")
    val angle by
    infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec =
            infiniteRepeatable(
                animation = tween(500, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ),
        label = ""
    )

    Canvas(
        modifier = Modifier.fillMaxWidth().height(80.dp).background(colorBg)
    ) {


        rotate(degrees = angle) {
            drawCircle(
                brush = brush,
                radius = size.width,
                blendMode = BlendMode.SrcIn,
            )
        }

        drawRoundRect(
            color = colorBg,
            topLeft = Offset(1.dp.toPx(), 1.dp.toPx()),
            size = Size(
                width = size.width - 2.dp.toPx(),
                height = size.height - 2.dp.toPx()
            ),
            cornerRadius = CornerRadius(
                x = 19.dp.toPx(),
                y = 19.dp.toPx()
            )
        )
    }
}

@Composable
fun CardWithAnimatedBorder(
    modifier: Modifier = Modifier,
    onCardClick: () -> Unit = {},
    borderColors: List<Color> = emptyList(),
    content: @Composable () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition()
    val angle by
    infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec =
            infiniteRepeatable(
                animation = tween(1000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            )
    )

    val brush =
        if (borderColors.isNotEmpty()) Brush.sweepGradient(borderColors)
        else Brush.sweepGradient(listOf(Color.Gray, Color.White))

    Surface(modifier = modifier.clickable { onCardClick() }, shape = RoundedCornerShape(20.dp)) {
        Surface(
            modifier =
                Modifier.clipToBounds().fillMaxWidth().padding(2.dp).drawWithContent {
                    rotate(angle) {
                        drawCircle(
                            brush = brush,
                            radius = size.width,
                            blendMode = BlendMode.SrcIn,
                        )
                    }
                    drawContent()
                },
            color = Color.White,
            shape = RoundedCornerShape(19.dp)
        ) {
            Box(modifier = Modifier.padding(8.dp)) { content() }
        }
    }
}


@Preview
@Composable
fun PreviewAnimatedButtonBorder(){
//    AnimatedBorderButtonUsingCanvas()

    val colors =
        listOf(
            Color(0xFFFF595A),
            Color(0xFFFFC766),
            Color(0xFF35A07F),
            Color(0xFF35A07F),
            Color(0xFFFFC766),
            Color(0xFFFF595A)
        )

    Box(
        modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp),
        contentAlignment = Alignment.Center
    ){
        CardWithAnimatedBorder (
            modifier = Modifier.fillMaxWidth().height(80.dp),
            borderColors = colors
        ) {

        }
    }


}