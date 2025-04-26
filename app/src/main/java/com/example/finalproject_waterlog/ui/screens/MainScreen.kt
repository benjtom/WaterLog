package com.example.finalproject_waterlog.ui.screens

import WaterDrop
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.finalproject_waterlog.WaterLogApplication
import com.example.finalproject_waterlog.ui.components.FlowerPot
import com.example.finalproject_waterlog.ui.components.MainFlower
import com.example.finalproject_waterlog.ui.components.TopButtonRow
import com.example.finalproject_waterlog.ui.components.WaterProgressBar
import com.example.finalproject_waterlog.ui.utils.RandomDrawableFlower
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
    
    // State for the flower earned message
    var showFlowerEarnedMessage by remember { mutableStateOf(false) }
    var flowerEarnedMessage by remember { mutableStateOf("") }
    var flowerRarity by remember { mutableStateOf("") }
    val messageAlpha = remember { Animatable(0f) }

    LaunchedEffect(ozTempState) {
        val ozDrunkThisTime = ozTempState
        launch {
            // if the amount of oz added would complete the goal
            // run the big animation sequence
            if ((userInfo.value.ouncesDrunk+ozDrunkThisTime) / (userInfo.value.weight/2) >= 1) {
                viewModel.setOuncesDrunk(userInfo.value.ouncesDrunk+ozDrunkThisTime)
                currentlyAnimating = true
                delay(500L)
                var randomDrawableFlower = RandomDrawableFlower.getRandomRarity()
                viewModel.setDrawableFlower(randomDrawableFlower.first)
                viewModel.addFlower(randomDrawableFlower.second)
                
                // Set the flower earned message based on rarity
                flowerRarity = when {
                    randomDrawableFlower.second in listOf("yellow", "orange", "purple", "pink", "red", "sun", "tulip") -> "Common"
                    randomDrawableFlower.second in listOf("blue", "carnation", "coral", "gold", "iris", "poppy", "rose", "salmon") -> "Uncommon"
                    randomDrawableFlower.second in listOf("achimenes", "comet", "geneva", "polaris", "starry") -> "Rare"
                    randomDrawableFlower.second in listOf("rainbow", "nebula", "midnight") -> "Legendary"
                    else -> "Common"
                }
                
                flowerEarnedMessage = "You earned a ${flowerRarity} ${randomDrawableFlower.second.replaceFirstChar { it.uppercase() }} flower!"
                showFlowerEarnedMessage = true
                
                // Animate the message appearance and disappearance
                messageAlpha.snapTo(0f)
                messageAlpha.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
                )
                
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

                messageAlpha.animateTo(
                    targetValue = 0f,
                    animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
                )
                flowerYOffset.snapTo(0f)
                flowerXOffset.snapTo(0f)
                ozTempState = 0

                showFlowerEarnedMessage = false
                currentlyAnimating = false
            } else {
                viewModel.setOuncesDrunk(userInfo.value.ouncesDrunk+ozDrunkThisTime)
            }
            ozTempState = 0
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                TopButtonRow(navController)
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
                    WaterDrop(4, addWater = {
                        scope.launch {
                            if (!currentlyAnimating) {
                                ozTempState += 4
                                viewModel.addDrinkLog(4)
                            }
                        }
                    })
                    WaterDrop(16, addWater = {
                        scope.launch {
                            if (!currentlyAnimating) {
                                ozTempState += 16
                                viewModel.addDrinkLog(16)
                            }
                        }
                    })
                    WaterDrop(8, addWater = {
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
        
        // Flower earned message overlay
        if (showFlowerEarnedMessage) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(messageAlpha.value),
                contentAlignment = Alignment.BottomCenter
            ) {
                Text(
                    text = flowerEarnedMessage,
                    color = when (flowerRarity) {
                        "Common" -> Color(0xFF9E9E9E) // Gray
                        "Uncommon" -> Color(0xFF4CAF50) // Green
                        "Rare" -> Color(0xFF2196F3) // Blue
                        "Legendary" -> Color(0xFFE91E63) // Pink
                        else -> Color.White
                    },
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(bottom = 32.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}