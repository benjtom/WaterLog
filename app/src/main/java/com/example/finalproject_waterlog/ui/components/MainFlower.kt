package com.example.finalproject_waterlog.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun MainFlower(drawableId: Int, xOffset: Float, yOffset: Float) {
    Image(
        painter = painterResource(id = drawableId),
        contentDescription = null,
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .size(120.dp)
            .offset(xOffset.dp, (yOffset+20).dp)
    )
}