package com.example.finalproject_waterlog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.finalproject_waterlog.ui.components.FlowerPot
import com.example.finalproject_waterlog.ui.components.TopButtonRow
import com.example.finalproject_waterlog.ui.components.WaterProgressBar
import com.example.finalproject_waterlog.ui.screens.CreateUserScreen
import com.example.finalproject_waterlog.ui.screens.FlowerCollectionScreen
import com.example.finalproject_waterlog.ui.screens.MainScreen
import com.example.finalproject_waterlog.ui.screens.OptionsScreen
import com.example.finalproject_waterlog.ui.screens.DrinkLogsScreen
import com.example.finalproject_waterlog.ui.theme.MyAppTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val application = LocalContext.current.applicationContext as WaterLogApplication
            val userExists = application.userInfoRepository.userExists.collectAsState()
            val userInfo = application.userInfoRepository.userInfo.collectAsState()
            var welcomeMessage by remember { mutableStateOf("Welcome") }
            val navController = rememberNavController()

            LaunchedEffect(Unit) {
                application.userInfoRepository.loadUserInfo()
            }

            MyAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        containerColor = MaterialTheme.colorScheme.surface,
                        topBar = {
                            CenterAlignedTopAppBar(
                                title = {
                                    Text(welcomeMessage)
                                },
                                colors = TopAppBarDefaults.topAppBarColors(
                                    containerColor = MaterialTheme.colorScheme.onSurface,
                                    titleContentColor = Color.White,
                                )
                            )
                        },
                    ) { innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = userExists.let {
                                if (it.value) Destinations.Home else Destinations.UserCreate
                            },
                            modifier = Modifier
                                .padding(innerPadding)
                                .fillMaxSize()
                        ) {
                            composable<Destinations.Home> {
                                welcomeMessage = "Welcome, " + userInfo.value.name
                                MainScreen(navController, application)
                            }
                            composable<Destinations.UserCreate> {
                                welcomeMessage = "Register for WaterLog"
                                CreateUserScreen() // Will automatically navigate to Home when user info is created
                            }
                            composable<Destinations.FlowerCollection> {
                                welcomeMessage = userInfo.value.name + "'s Garden"
                                FlowerCollectionScreen(navController)
                            }
                            composable<Destinations.Options> {
                                welcomeMessage = "Options"
                                OptionsScreen(navController)
                            }
                            composable<Destinations.DrinkLogs> {
                                welcomeMessage = "Drink Logs"
                                DrinkLogsScreen(navController)
                            }
                        }
                    }
                }
            }
        }
    }
}
