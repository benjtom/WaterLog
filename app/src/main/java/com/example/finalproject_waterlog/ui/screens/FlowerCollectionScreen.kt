package com.example.finalproject_waterlog.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.finalproject_waterlog.Destinations
import com.example.finalproject_waterlog.R
import com.example.finalproject_waterlog.viewmodels.FlowerCollectionScreenViewModel

@Composable
fun FlowerCollectionScreen(
    navController: NavController,
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
        Button(
            onClick = { navController.navigate(Destinations.Home) },
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text("Back to Home")
        }

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
                            "yellow" -> R.drawable.yellow
                            "pink" -> R.drawable.pink
                            "orange" -> R.drawable.orange
                            "purple" -> R.drawable.purple
                            "tulip" -> R.drawable.tulip
                            "sun" -> R.drawable.sun
                            "red" -> R.drawable.red
                            "blue" -> R.drawable.blue
                            "carnation" -> R.drawable.carnation
                            "coral" -> R.drawable.coral
                            "gold" -> R.drawable.gold
                            "iris" -> R.drawable.iris
                            "poppy" -> R.drawable.poppy
                            "rose" -> R.drawable.rose
                            "salmon" -> R.drawable.salmon
                            "achimenes" -> R.drawable.achimenes
                            "comet" -> R.drawable.comet
                            "geneva" -> R.drawable.geneva
                            "polaris" -> R.drawable.polaris
                            "starry" -> R.drawable.starry
                            "rainbow" -> R.drawable.rainbow
                            "nebula" -> R.drawable.nebula
                            "midnight" -> R.drawable.midnight
                            else -> R.drawable.yellow
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

    }
}