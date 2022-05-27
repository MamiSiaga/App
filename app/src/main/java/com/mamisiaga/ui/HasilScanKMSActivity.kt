package com.mamisiaga.ui

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.mamisiaga.R
import com.mamisiaga.databinding.ActivityHasilScanKmsBinding
import com.mamisiaga.viewmodel.AnakViewModel
import com.mamisiaga.viewmodelfactory.ViewModelFactory

class HasilScanKMSActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityHasilScanKmsBinding
    private lateinit var anakViewModel: AnakViewModel

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        binding = ActivityHasilScanKmsBinding.inflate(layoutInflater)

        setContentView(binding.root)

        anakViewModel = ViewModelProvider(
            this,
            ViewModelFactory.AnakViewModelFactory(applicationContext)
        )[AnakViewModel::class.java]

        binding.imagebuttonKeluar.setOnClickListener(this)
        binding.buttonSimpan.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.imagebutton_keluar -> {
                onBackPressed()
            }
            R.id.button_simpan -> {
                seeEditBeratBadanResponse()
            }
        }
    }

    private fun seeEditBeratBadanResponse() {

    }
}