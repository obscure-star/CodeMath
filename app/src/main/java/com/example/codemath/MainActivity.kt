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

    private var runningCountJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
    }

    private fun setupClickListeners(){
        compute.setOnClickListener { computeButtonClicked() }
    }

    private fun computeButtonClicked() {
        try {
            val result = numberInput.pow(powerInput)
            output.text = result.toString()
        } catch (e: NumberFormatException) {
            output.text = "Invalid input" // Handle invalid input gracefully
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        runningCountJob?.cancel()
    }
}