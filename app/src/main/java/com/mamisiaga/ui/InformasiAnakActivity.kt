package com.mamisiaga.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mamisiaga.R
import com.mamisiaga.databinding.ActivityInformasiAnakBinding

class InformasiAnakActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityInformasiAnakBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInformasiAnakBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tambahDataAnak.setOnClickListener(this)
        binding.imagebuttonKeluar.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.imagebutton_keluar -> {
                onBackPressed()
            }
            R.id.tambahDataAnak -> {
                startActivity(Intent(this, TambahAnakActivity::class.java))
            }
        }
    }
}