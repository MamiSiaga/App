package com.mamisiaga.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mamisiaga.databinding.ActivityTambahAnakBinding

class TambahAnakActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTambahAnakBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTambahAnakBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}