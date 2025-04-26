package com.example.finalproject_waterlog.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.finalproject_waterlog.R

@Composable
fun FlowerPot() {
    Image(
        painter = painterResource(id = R.drawable.pot),
        contentDescription = null,
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .size(120.dp)
    )
}