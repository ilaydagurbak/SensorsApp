package com.example.sensorsapp


import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Sensors : AppCompatActivity() {
    private lateinit var sensorManager: SensorManager
    private lateinit var sensorView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensors)

        sensorView = findViewById<View>(R.id.sensor_list) as TextView
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL)
        sensorView.text = sensorList.joinToString("\n") { it.name }
    }
}
