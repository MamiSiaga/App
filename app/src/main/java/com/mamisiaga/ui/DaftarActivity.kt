package com.mamisiaga.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mamisiaga.databinding.ActivityDaftarBinding

class DaftarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDaftarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDaftarBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.buttonLanjut.setOnClickListener {
            startActivity(Intent(this, DaftarLanjutanActivity::class.java))
        }
    }
}