package com.mamisiaga.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mamisiaga.databinding.ActivityRencanaMelahirkanBinding

class RencanaMelahirkanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRencanaMelahirkanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRencanaMelahirkanBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}