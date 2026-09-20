package com.test.speedometer.ui.hud

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.test.speedometer.ui.theme.SpeedometerTheme

private val HudBlack = Color(0xFF05070A)
private val HudDeepBlue = Color(0xFF0A1B33)
private val HudPanelBlue = Color(0xFF132B4A)
private val HudOffWhite = Color(0xFFF4F1E8)
private val HudMutedWhite = Color(0xFFAAAEB7)
private val HudAccent = Color(0xFF55D9FF)

@Composable
fun HudScreen() {
    var isMirrored by rememberSaveable {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        HudBlack,
                        HudDeepBlue,
                        HudPanelBlue
                    )
                )
            )
            .statusBarsPadding()
            .padding(horizontal = 24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "HUD MODE",
                color = HudOffWhite,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 24.dp)
            )

            Text(
                text = "Reflect your speed onto the windshield",
                color = HudOffWhite,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            Column(
                modifier = Modifier
                    .graphicsLayer {
                        scaleX = if (isMirrored) -1f else 1f
                    }
                    .clip(RoundedCornerShape(28.dp))
                    .background(HudBlack)
                    .border(
                        width = 2.dp,
                        color = HudAccent,
                        shape = RoundedCornerShape(28.dp)
                    )
                    .padding(
                        horizontal = 54.dp,
                        vertical = 28.dp
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "0",
                    color = HudOffWhite,
                    fontSize = 112.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 112.sp
                )

                Text(
                    text = "MPH",
                    color = HudAccent,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 4.sp
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = if (isMirrored) {
                    "Mirrored for windshield reflection"
                } else {
                    "Normal screen display"
                },
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier
                    .width(220.dp)
                    .height(56.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(
                        if (isMirrored) {
                            HudAccent
                        } else {
                            HudPanelBlue
                        }
                    )
                    .clickable {
                        isMirrored = !isMirrored
                    },
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isMirrored) {
                        "MIRROR: ON"
                    } else {
                        "MIRROR: OFF"
                    },
                    color = if (isMirrored) {
                        HudBlack
                    } else {
                        HudOffWhite
                    },
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(36.dp))
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun HudScreenPreview() {
    SpeedometerTheme {
        HudScreen()
    }
}
