package com.example.learnkotlin

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ThreeImageCarousel(imageUrls: List<String>) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val imageWidth = screenWidth / 3f // exactly 3 per screen

    val pagerState = rememberPagerState(
        initialPage = 1,
        pageCount = { imageUrls.size }
    )

    // Auto-scroll every 3 seconds
    LaunchedEffect(Unit) {
        while (true) {
            delay(3000)
            pagerState.animateScrollToPage(
                (pagerState.currentPage + 1) % imageUrls.size
            )
        }
    }

    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        pageSpacing = 8.dp,
        beyondViewportPageCount = 2,
        verticalAlignment = Alignment.CenterVertically,
        pageSize = PageSize.Fixed(imageWidth) // 🔹 Ensures 3 images on screen
    ) { page ->

        // Distance from the current page
        val pageOffset = (
                pagerState.currentPage - page + pagerState.currentPageOffsetFraction
                ).absoluteValue

        // Middle is bigger, sides smaller
        val scale = 0.8f + (1f - pageOffset.coerceIn(0f, 1f)) * 0.2f

        Box(
            modifier = Modifier
                .width(imageWidth)
                .scale(scale) // 🔹 Apply scale based on position
                .aspectRatio(1f),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = rememberAsyncImagePainter(imageUrls[page]),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}



@Preview
@Composable
fun Preview(){
    ThreeImageCarousel(
        listOf(
            "https://picsum.photos/300/300?1",
            "https://picsum.photos/300/300?2",
            "https://picsum.photos/300/300?3",
            "https://picsum.photos/300/300?4",
            "https://picsum.photos/300/300?5"
        )
    )
}


