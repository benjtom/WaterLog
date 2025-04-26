package com.example.finalproject_waterlog.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalproject_waterlog.R
import com.example.finalproject_waterlog.viewmodels.FlowerCollectionScreenViewModel

@Composable
fun FlowerCollectionScreen(
    viewModel: FlowerCollectionScreenViewModel = viewModel(factory = FlowerCollectionScreenViewModel.Factory)
) {
    val flowers by viewModel.flowers.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadFlowers()
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ){

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Break the list into rows of 3 images
            items(flowers.chunked(3)) { flowerObjects ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    flowerObjects.forEach { flower ->
                        val drawableId = when (flower.type) {
                            "yellow" -> R.drawable.yellow_flower
                            "pink" -> R.drawable.pink_flower
                            "orange" -> R.drawable.orange_flower
                            "blue" -> R.drawable.blue_flower
                            "yellow_tulip" -> R.drawable.yellow_tulip_big
                            "purple_star" -> R.drawable.purple_star
                            "rainbow_tulip" -> R.drawable.rainbow_tulip
                            else -> R.drawable.yellow_flower
                        }
                        Image(
                            painter = painterResource(id = drawableId),
                            contentDescription = null,
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f) // Keep square shape
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }

                    // Fill any empty spots in the last row
                    repeat(3 - flowerObjects.size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }

//        LazyColumn(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            modifier = Modifier
//                .fillMaxWidth()
//                .fillMaxHeight(.9f)
//        ){
//            items(flowers) {
//                val drawableId = when (it.type) {
//                    "yellow" -> R.drawable.yellow_flower
//                    "pink" -> R.drawable.pink_flower
//                    "orange" -> R.drawable.orange_flower
//                    "blue" -> R.drawable.blue_flower
//                    "yellow_tulip" -> R.drawable.yellow_tulip_big
//                    "purple_star" -> R.drawable.purple_star
//                    "rainbow_tulip" -> R.drawable.rainbow_tulip
//                    else -> R.drawable.yellow_flower
//                }
//                Image(
//                    painter = painterResource(id = drawableId),
//                    contentDescription = "flower",
//                    modifier = Modifier
//                        .clip(CircleShape),
//                    contentScale = ContentScale.Crop
//                )
//                Spacer(modifier = Modifier.height(8.dp))
//            }
//        }
    }
}