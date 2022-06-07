package com.mamisiaga.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mamisiaga.R
import com.mamisiaga.adapter.PertumbuhanDataAdapter
import com.mamisiaga.dataClass.Anak
import com.mamisiaga.dataClass.Ibu
import com.mamisiaga.dataClass.Pertumbuhan
import com.mamisiaga.databinding.ActivityRiwayatPertumbuhanBinding
import com.mamisiaga.tools.ResultResponse
import com.mamisiaga.tools.isConnected
import com.mamisiaga.viewmodel.PertumbuhanViewModel
import com.mamisiaga.viewmodelfactory.ViewModelFactory

class RiwayatPertumbuhanActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityRiwayatPertumbuhanBinding
    private lateinit var pertumbuhanViewModel: PertumbuhanViewModel
    private lateinit var ibu: Ibu
    private lateinit var anak: Anak
    private lateinit var pertumbuhanDataAdapter: PertumbuhanDataAdapter
    private val responseCode =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == TambahEditPertumbuhanAnakActivity.EDIT_PERTUMBUHAN_ANAK_RESPONSE_CODE) {
                pertumbuhanViewModel.getPertumbuhan(anak.id!!).removeObservers(this)

                seeRiwayatPertumbuhanAnakResponse()

                Toast.makeText(
                    this,
                    "Pengubahan data pertumbuhan anak pada berhasil.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        binding = ActivityRiwayatPertumbuhanBinding.inflate(layoutInflater)

        setContentView(binding.root)

        ibu = intent.getParcelableExtra<Ibu>(AnakActivity.EXTRA_IBU) as Ibu
        anak = intent.getParcelableExtra<Anak>(AnakActivity.EXTRA_ANAK) as Anak

        pertumbuhanViewModel = ViewModelProvider(
            this,
            ViewModelFactory.PertumbuhanViewModelFactory(ibu.token!!)
        )[PertumbuhanViewModel::class.java]

        pertumbuhanDataAdapter = PertumbuhanDataAdapter { pertumbuhanData ->
            val pertumbuhan = Pertumbuhan(
                pertumbuhanData.id,
                pertumbuhanData.childrenId,
                null,
                pertumbuhanData.age,
                pertumbuhanData.weight,
                pertumbuhanData.height,
                pertumbuhanData.headDiameter
            )

            responseCode.launch(
                Intent(
                    this,
                    TambahEditPertumbuhanAnakActivity::class.java
                ).putExtra(TambahEditPertumbuhanAnakActivity.EXTRA_IBU, ibu)
                    .putExtra(TambahEditPertumbuhanAnakActivity.EXTRA_PERTUMBUHAN, pertumbuhan)
            )
        }

        binding.imagebuttonKeluar.setOnClickListener(this)
        binding.layoutOffline.buttonMuatUlang.setOnClickListener(this)

        lifecycleScope.launchWhenStarted {
            seeRiwayatPertumbuhanAnakResponse()
        }
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

    private fun seeRiwayatPertumbuhanAnakResponse() {
        pertumbuhanViewModel.getPertumbuhan(1).observe(this) { resultResponse ->
            when (resultResponse) {
                is ResultResponse.Loading -> {
                    showLoadingSign(true)
                }
                is ResultResponse.Success -> {
                    showLoadingSign(false)

                    binding.apply {
                        textViewNamaAnak.text = anak.name
                        textViewUsiaAnak.text =
                            "Usia anak saat ini: ${resultResponse.data.pertumbuhanData.size.toString()} bulan"
                    }

                    pertumbuhanDataAdapter.submitList(resultResponse.data.pertumbuhanData)

                    if (resultResponse.data.pertumbuhanData.isEmpty()) {
                        binding.recyclerViewDataPertumbuhan.visibility = View.GONE
                        binding.textviewTidakAdaData.visibility = View.VISIBLE
                    } else {
                        binding.recyclerViewDataPertumbuhan.visibility = View.VISIBLE
                        binding.textviewTidakAdaData.visibility = View.GONE
                    }
                }
                is ResultResponse.Error -> {
                    showLoadingSign(false)

                    when (resultResponse.error) {
                        "No Internet Connection" -> drawLayout()
                        //else -> showMasukError(true)
                    }
                }
            }

            with(binding.recyclerViewDataPertumbuhan) {
                layoutManager = LinearLayoutManager(context)

                setHasFixedSize(true)

                adapter = pertumbuhanDataAdapter
            }
        }
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
        binding.textviewTidakAdaData.visibility = View.GONE

        if (isLoading) {
            binding.progressbar.visibility = View.VISIBLE
        } else {
            binding.progressbar.visibility = View.GONE
        }
    }

    companion object {
        const val EXTRA_IBU = "extraIbu"
        const val EXTRA_ANAK = "extraAnak"
    }
}