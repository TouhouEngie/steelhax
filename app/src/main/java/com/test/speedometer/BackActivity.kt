package com.test.speedometer

import android.hardware.*
import android.os.Bundle
import android.app.Activity
import kotlin.math.cos
import kotlin.math.sin

// takes a bunch of acceleration readings, sanitizes them to account for angle of vertically oriented phone, and averages the acceleration to multiply by elapsed time for a certain velocity
// todo: account for horizontal orientation maybe
class BackActivity : Activity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager

    private var mAccel: Sensor? = null
    // private var rAccel: Sensor? = null
    private var magnet: Sensor? = null
    private var gAccel: Sensor? = null

    private lateinit var lAccel: Array<Float>

    private val dequeSensorData = ArrayDeque<Array<Float>>()
    private val dequeAngleData = ArrayDeque<Float>()
    private val poll = 5
    private var increment = 0
    private val ms = 100

    public override fun onCreate(savedInstanceState: Bundle?) {
        // x side by side, y up and down, z through...
        super.onCreate(savedInstanceState)
        // call frontend??
        setup()

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
        sensorManager.registerListener(this, mAccel, SensorManager.SENSOR_DELAY_NORMAL)
        // sensorManager.registerListener(this, rAccel, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(this, gAccel, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(this, magnet, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        return
    }


    // they may be an edge case where if there isn't any gravity and magnet data, it might break
    override fun onSensorChanged(event: SensorEvent?) {
        // var rAccel: Array<Float>
        lateinit var gAccel: FloatArray
        lateinit var magnet: FloatArray

        when (event?.sensor?.type) {
            Sensor.TYPE_LINEAR_ACCELERATION -> lAccel = arrayOf(event.values[1], event.values[2])
            // Sensor.TYPE_ACCELEROMETER -> rAccel = arrayOf(event.values[0], event.values[1], event.values[2])
            Sensor.TYPE_GRAVITY -> gAccel = floatArrayOf(event.values[0], event.values[1], event.values[2])
            Sensor.TYPE_MAGNETIC_FIELD -> magnet = floatArrayOf(event.values[0], event.values[1], event.values[2])
        }
        val mRotat = matrixAngle(gAccel, magnet)
        increment++
        val deleteFirst = (increment > poll)
        addDataToList(deleteFirst, mRotat)
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
        Thread.sleep(ms.toLong())
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
        if (size != dequeAngleData.size) {
            return 0.0
        }
        for ((i, element) in dequeSensorData.withIndex()) {
            totalAccel += getRelevantAccelVector(element, dequeAngleData[i].toDouble())
        }
        return totalAccel / size
    }

    fun loadAverageVelocity(): Double {
        return averageAccel() * (ms / 1000)
    }
}


