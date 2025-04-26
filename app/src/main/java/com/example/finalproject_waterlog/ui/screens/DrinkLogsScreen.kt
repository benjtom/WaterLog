package com.example.finalproject_waterlog.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.MutableCreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.finalproject_waterlog.Destinations
import com.example.finalproject_waterlog.WaterLogApplication
import com.example.finalproject_waterlog.viewmodels.DrinkLogsScreenViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun DrinkLogsScreen(
    navController: NavController,
    viewModel: DrinkLogsScreenViewModel = viewModel(
        factory = DrinkLogsScreenViewModel.Factory,
        extras = MutableCreationExtras().apply {
            this[APPLICATION_KEY] = LocalContext.current.applicationContext as WaterLogApplication
        }
    )
) {
    val drinkLogGroups by viewModel.drinkLogGroups.collectAsState()
    val timeFormat = remember { SimpleDateFormat("h:mm a", Locale.getDefault()) }

    LaunchedEffect(Unit) {
        viewModel.loadDrinkLogs()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Back button
        Button(
            onClick = { navController.navigate(Destinations.Home) },
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text("Back to Home")
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(drinkLogGroups) { group ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp)
                ) {
                    // Date header
                    Text(
                        text = viewModel.formatDate(group.date),
                        fontSize = 20.sp,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    // Drink logs for this date
                    group.logs.forEach { log ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = timeFormat.format(log.date),
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Text(
                                    text = "${log.ounces} oz",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            }
        }
    }
} 