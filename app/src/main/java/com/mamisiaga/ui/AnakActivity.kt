package com.mamisiaga.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mamisiaga.databinding.ActivityAnakBinding

class AnakActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAnakBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAnakBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}