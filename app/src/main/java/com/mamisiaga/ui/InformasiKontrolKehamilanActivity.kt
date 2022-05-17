package com.mamisiaga.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mamisiaga.R
import com.mamisiaga.databinding.ActivityDaftarBinding
import com.mamisiaga.databinding.ActivityInformasiImunisasiBinding

class InformasiKontrolKehamilanActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityInformasiImunisasiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInformasiImunisasiBinding.inflate(layoutInflater)

        setContentView(binding.root)

        //binding.layoutOffline.buttonMuatUlang.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.button_muat_ulang -> {
                //drawLayout()
            }
        }
    }
}