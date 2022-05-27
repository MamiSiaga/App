package com.mamisiaga.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.mamisiaga.R
import com.mamisiaga.databinding.ActivityInformasiKontrolKehamilanBinding
import com.mamisiaga.tools.isConnected
import com.mamisiaga.viewmodel.KehamilanViewModel
import com.mamisiaga.viewmodelfactory.ViewModelFactory

class InformasiKontrolKehamilanActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityInformasiKontrolKehamilanBinding
    private lateinit var kehamilanViewModel: KehamilanViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInformasiKontrolKehamilanBinding.inflate(layoutInflater)

        setContentView(binding.root)

        kehamilanViewModel = ViewModelProvider(
            this,
            ViewModelFactory.KehamilanViewModelFactory(applicationContext)
        )[KehamilanViewModel::class.java]

        binding.layoutOffline.buttonMuatUlang.setOnClickListener(this)
        binding.imagebuttonKeluar.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.button_muat_ulang -> {
                drawLayout()
            }
            R.id.imagebutton_keluar -> {
                onBackPressed()
            }
        }
    }

    private fun getInformasiKontrolKehamilanResponse() {
        /*kehamilanViewModel.getKontrolKehamilanResponse("ibu1").observe(this) { resultResponse ->
            when (resultResponse) {
                is ResultResponse.Loading -> {
                    showLoadingSign(true)
                }
                is ResultResponse.Success -> {
                    showLoadingSign(false)

                    anakDataAdapter.submitList(resultResponse.data.anakData)

                    binding.textviewJudul.text = resultResponse
                    binding.textviewParagraf.text = resultResponse
                }
                is ResultResponse.Error -> {
                    showLoadingSign(false)

                    when (resultResponse.error) {
                        "No Internet Connection" -> drawLayout()
                        //else -> showMasukError(true)
                    }
                }
            }
        }
         */
    }

    private fun drawLayout() {
        // Checking Internet connection
        if (isConnected(applicationContext)) {
            binding.layoutOnline.visibility = View.VISIBLE
            binding.layoutOffline.layoutOffline.visibility = View.GONE
        } else {
            binding.layoutOnline.visibility = View.GONE
            binding.layoutOffline.layoutOffline.visibility = View.VISIBLE
        }
    }

    private fun showLoadingSign(isLoading: Boolean) {
        if (isLoading) {
            binding.layoutMemuat.layoutMemuat.visibility = View.VISIBLE
            binding.layoutOnline.visibility = View.GONE
        } else {
            binding.layoutMemuat.layoutMemuat.visibility = View.GONE
            binding.layoutOnline.visibility = View.VISIBLE
        }
    }
}