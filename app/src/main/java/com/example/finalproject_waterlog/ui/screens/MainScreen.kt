package com.example.finalproject_waterlog.ui.screens

import WaterDrop
import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.finalproject_waterlog.WaterLogApplication
import com.example.finalproject_waterlog.ui.components.FlowerPot
import com.example.finalproject_waterlog.ui.components.MainFlower
import com.example.finalproject_waterlog.ui.components.TopButtonRow
import com.example.finalproject_waterlog.ui.components.WaterProgressBar
import com.example.finalproject_waterlog.viewmodels.MainScreenViewModel
import kotlinx.coroutines.launch
import com.example.finalproject_waterlog.R
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

@Composable
fun MainScreen(
    navController: NavController,
    application: WaterLogApplication,
    viewModel: MainScreenViewModel = viewModel(factory = MainScreenViewModel.Factory)
) {

    fun randomDrawableFlower(): Pair<Int, String> {
        val random = (1..6).random()
        var flowerTuple = Pair<Int, String>(0, "")
        flowerTuple = when (random) {
            1 -> Pair<Int, String>(R.drawable.yellow_flower, "yellow")
            2 -> Pair<Int, String>(R.drawable.orange_flower, "orange")
            3 -> Pair<Int, String>(R.drawable.blue_flower, "blue")
            4 -> Pair<Int, String>(R.drawable.pink_flower, "pink")
            5 -> Pair<Int, String>(R.drawable.purple_star, "purple_star")
            6 -> Pair<Int, String>(R.drawable.yellow_tulip_small, "yellow_tulip")
            else -> Pair<Int, String>(R.drawable.rainbow_tulip, "rainbow_tulip")
        }
        return flowerTuple
    }

    val scope = rememberCoroutineScope()

    val userInfo = application.userInfoRepository.userInfo.collectAsState()

    val drawableFlower = viewModel.drawableFlower.collectAsState()

    val percentComplete = viewModel.percentComplete.collectAsState()

    var currentlyAnimating by remember { mutableStateOf(false) }

    var flowerToBeDrawn = R.drawable.stage1
    if (!currentlyAnimating) {
        flowerToBeDrawn = when(percentComplete.value) {
            in 0f.. .16f -> R.drawable.stage1
            in .16f.. .33f -> R.drawable.stage2
            in .33f.. .5f -> R.drawable.stage3
            in .5f.. .66f -> R.drawable.stage4
            in .66f.. .83f -> R.drawable.stage5
            in .83f.. 1f -> R.drawable.stage6
            else -> R.drawable.stage1
        }

        viewModel.setDrawableFlower(flowerToBeDrawn)
    }

    var ozTempState by remember { mutableIntStateOf(0) }

    val flowerXOffset = remember { Animatable(0f) }
    val flowerYOffset = remember { Animatable(0f) }

    LaunchedEffect(ozTempState) {
        val ozDrunkThisTime = ozTempState
        launch {
            // if the amount of oz added would complete the goal
            if ((userInfo.value.ouncesDrunk+ozDrunkThisTime) / (userInfo.value.weight/2) >= 1) {
                viewModel.setOuncesDrunk(userInfo.value.ouncesDrunk+ozDrunkThisTime)
                currentlyAnimating = true
                delay(500L)
                var randomDrawableFlower = randomDrawableFlower()
                viewModel.setDrawableFlower(randomDrawableFlower.first)
                viewModel.addFlower(randomDrawableFlower.second)
                delay(500L)
                flowerYOffset.animateTo(-80f)
                delay(2000L)
                coroutineScope {
                    launch { flowerYOffset.animateTo(-400f) }
                    launch { flowerXOffset.animateTo(-160f) }
                }
                delay(200L)
                flowerXOffset.snapTo(-300f)
                viewModel.setOuncesDrunk(0)
                viewModel.setDrawableFlower(R.drawable.stage1)
                delay(500L)
                flowerYOffset.snapTo(0f)
                flowerXOffset.snapTo(0f)
                ozTempState = 0
                currentlyAnimating = false
            } else {
                viewModel.setOuncesDrunk(userInfo.value.ouncesDrunk+ozDrunkThisTime)
            }
            ozTempState = 0
        }
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            TopButtonRow(navController, scope, application)
            Spacer(modifier = Modifier.height(32.dp))
            WaterProgressBar(userInfo.value.ouncesDrunk, userInfo.value.weight/2)
        }

        Spacer(modifier = Modifier.fillMaxHeight(.2f)) // Push bottom section down

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MainFlower(drawableFlower.value, xOffset = flowerXOffset.value, yOffset = flowerYOffset.value)
            FlowerPot()

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                WaterDrop(4, "left", addWater = {
                    scope.launch {
                        if (!currentlyAnimating) {
                            ozTempState += 4
                            viewModel.addDrinkLog(4)
                        }
                    }
                })
                WaterDrop(16, "middle", addWater = {
                    scope.launch {
                        if (!currentlyAnimating) {
                            ozTempState += 16
                            viewModel.addDrinkLog(16)
                        }
                    }
                })
                WaterDrop(8, "right", addWater = {
                    scope.launch {
                        if (!currentlyAnimating) {
                            ozTempState += 8
                            viewModel.addDrinkLog(8)
                        }
                    }
                })
            }
        }
    }
}