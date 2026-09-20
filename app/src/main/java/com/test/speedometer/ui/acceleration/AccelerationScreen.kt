package com.test.speedometer.ui.acceleration

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val AppBlack = Color(0xFF05070A)
private val DeepBlue = Color(0xFF0A1B33)
private val PanelBlue = Color(0xFF132B4A)
private val OffWhite = Color(0xFFF4F1E8)
private val MutedWhite = Color(0xFFAAAEB7)

@Composable
fun AccelerationScreen() {
    var selectedVehicle by rememberSaveable { mutableStateOf("Car") }
    var isRunning by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        AppBlack,
                        DeepBlue,
                        PanelBlue
                    )
                )
            )
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "ACCELERATION",
            color = OffWhite,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Measure acceleration for any vehicle",
            color = MutedWhite,
            fontSize = 14.sp,
            modifier = Modifier.padding(top = 8.dp)
        )

        Spacer(modifier = Modifier.height(28.dp))

        VehicleSelector(
            selectedVehicle = selectedVehicle,
            onVehicleSelected = { selectedVehicle = it }
        )

        Spacer(modifier = Modifier.height(28.dp))

        AccelerationScoreCard(
            speed = 0,
            score = 0.0
        )

        Spacer(modifier = Modifier.height(28.dp))

        AccelerationControls(
            isRunning = isRunning,
            onStart = { isRunning = !isRunning },
            onReset = { isRunning = false }
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun AccelerationScreenPreview() {
    AccelerationScreen()
}
