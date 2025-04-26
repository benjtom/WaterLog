package com.example.finalproject_waterlog.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import androidx.navigation.NavController
import com.example.finalproject_waterlog.Destinations
import com.example.finalproject_waterlog.WaterLogApplication

@Composable
fun TopButtonRow(
    navController: NavController,
    scope: CoroutineScope,
    application: WaterLogApplication
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Left Button - Garden
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary,
                contentColor = MaterialTheme.colorScheme.onTertiary
            ),
            onClick = {
                navController.navigate(Destinations.FlowerCollection)
            },
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "My Garden🌼",
                style = MaterialTheme.typography.bodySmall
            )
        }

        // Middle Button - Drink Logs
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            onClick = {
                navController.navigate(Destinations.DrinkLogs)
            },
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Drink Logs💧",
                style = MaterialTheme.typography.bodySmall
            )
        }

        // Right Button - Options
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary
            ),
            onClick = {
                navController.navigate(Destinations.Options)
            },
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Options⚙️",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
