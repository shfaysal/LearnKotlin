package com.example.learnkotlin

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import kotlin.math.abs

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomCollapsingTopBarExample() {
    val density = LocalDensity.current

    var boxHeightPx by remember { mutableStateOf(0) }
    val boxHeightDp = with(density) { boxHeightPx.toDp() }


    var measuredExpandedHeight by remember { mutableStateOf(400.dp) } // default guess
    var toolbarHeight by remember { mutableStateOf(400.dp) }

    val collapsedHeight = 100.dp
    val expandedHeight = toolbarHeight

    // Animate toolbar height so transitions look smooth
    val animatedToolbarUiHeight by animateDpAsState(
        targetValue = toolbarHeight,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessMediumLow
        ),
        label = "ToolbarHeightAnimation"
    )

    // Custom scroll connection
    val nestedScrollConnection = remember(density, collapsedHeight, measuredExpandedHeight) {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val currentTargetHeight = toolbarHeight
                val delta = available.y
                val deltaDp = with(density) { delta.toDp() }

                val newTargetHeight =
                    (currentTargetHeight + deltaDp).coerceIn(collapsedHeight, measuredExpandedHeight)
                val consumedDp = newTargetHeight - currentTargetHeight

                if (consumedDp != 0.dp) {
                    toolbarHeight = newTargetHeight
                }
                return Offset(x = 0f, y = with(density) { consumedDp.toPx() })
            }

            override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
                val currentTargetHeight = toolbarHeight
                val halfway = collapsedHeight + (measuredExpandedHeight - collapsedHeight) / 2
                val targetHeightAfterFling =
                    if (currentTargetHeight < halfway) collapsedHeight else measuredExpandedHeight

                if (currentTargetHeight != targetHeightAfterFling) {
                    toolbarHeight = targetHeightAfterFling
                }
                return super.onPostFling(consumed, available)
            }


//            override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
//                val currentTargetHeight = toolbarHeight
//                val flingThreshold = 5000f
//
//                val targetHeightAfterFling: Dp = if (abs(available.y) > flingThreshold) {
//                    if (available.y < 0) collapsedHeight else expandedHeight
//                } else {
//                    val halfway = collapsedHeight + (expandedHeight - collapsedHeight) / 2
//                    if (currentTargetHeight < halfway) collapsedHeight else expandedHeight
//                }
//
//                if (currentTargetHeight != targetHeightAfterFling) {
//                    toolbarHeight = targetHeightAfterFling
//                }
//                return super.onPostFling(consumed, available)
//            }
        }
    }

    // Derive progress 0f..1f
    val collapseFraction = ((animatedToolbarUiHeight - collapsedHeight) /
            (measuredExpandedHeight - collapsedHeight))
        .coerceIn(0f, 1f)



    Scaffold(
        modifier = Modifier.nestedScroll(nestedScrollConnection),
        topBar = {
            Box(
                modifier = Modifier
                    .onGloballyPositioned { coordinates ->
                        // capture measured expanded height only once
                        val h = with(density) { coordinates.size.height.toDp() }
                        if (h > measuredExpandedHeight) {
                            measuredExpandedHeight = h
                            toolbarHeight = h // initialize toolbarHeight with real expanded
                        }
                    }
//                    .height()
            ){
                DynamicCollapsibleHeader(
                    height = animatedToolbarUiHeight,
                    collapsedHeight = collapsedHeight,
                    expandedHeight = measuredExpandedHeight,
                    collapseFraction = collapseFraction
                )
            }

        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(30) {
                Box(
                    modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth()
                        .background(Color.Green)
                )
            }
        }
    }
}


@Composable
fun DynamicCollapsibleHeader(
    height: Dp,
    collapsedHeight: Dp,
    expandedHeight: Dp,
    collapseFraction: Float
) {
    val scale = 1f - (0.4f * collapseFraction)
    val alpha = 1f - collapseFraction

    val currentHeight = lerp(expandedHeight, collapsedHeight, collapseFraction)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .background(Color(0xFFB71C1C)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // pinned area
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(collapsedHeight)
                    .background(Color.White)
            )

            repeat(10){
                // collapsible content
                Text("Dynamic Collapsible Header")
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(20.dp)
                        .background(Color.Blue)
//                    .background(Color.Blue.copy(alpha = alpha))
                )
            }


        }
    }
}


