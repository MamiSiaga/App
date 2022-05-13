package com.mamisiaga.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mamisiaga.R
import com.mamisiaga.databinding.ActivityDaftarLanjutanBinding

class DaftarLanjutanActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityDaftarLanjutanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDaftarLanjutanBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.edittextTglLahir.setOnClickListener(this)
        binding.buttonDaftar.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.edittext_tgl_lahir -> {

            }
            R.id.button_daftar -> {
                startActivity(Intent(this, HomeActivity::class.java))

                finish()
            }
        }
    }
}