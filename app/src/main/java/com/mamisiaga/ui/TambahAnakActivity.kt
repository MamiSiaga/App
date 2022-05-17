package com.mamisiaga.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mamisiaga.R
import com.mamisiaga.databinding.ActivityTambahAnakBinding

class TambahAnakActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityTambahAnakBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTambahAnakBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.imagebuttonKeluar.setOnClickListener(this)
        binding.buttonTambahAnak.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.imagebutton_keluar -> {
                onBackPressed()
            }
            R.id.button_tambah_anak -> {

            }
        }
    }
}