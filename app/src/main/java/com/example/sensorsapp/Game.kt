package com.example.sensorsapp
import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.Menu


class Game : Activity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private lateinit var accelerometer: Sensor
    private var lastUpdate: Long = 0
    private lateinit var animatedView: AnimatedView
    var ballShape = ShapeDrawable(OvalShape())

    private var touchScreen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager
            .getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        lastUpdate = System.currentTimeMillis()
        animatedView = AnimatedView(this)
        setContentView(animatedView)
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(
            this, accelerometer,
            SensorManager.SENSOR_DELAY_GAME
        )
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        setContentView(R.layout.activity_game)
        return true
    }

    override fun onAccuracyChanged(arg0: Sensor, arg1: Int) {
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            x -= event.values[0].toInt()
            y += event.values[1].toInt()
        }
    }

    inner class AnimatedView(context: Context?) :
        androidx.appcompat.widget.AppCompatImageView(context) {
        override fun onDraw(canvas: Canvas) {
            ballShape.setBounds(
                Game.x,
                Game.y,
                Game.x + Companion.width,
                Game.y + Companion.height
            )
            ballShape.draw(canvas)
            ballShape.paint.color = Color.parseColor("#D53B96")
            invalidate()
        }
    }

    companion object {
        const val width: Int = 120
        const val height: Int = 120
        var x = 0
        var y = 0
    }
}