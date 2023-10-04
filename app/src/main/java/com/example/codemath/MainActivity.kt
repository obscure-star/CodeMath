package com.example.codemath

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.codemath.databinding.ActivityMainBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.pow

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val compute get() = binding.compute
    private val output get() = binding.output
    private val numberInput get() = binding.numberInput.text.toString().toFloat()
    private val powerInput get() = binding.powerInput.text.toString().toFloat()

    private val countButtonMilli get() = binding.countButtonMilli
    private val countButtonSecond get() = binding.countButtonSecond
    private val countText get() = binding.countText
    private val mainScope = MainScope()
    private var runningCountJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
    }

    private fun setupClickListeners(){
        compute.setOnClickListener { computeButtonClicked() }
        countButtonMilli.setOnClickListener { countButtonClicked(1) }
        countButtonSecond.setOnClickListener { countButtonClicked(1000) }
    }

    private fun computeButtonClicked() {
        try {
            val result = numberInput.pow(powerInput)
            output.text = result.toString()
        } catch (e: NumberFormatException) {
            output.text = "Invalid input" // Handle invalid input gracefully
        }
    }

    private fun countButtonClicked(interval: Long) {
        runningCountJob?.cancel()
        val outputResult = output.text.toString().toFloatOrNull()?.toInt() ?: 0

        runningCountJob = mainScope.launch {
            try {
                for (count in 0..outputResult) {
                    countText.text = count.toString()
                    delay(interval) // 1000 milliseconds = 1 second
                }
            } catch (e: NumberFormatException) {
                countText.text = "Invalid result"
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        runningCountJob?.cancel()
    }
}