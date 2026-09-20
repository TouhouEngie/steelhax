package com.test.speedometer.ui.acceleration

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsBike
import androidx.compose.material.icons.filled.DirectionsBoat
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.ElectricScooter
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val SelectorOffWhite = Color(0xFFF4F1E8)
private val SelectorMutedWhite = Color(0xFFAAAEB7)
private val SelectorDeepBlue = Color(0xFF132B4A)
private val SelectorActiveBlue = Color(0xFF24558A)

@Composable
fun VehicleSelector(
    selectedVehicle: String,
    onVehicleSelected: (String) -> Unit
) {
    val vehicles = listOf(
        "Car" to Icons.Filled.DirectionsCar,
        "Bike" to Icons.Filled.DirectionsBike,
        "Scooter" to Icons.Filled.ElectricScooter,
        "Boat" to Icons.Filled.DirectionsBoat
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "SELECT VEHICLE",
            color = SelectorMutedWhite,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            vehicles.forEach { (vehicle, icon) ->
                val isSelected = selectedVehicle == vehicle

                Column(
                    modifier = Modifier
                        .width(104.dp)
                        .height(94.dp)
                        .background(
                            color = if (isSelected) {
                                SelectorActiveBlue
                            } else {
                                SelectorDeepBlue
                            },
                            shape = RoundedCornerShape(20.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = if (isSelected) {
                                SelectorOffWhite
                            } else {
                                Color.Transparent
                            },
                            shape = RoundedCornerShape(20.dp)
                        )
                        .clickable {
                            onVehicleSelected(vehicle)
                        }
                        .padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = vehicle,
                        tint = SelectorOffWhite,
                        modifier = Modifier.size(30.dp)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = vehicle,
                        color = SelectorOffWhite,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}
