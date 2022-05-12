package com.mamisiaga.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mamisiaga.databinding.ActivityChildBinding

class ChildActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChildBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChildBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}