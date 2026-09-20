package com.test.speedometer.ui.acceleration

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val ControlsOffWhite = Color(0xFFF4F1E8)
private val ControlsDeepBlue = Color(0xFF17395F)
private val ControlsAccentBlue = Color(0xFF5AA9FF)
private val ControlsStopRed = Color(0xFFE05252)

@Composable
fun AccelerationControls(
    isRunning: Boolean,
    onStart: () -> Unit,
    onReset: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Button(
            onClick = onStart,
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isRunning) {
                    ControlsStopRed
                } else {
                    ControlsAccentBlue
                },
                contentColor = ControlsOffWhite
            )
        ) {
            Text(
                text = if (isRunning) {
                    "STOP TRACKING"
                } else {
                    "START TRACKING"
                },
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        OutlinedButton(
            onClick = onReset,
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = ControlsDeepBlue,
                contentColor = ControlsOffWhite
            )
        ) {
            Text(
                text = "RESET",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
