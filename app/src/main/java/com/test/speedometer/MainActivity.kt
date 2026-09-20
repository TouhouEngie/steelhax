package com.test.speedometer

import android.hardware.*
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.test.speedometer.ui.theme.SpeedometerTheme
import java.util.*
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

// takes a bunch of acceleration readings, sanitizes them to account for angle of vertically oriented phone, and averages the acceleration to multiply by elapsed time for a certain velocity
class MainActivity : ComponentActivity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager

    private var mAccel: Sensor? = null
    // private var rAccel: Sensor? = null
    private var magnet: Sensor? = null
    private var gAccel: Sensor? = null

    private var lAccel: Array<Float> = arrayOf(0f, 0f)

    private val dequeSensorData = ArrayDeque<Array<Float>>()
    private val dequeAngleData = ArrayDeque<Float>()

    private var velocity by mutableStateOf(0.0)
    private var highScore by mutableStateOf(0.0)
    
    private val poll = 5
    private var increment = 0
    private val ms = 100

    private var lastUpdate: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        // x side by side, y up and down, z through...
        super.onCreate(savedInstanceState)
        setup()
        setContent {
            SpeedometerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HudScreen(
                        name = String.format(Locale.US, "%.1f", abs(velocity)),
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }

    }

    private fun setup() {
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        mAccel = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)
        // rAccel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) // includes gravity
        gAccel = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)
        magnet = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, mAccel, SensorManager.SENSOR_DELAY_UI)
        sensorManager.registerListener(this, gAccel, SensorManager.SENSOR_DELAY_UI)
        sensorManager.registerListener(this, magnet, SensorManager.SENSOR_DELAY_UI)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        return
    }

    private var currentGravity = FloatArray(3)
    private var currentMagnet = FloatArray(3)

    // they may be an edge case where if there isn't any gravity and magnet data, it might break
    override fun onSensorChanged(event: SensorEvent?) {
        when (event?.sensor?.type) {
            Sensor.TYPE_LINEAR_ACCELERATION -> lAccel = arrayOf(event.values[1], event.values[2])
            Sensor.TYPE_GRAVITY -> currentGravity = event.values.clone()
            Sensor.TYPE_MAGNETIC_FIELD -> currentMagnet = event.values.clone()
        }
        /*
        val mRotat = matrixAngle(gAccel, magnet)
        increment++
        val deleteFirst = (increment > poll)
        addDataToList(deleteFirst, mRotat)
        setAverageVelocity()

        */
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastUpdate >= ms) {
            lastUpdate = currentTime
            
            val mRotat = matrixAngle(currentGravity, currentMagnet)
            increment++
            val deleteFirst = (increment > poll)
            addDataToList(deleteFirst, mRotat)
            setAverageVelocity()
        }
    }

    private fun matrixAngle(gAccel: FloatArray, magnet: FloatArray): FloatArray {
        val rotationMatrix = FloatArray(9)
        val mRotat = FloatArray(3)
        SensorManager.getRotationMatrix(rotationMatrix, null, gAccel, magnet)
        SensorManager.getOrientation(rotationMatrix, mRotat)
        return mRotat
    }

    private fun addDataToList(parallel: Boolean, rotate: FloatArray) {
        if (parallel) {
            dequeSensorData.removeFirst()
            dequeAngleData.removeFirst()
        }
        dequeSensorData.addLast(lAccel)
        dequeAngleData.addLast(rotate[1]) // we only need the pitch
    }

    // compare the pitch and use it to calculate how much y and z vectors
    private fun getRelevantAccelVector(accel: Array<Float>, rotate: Double): Double {
        val relevantY = accel[0] * cos(rotate)
        val relevantZ = accel[1] * sin(rotate)
        return relevantY + relevantZ
    }

    private fun averageAccel(): Double {
        var totalAccel = 0.0
        val size = dequeSensorData.size
        if (size == 0 || size != dequeAngleData.size) {
            return 0.0
        }
        for ((i, elementA) in dequeSensorData.withIndex()) {
            for ((j, elementB) in dequeAngleData.withIndex()) {
                totalAccel += getRelevantAccelVector(elementA, elementB.toDouble())
            }
        }
        return totalAccel / size
    }

    private fun setAverageVelocity() {
        val velo = averageAccel() * (ms / 1000.0)
        if (velo > highScore) {
            highScore = velo
        }
        velocity = velo
    }
}

private val HudBlack = Color(0xFF05070A)
private val HudDeepBlue = Color(0xFF0A1B33)
private val HudPanelBlue = Color(0xFF132B4A)
private val HudOffWhite = Color(0xFFF4F1E8)
private val HudAccent = Color(0xFF55D9FF)

@Composable
fun HudScreen(name: String, modifier: Modifier = Modifier) {
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
                text = "SPEEDOMETER",
                color = HudOffWhite,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 24.dp)
            )

            Text(
                text = "No wireless necessary",
                color = HudOffWhite,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            Column(
            ) {
                Text(
                    text = name,
                    color = HudOffWhite,
                    fontSize = 112.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 112.sp
                )

                Text(
                    text = "M/S",
                    color = HudAccent,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 4.sp
                )
            }

            Spacer(modifier = Modifier.height(28.dp))
            Spacer(modifier = Modifier.weight(1f))
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
        HudScreen("0.0")
    }
}
