package com.mamisiaga.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mamisiaga.R
import com.mamisiaga.dataClass.Ibu
import com.mamisiaga.databinding.ActivityInformasiIbuHamilBinding
import com.mamisiaga.tools.ResultResponse
import com.mamisiaga.tools.isConnected
import com.mamisiaga.viewmodel.KehamilanViewModel
import com.mamisiaga.viewmodel.UserViewModel
import com.mamisiaga.viewmodelfactory.ViewModelFactory

class InformasiIbuHamilActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityInformasiIbuHamilBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var kehamilanViewModel: KehamilanViewModel
    private lateinit var ibu: Ibu

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        binding = ActivityInformasiIbuHamilBinding.inflate(layoutInflater)

        setContentView(binding.root)

        ibu = intent.getParcelableExtra<Ibu>(EXTRA_IBU) as Ibu

        userViewModel = ViewModelProvider(
            this,
            ViewModelFactory.UserViewModelFactory()
        )[UserViewModel::class.java]

        kehamilanViewModel = ViewModelProvider(
            this,
            ViewModelFactory.KehamilanViewModelFactory(ibu.token!!)
        )[KehamilanViewModel::class.java]

        seeKehamilanResponse()

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
        userViewModel.getUser(ibu.token!!).observe(this) { resultResponse ->
            when (resultResponse) {
                is ResultResponse.Loading -> {
                    showLoadingSign(true)
                }
                is ResultResponse.Success -> {
                    showLoadingSign(false)

                    if (resultResponse.data.userData.profileData.pregnancies.isEmpty()) {
                        binding.tubuh1.visibility = View.VISIBLE
                        binding.tubuh2.visibility = View.GONE
                        binding.tubuh3.visibility = View.GONE
                    } else {
                        binding.tubuh1.visibility = View.GONE
                        binding.tubuh2.visibility = View.GONE
                        binding.tubuh3.visibility = View.VISIBLE
                    }
                }
                is ResultResponse.Error -> {
                    showLoadingSign(false)

                    when (resultResponse.error) {
                        "No Internet Connection" -> drawLayout()
                        else -> {
                            binding.recyclerviewKontrolKehamilan.visibility = View.GONE

                            Toast.makeText(
                                this@InformasiIbuHamilActivity,
                                "Gagal menampilkan data. Silahkan dicoba ulang.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }

            with(binding.recyclerviewKontrolKehamilan) {
                layoutManager = LinearLayoutManager(context)

                setHasFixedSize(true)

                //adapter =
            }
        }
    }

    private fun getKontrolKehamilanResponse() {
        /*kehamilanViewModel.getKontrolKehamilanResponse("ibu1").observe(this) { resultResponse ->
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

    companion object {
        const val EXTRA_IBU = "extraIbu"
    }
}