package com.mamisiaga.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.mamisiaga.R
import com.mamisiaga.databinding.ActivityInformasiIbuHamilBinding
import com.mamisiaga.viewmodel.IbuViewModel
import com.mamisiaga.viewmodelfactory.ViewModelFactory

class InformasiIbuHamilActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityInformasiIbuHamilBinding
    private lateinit var ibuViewModel: IbuViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInformasiIbuHamilBinding.inflate(layoutInflater)

        setContentView(binding.root)

        ibuViewModel = ViewModelProvider(
            this,
            ViewModelFactory.IbuViewModelFactory(applicationContext)
        )[IbuViewModel::class.java]

        binding.imagebuttonKeluar.setOnClickListener(this)
        binding.rencanaMelahirkan.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.imagebutton_keluar -> {
                onBackPressed()
            }
            R.id.rencana_melahirkan -> {
                startActivity(Intent(this, RencanaMelahirkanActivity::class.java))
            }
        }
    }

    private fun getIbuHamilResponse() {
        //ibuViewModel.
    }
}