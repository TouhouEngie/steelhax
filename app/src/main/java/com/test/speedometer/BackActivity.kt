package com.test.speedometer

import android.hardware.SensorManager
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

class BackActivity {

}

@Composable
fun DetermineSpeed(rate: Int) {

    // x side by side, y up and down, z through...
    // we're gonna need to get rotational data to determine how much of the z and y axes we'll need to use

    // take sensor data every 100 milliseconds
    for (i in 1..rate) {
        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val accelerationSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)
        val rotationSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)


        Thread.sleep(100);
    }

}

