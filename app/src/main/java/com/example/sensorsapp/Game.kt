package com.example.sensorsapp
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView

class Game : Activity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private lateinit var accelerometer: Sensor
    private lateinit var animatedView: AnimatedView
    private var ballShape = ShapeDrawable(OvalShape())
    private var touchScreen = false
    private var displayWidth = 0
    private var displayHeight = 0
    private var lastUpdate: Long = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        counter = 0
        animatedView = AnimatedView(this)
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        displayWidth = displayMetrics.widthPixels - 100
        displayHeight = displayMetrics.heightPixels - 100
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
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
            xAcceleration -= event.values[0].toInt()
            yAcceleration += event.values[1].toInt()
            if (xAcceleration > displayWidth) {
                xAcceleration = displayWidth
                gameOver()

            } else if (xAcceleration < 0) {
                xAcceleration = 0
                gameOver()
            }
            if (yAcceleration > displayHeight - 100) {
                yAcceleration = displayHeight - 100
                gameOver()
            } else if (yAcceleration < 0) {
                yAcceleration = 0
                gameOver()
            }
        }
    }

    private fun  gameOver() {
        touchScreen = true
        counter++
        resetGame()
        if (counter > 0) {
            onPause()
            Toast.makeText(applicationContext, "Game over!!", Toast.LENGTH_SHORT).show()
            val handler = Handler()
            handler.postDelayed({
                val intent = Intent(this@Game, MainActivity::class.java)
                startActivity(intent)
            }, 1000)
        }
    }

    private fun resetGame() {
        yAcceleration = displayHeight / 2
        xAcceleration = displayWidth / 2
    }

    inner class AnimatedView(context: Context?) : AppCompatImageView(context) {
        var p = Paint()
        override fun onDraw(canvas: Canvas) {
            p.color = Color.BLACK
            p.textSize = 80f
            ballShape.setBounds(xAcceleration, yAcceleration, xAcceleration + Companion.width, yAcceleration + Companion.height)
            canvas.drawColor(Color.parseColor("#d1d8e0"))
            ballShape.draw(canvas)
            invalidate()
        }

        init {
            ballShape = ShapeDrawable(OvalShape())
            ballShape.paint.color = Color.parseColor("#D53B96")
        }
    }

    companion object {
        const val width: Int = 100
        const val height: Int = 100
        var x = 0
        var y = 0
        var xAcceleration = 0
        var yAcceleration = 0
        var counter = 0
    }
}
