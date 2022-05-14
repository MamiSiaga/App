package com.mamisiaga.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mamisiaga.R
import com.mamisiaga.databinding.ActivityAnakBinding

class AnakActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityAnakBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAnakBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imagebuttonKeluar.setOnClickListener(this)
        binding.layoutInfoImunisasi.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.imagebutton_keluar -> {
                onBackPressed()
            }
            R.id.layoutInfoImunisasi -> {
                startActivity(Intent(this, InformasiImunisasiActivity::class.java))
            }
        }
    }
}