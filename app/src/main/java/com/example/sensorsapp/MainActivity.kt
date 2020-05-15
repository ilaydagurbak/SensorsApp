package com.example.sensorsapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

        override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.activity_main)

                button1.setOnClickListener { this.startSensors() }
                button2.setOnClickListener { this.startGame() }
                button3.setOnClickListener { this.startGps() }

        }

        private fun startSensors() {
                val intent = Intent(this, Sensors::class.java)
                startActivity(intent)
        }

        private fun startGame() {
                val intent = Intent(this, Game::class.java)
                startActivity(intent)

        }  private fun startGps() {
                val intent = Intent(this, Gps::class.java)
                startActivity(intent)
        }


}