package com.example.bai5

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var textViewTimer: TextView
    private lateinit var buttonStart: Button
    private lateinit var buttonStop: Button
    private var secondsElapsed = 0
    private lateinit var handler: Handler
    private lateinit var updateRunnable: Runnable
    private var isRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewTimer = findViewById(R.id.textViewTimer)
        buttonStart = findViewById(R.id.buttonStart)
        buttonStop = findViewById(R.id.buttonStop)

        handler = Handler(Looper.getMainLooper())

        updateRunnable = Runnable {
            if (isRunning) {
                textViewTimer.text = "$secondsElapsed giây"
                handler.postDelayed(updateRunnable, 1000)
            }
        }

        buttonStart.setOnClickListener {
            if (!isRunning) {
                isRunning = true
                startTimer()
            }
        }

        buttonStop.setOnClickListener {
            isRunning = false
            handler.removeCallbacks(updateRunnable)
        }
    }

    private fun startTimer() {
        handler.post(updateRunnable)
        Thread {
            while (isRunning) {
                Thread.sleep(1000)
                if (isRunning) secondsElapsed++
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(updateRunnable)
        isRunning = false
    }
}