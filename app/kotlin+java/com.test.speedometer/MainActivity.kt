package com.test.speedometer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.test.speedometer.ui.theme.MyApplicationTheme

// int main
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "test",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

//

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

fun DetermineSpeed(rate: int) {
    // x side by side, y up and down, z through...
    // we're gonna need to get rotational data to determine how much of the z and y axes we'll need to use
    for (int i = 0; i < rate; i++) {
        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)
    }
}

@Preview(showBackground = false)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Greeting("Test")
    }
}