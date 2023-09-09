package com.mobileinsights.match3game.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mobileinsights.match3game.R
import kotlin.random.Random

data class Candy(val id: Int, val color: Color)

@Composable
fun CandyCrushGameBoard(
    rows: Int,
    columns: Int,
    candies: List<List<Candy>>
) {
    val lazyListState = rememberLazyListState()
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.gameboard), // Replace with your image resource
            contentDescription = null, // Content description for accessibility (can be null)
            contentScale = ContentScale.FillBounds, // Scale the image to fill the entire screen
            modifier = Modifier.fillMaxSize()
        )
        LazyColumn(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            state = lazyListState,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            itemsIndexed(candies) { rowIndex, rowCandies ->
                Row(
                    modifier = Modifier
                        .height(60.dp)
                ) {
                    rowCandies.forEach { candy ->
                        CandyCell(candy = candy)
                    }
                }
            }
        }
    }

}

@Composable
fun CandyCell(candy: Candy) {
    var waveAmplitude by remember { mutableStateOf(0f) }

    // Create a wavy animation for the candy
    val infiniteTransition = rememberInfiniteTransition()
    val waveAnimValue by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

    Box(
        modifier = Modifier
            .size(60.dp)
            .padding(4.dp)
            .background(candy.color, shape = CircleShape)
            .clip(MaterialTheme.shapes.medium)
    ) {
        // You can add additional content inside the CandyCell if needed
    }
}

@Composable
fun Circle(color: Color) {
    Box(
        modifier = Modifier
            .size(60.dp)
            .background(color)
            .clip(CircleShape)
    )
}

@Composable
fun Square(color: Color) {
    Box(
        modifier = Modifier
            .size(60.dp)
            .background(color)
            .clip(RoundedCornerShape(corner = CornerSize(0.dp)))
    )
}

@Composable
fun CandyCrushGame() {
    val numRows = 6
    val numColumns = 4

    // Generate random candies for the game board
    val candyGrid = remember {
        val candies = mutableListOf<List<Candy>>()
        for (row in 0 until numRows) {
            val rowCandies = mutableListOf<Candy>()
            for (column in 0 until numColumns) {
                val randomColor = Color(Random.nextFloat(), Random.nextFloat(), Random.nextFloat(), 1f)
                rowCandies.add(Candy(id = row * numColumns + column, color = randomColor))
            }
            candies.add(rowCandies)
        }
        candies
    }

    CandyCrushGameBoard(rows = numRows, columns = numColumns, candies = candyGrid)
}