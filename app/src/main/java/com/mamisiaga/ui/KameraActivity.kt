package com.mamisiaga.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mamisiaga.databinding.ActivityKameraBinding

class KameraActivity : AppCompatActivity() {
    private lateinit var binding: ActivityKameraBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityKameraBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}