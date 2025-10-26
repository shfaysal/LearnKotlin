package com.example.learnkotlin.UIPractice

import android.annotation.SuppressLint
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.random.Random

@SuppressLint("ModifierFactoryUnreferencedReceiver")
@Composable
fun Modifier.animatedBorder(
    borderColors: List<Color>,
    backGroundColor: Color,
    shape: Shape = RoundedCornerShape(8.dp),
    borderWidth: Dp = 1.dp,
    animationDuration: Int = 1000,
    easing: Easing = LinearEasing
): Modifier {

    val brush = Brush.sweepGradient(borderColors)
    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            tween(
                durationMillis = animationDuration,
                easing = easing
            ),
            repeatMode = RepeatMode.Restart
        ) ,
        label = "angleAnimation"
    )

    return this
        .clip(shape)
        .padding(borderWidth)
        .drawWithContent {
            rotate(angle){
                drawCircle(
                    brush = brush,
                    radius = size.width,
                    blendMode = BlendMode.SrcIn
                )
            }
            drawContent()
        }
        .background(color = backGroundColor, shape = shape)

}

@Composable
fun FlatButton(
    borderColors: List<Color> = listOf(Color.Red, Color.Green,Color.Red, Color.Green,Color.Red, Color.Green),
    backGroundColor: Color = Color.Green,
    shape: Shape = RoundedCornerShape(8.dp),
    borderWidth: Dp = 2.dp,
    inactiveColor: List<Color> = listOf(Color.Red, Color.Red,Color.Red, Color.Red,Color.Red, Color.Red),
    animationDuration: Int = 1000,
    easing: Easing = LinearEasing,
    text : String = "",
    enable: Boolean = true
){
//    val inactiveColor = listOf(Color.Red, Color.Red,Color.Red, Color.Red,Color.Red, Color.Red)

    var color by rememberSaveable { mutableStateOf(if (enable) borderColors else inactiveColor) }
//    val activeColor = listOf(Color.Red, Color.Green,Color.Red, Color.Green,Color.Red, Color.Green)



//    LaunchedEffect(Unit) {
//        while (true){
//            val num = Random.nextInt()
//            color = if (num % 2 == 0) activeColor else inactiveColor
//
//            delay(3000)
//        }
//    }





    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clip(shape)
            .animatedBorder(
                borderColors = if (enable) borderColors else inactiveColor,
                backGroundColor = backGroundColor,
                shape = shape,
                borderWidth = borderWidth,
                animationDuration = animationDuration,
                easing = easing
            ),
        contentAlignment = Alignment.Center
    ) {

        Box(
            modifier = Modifier.matchParentSize().clip(shape),
            contentAlignment = Alignment.Center
        ) {
                    Text(text)

        }


    }
}


@Preview
@Composable
fun AnimatedBorderDemo(){
    Column (
        modifier = Modifier.fillMaxSize().padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        var isEnable by remember { mutableStateOf(true) }

        LaunchedEffect(Unit) {
            delay(4000)
        }

//        Thread.sleep(4000)

        LaunchedEffect(Unit) {
            while (true){
                delay(2000)
                isEnable = !isEnable
            }
        }



        FlatButton(enable = isEnable)

    }
}


















