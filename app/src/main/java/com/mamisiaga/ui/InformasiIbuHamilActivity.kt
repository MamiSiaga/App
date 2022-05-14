package com.mamisiaga.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mamisiaga.R
import com.mamisiaga.databinding.ActivityInformasiIbuHamilBinding

class InformasiIbuHamilActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityInformasiIbuHamilBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInformasiIbuHamilBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.imagebuttonKeluar.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.imagebutton_keluar -> {
                onBackPressed()
            }
        }
    }
}