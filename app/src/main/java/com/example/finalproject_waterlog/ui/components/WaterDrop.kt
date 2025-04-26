import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.*
import com.example.finalproject_waterlog.ui.components.SplashParticle
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

@Composable
fun WaterDrop(
    ounces: Int,
    variant: String,
    addWater: () -> Unit,
) {
    var ouncesTemp by remember { mutableIntStateOf(ounces) }

    val size: Dp = when (ounces) {
        4 -> 72.dp
        8 -> 84.dp
        16 -> 96.dp
        else -> 60.dp
    }

    var sizeTemp by remember { mutableStateOf(size) }

    val outerSize = 96.dp
    val density = LocalDensity.current
    val originalXOffset = when (variant) {
        "left" -> -outerSize
        "right" -> outerSize
        else -> 0.dp
    }

    val padding = outerSize - size
    val fontSize = when (ounces) {
        4 -> 14.sp
        8 -> 16.sp
        16 -> 18.sp
        else -> 12.sp
    }

    var released by remember { mutableStateOf(true) }
    val xPos = remember { Animatable(0f) }
    val yPos = remember { Animatable(0f) }

    val scope = rememberCoroutineScope()

    // Particle offsets
    val particles = remember {
        List(ounces) {
            Animatable(Offset(0f, 1000f), Offset.VectorConverter)
        }
    }

    LaunchedEffect(released) {
        if (released && yPos.value <= -200f) {
            // Animate splash

            particles.forEachIndexed { i, anim ->
                sizeTemp -= (40 * (1f / ounces)).dp
                ouncesTemp--
                anim.animateTo(
                    targetValue = Offset(xPos.value, yPos.value+300f),
                    animationSpec = tween(
                        durationMillis = 200,
                        easing = FastOutLinearInEasing
                    )
                )
                anim.snapTo(Offset(0f, 1000f))
            }

            ouncesTemp = ounces
            sizeTemp = size
            addWater()

            // Reset drop position
            yPos.snapTo(0f)
            xPos.snapTo(0f)

        } else {
            coroutineScope {
                particles.forEachIndexed { i, anim ->
                    anim.snapTo(Offset(0f, 1000f))
                }
                launch { yPos.animateTo(0f) }
                launch { xPos.animateTo(0f) }
            }
        }
    }

    Box(
        modifier = Modifier
            .size(outerSize)
            .padding(padding)
            .pointerInput(Unit) {
                awaitEachGesture {
                    awaitFirstDown()
                    while (true) {
                        val event = awaitPointerEvent()
                        val change = event.changes.first()
                        if (change.pressed) {
                            released = false
                            scope.launch {
                                xPos.snapTo(xPos.value + (change.positionChange().x / density.density))
                                yPos.snapTo(yPos.value + (change.positionChange().y / density.density))

                                // Update particles while dragging
                                particles.forEach {
                                    it.snapTo(Offset(xPos.value, yPos.value))
                                }
                            }
                        } else {
                            released = true
                        }
                    }
                }
            },
        contentAlignment = Alignment.Center
    ) {
        // Splash particles
        particles.forEach {
            SplashParticle(
                xOffset = it.value.x,
                yOffset = it.value.y
            )
        }

        // Main drop
        Box(
            modifier = Modifier
                .size(sizeTemp)
                .offset(xPos.value.dp, yPos.value.dp)
                .clip(CircleShape)
                .background(Color(0xFF00BCD4)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "${ouncesTemp}oz",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.White,
                    fontSize = fontSize,
                )
            )
        }
    }
}
