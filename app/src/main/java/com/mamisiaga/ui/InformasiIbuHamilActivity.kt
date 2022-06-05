package com.mamisiaga.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import com.mamisiaga.R
import com.mamisiaga.databinding.ActivityInformasiIbuHamilBinding
import com.mamisiaga.tools.isConnected
import com.mamisiaga.viewmodel.IbuViewModel
import com.mamisiaga.viewmodelfactory.ViewModelFactory

class InformasiIbuHamilActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityInformasiIbuHamilBinding
    private lateinit var ibuViewModel: IbuViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        binding = ActivityInformasiIbuHamilBinding.inflate(layoutInflater)

        setContentView(binding.root)

        ibuViewModel = ViewModelProvider(
            this,
            ViewModelFactory.IbuViewModelFactory("9|4GgQ7ufHhmiMRZ289qHshRM79vFaGquYo3JHJ54z")
        )[IbuViewModel::class.java]

        binding.layoutOffline.buttonMuatUlang.setOnClickListener(this)
        binding.imagebuttonKeluar.setOnClickListener(this)
        binding.rencanaPersalinan.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.button_muat_ulang -> {
                drawLayout()
            }
            R.id.imagebutton_keluar -> {
                onBackPressed()
            }
            R.id.rencana_persalinan -> {
                startActivity(Intent(this, RencanaPersalinanActivity::class.java))
            }
        }
    }

    private fun seeKehamilanResponse() {

    }

    private fun getKontrolKehamilanResponse() {
        /*ibuViewModel.getKontrolKehamilanResponse("ibu1").observe(this) { resultResponse ->
            when (resultResponse) {
                is ResultResponse.Loading -> {
                    showLoadingSign(true)
                }
                is ResultResponse.Success -> {
                    showLoadingSign(false)

                    anakDataAdapter.submitList(resultResponse.data.anakData)

                    if (resultResponse.data.anakData.isEmpty()) {
                        binding.recyclerViewDataAnak.visibility = View.GONE
                        binding.textviewTidakAdaData.visibility = View.VISIBLE
                    } else {
                        binding.recyclerViewDataAnak.visibility = View.VISIBLE
                        binding.textviewTidakAdaData.visibility = View.GONE
                    }
                }
                is ResultResponse.Error -> {
                    showLoadingSign(false)

                    binding.recyclerViewDataAnak.visibility = View.GONE
                    binding.textviewTidakAdaData.visibility = View.GONE

                    when (resultResponse.error) {
                        "No Internet Connection" -> drawLayout()
                        else -> Toast.makeText(
                                this@InformasiAnakActivity,
                                "Gagal menampilkan data. Silahkan dicoba ulang.",
                                Toast.LENGTH_SHORT
                            ).show()
                    }
                }
            }

            with(binding.recyclerViewDataAnak) {
                layoutManager = LinearLayoutManager(context)

                setHasFixedSize(true)

                adapter = anakDataAdapter
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
            binding.layoutMemuat.visibility = View.VISIBLE
        } else {
            binding.layoutMemuat.visibility = View.GONE
        }
    }
}