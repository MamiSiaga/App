package com.mamisiaga.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mamisiaga.R
import com.mamisiaga.databinding.ActivityInformasiIbuHamilBinding

class InformasiIbuHamilActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInformasiIbuHamilBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInformasiIbuHamilBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.imagebuttonKeluar.setOnClickListener {
            onBackPressed()
        }
    }
}