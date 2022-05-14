package com.mamisiaga.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mamisiaga.databinding.ActivityHasilGambarBinding
import com.mamisiaga.databinding.ActivityMainBinding

class HasilGambarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHasilGambarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHasilGambarBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}