package com.example.finalproject_waterlog.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.MutableCreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.finalproject_waterlog.Destinations
import com.example.finalproject_waterlog.WaterLogApplication
import com.example.finalproject_waterlog.viewmodels.OptionsScreenViewModel
import kotlinx.coroutines.launch

@Composable
fun OptionsScreen(
    navController: NavController,
    viewModel: OptionsScreenViewModel = viewModel(
        factory = OptionsScreenViewModel.Factory,
        extras = MutableCreationExtras().apply {
            this[APPLICATION_KEY] = LocalContext.current.applicationContext as WaterLogApplication
        }
    )
) {
    val scope = rememberCoroutineScope()
    val weightState by viewModel.weight.collectAsState()
    val pattern = remember { Regex("^\\d+$") }

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

        Text(
            text = "Options",
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Weight Input Section
        Text(
            text = "Update Weight",
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            value = if (weightState == 0) "" else weightState.toString(),
            onValueChange = {
                if (it.isEmpty() || it.matches(pattern)) {
                    viewModel.setWeight(it.toIntOrNull() ?: 0)
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text("Your Weight (In Pounds)") },
            singleLine = true
        )

        Button(
            onClick = {
                scope.launch {
                    viewModel.updateWeight()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
        ) {
            Text("Save Weight")
        }

        // Delete User Data Section
        Text(
            text = "Danger Zone",
            fontSize = 18.sp,
            color = Color.Red,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Button(
            onClick = {
                scope.launch {
                    viewModel.deleteUserData()
                    navController.navigate(Destinations.UserCreate)
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red,
                contentColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Delete User Data")
        }
    }
} 