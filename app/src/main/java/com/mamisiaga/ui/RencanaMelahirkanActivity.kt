package com.mamisiaga.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mamisiaga.R
import com.mamisiaga.databinding.ActivityRencanaMelahirkanBinding

class RencanaMelahirkanActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityRencanaMelahirkanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRencanaMelahirkanBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.imagebuttonKeluar.setOnClickListener(this)
        binding.buttonSimpan.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.imagebutton_keluar -> {
                onBackPressed()
            }
            R.id.button_simpan -> {

            }
        }
    }


}