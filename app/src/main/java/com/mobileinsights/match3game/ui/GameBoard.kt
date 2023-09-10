package com.mobileinsights.match3game.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mobileinsights.match3game.R
import kotlin.random.Random

data class Candy(val id: Int, val fruit: FRUIT)

@Composable
fun CandyCrushGameBoard(
    rows: Int,
    columns: Int,
    candies: List<List<Candy>>
) {
    val lazyListState = rememberLazyListState()
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.scene_01), // Replace with your image resource
            contentDescription = null, // Content description for accessibility (can be null)
            contentScale = ContentScale.FillBounds, // Scale the image to fill the entire screen
            modifier = Modifier.fillMaxSize()
        )
        TransparentCard {
            LazyColumn(
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
}

@Composable
fun TransparentCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    // Define the shape and background color of the card
    val cardShape = RoundedCornerShape(16.dp)
    val cardBackgroundColor = Color.White.copy(alpha = 0.7f) // Adjust alpha for transparency

    Box(
        modifier = modifier
            .clip(cardShape)
            .background(cardBackgroundColor)
            .padding(16.dp)
    ) {
        content()
    }
}

@Composable
fun CandyCell(candy: Candy) {
    Box(
        modifier = Modifier
            .size(60.dp)
            .padding(8.dp)
            .clip(MaterialTheme.shapes.medium)
    ) {
        // You can add additional content inside the CandyCell if needed
        Image(
            painter = painterResource(id = candy.fruit.resource), // Replace with your image resource
            contentDescription = null, // Content description for accessibility (can be null)
            contentScale = ContentScale.FillBounds, // Scale the image to fill the entire screen
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun CandyCrushGame() {
    val numRows = 6
    val numColumns = 4


    val fruits = mutableListOf(
        FRUIT.PINK_GRAPE,
        FRUIT.PURPLE_GRAPE,
        FRUIT.ORANGE,
        FRUIT.PEAR
    )

    // Generate random candies for the game board
    val candyGrid = remember {
        val candies = mutableListOf<List<Candy>>()
        for (row in 0 until numRows) {
            val rowCandies = mutableListOf<Candy>()
            for (column in 0 until numColumns) {
                val randomFruitId = Random.nextInt(from = 0, until = 4)
                rowCandies.add(
                    Candy(id = row * numColumns + column, fruit = fruits[randomFruitId])
                )
            }
            candies.add(rowCandies)
        }
        candies
    }

    CandyCrushGameBoard(rows = numRows, columns = numColumns, candies = candyGrid)
}

enum class FRUIT (
    val id: Int,
    val resource: Int
) {
    PINK_GRAPE(1, R.drawable.pink_grapes),
    PURPLE_GRAPE(2, R.drawable.purple_grapes),
    ORANGE(3, R.drawable.orange),
    PEAR(4, R.drawable.pear)
}