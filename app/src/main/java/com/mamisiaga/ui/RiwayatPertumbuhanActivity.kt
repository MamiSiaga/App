package com.mamisiaga.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mamisiaga.R
import com.mamisiaga.adapter.PertumbuhanDataAdapter
import com.mamisiaga.databinding.ActivityRiwayatPertumbuhanBinding
import com.mamisiaga.tools.ResultResponse
import com.mamisiaga.tools.isConnected
import com.mamisiaga.viewmodel.AnakViewModel
import com.mamisiaga.viewmodelfactory.ViewModelFactory

class RiwayatPertumbuhanActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityRiwayatPertumbuhanBinding
    private lateinit var anakViewModel: AnakViewModel
    private lateinit var pertumbuhanDataAdapter: PertumbuhanDataAdapter
    private val tambahAnakResponseCode =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == TambahAnakActivity.TAMBAH_ANAK_RESPONSE_CODE) {
                anakViewModel.getAnak("1").removeObservers(this)

                seeDaftarAnakResponse()

                Toast.makeText(
                    this,
                    "Berhasil menambahkan data anak baru.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRiwayatPertumbuhanBinding.inflate(layoutInflater)

        setContentView(binding.root)

        anakViewModel = ViewModelProvider(
            this,
            ViewModelFactory.AnakViewModelFactory(applicationContext)
        )[AnakViewModel::class.java]

        pertumbuhanDataAdapter = PertumbuhanDataAdapter { pertumbuhanData ->

        }

        binding.imagebuttonKeluar.setOnClickListener(this)
        binding.layoutOffline.buttonMuatUlang.setOnClickListener(this)

        lifecycleScope.launchWhenStarted {
            seeDaftarAnakResponse()
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
            R.id.tambahDataAnak -> {
                tambahAnakResponseCode.launch(Intent(this, TambahAnakActivity::class.java))
            }
        }
    }

    private fun seeDaftarAnakResponse() {
        anakViewModel.getAnak("ibu1").observe(this) { resultResponse ->
            when (resultResponse) {
                is ResultResponse.Loading -> {
                    showLoadingSign(true)
                }
                is ResultResponse.Success -> {
                    showLoadingSign(false)

                    /*pertumbuhanDataAdapter.submitList(resultResponse.data.pertumbuhanData)

                    if (resultResponse.data.pertumbuhanData.isEmpty()) {
                        binding.recyclerViewDataPertumbuhan.visibility = View.GONE
                        binding.textviewTidakAdaData.visibility = View.VISIBLE
                    } else {
                        binding.recyclerViewDataPertumbuhan.visibility = View.VISIBLE
                        binding.textviewTidakAdaData.visibility = View.GONE
                    }
                     */
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
        if (isLoading) {
            binding.layoutMemuat.layoutMemuat.visibility = View.VISIBLE
            binding.layoutOnline.visibility = View.GONE
        } else {
            binding.layoutMemuat.layoutMemuat.visibility = View.GONE
            binding.layoutOnline.visibility = View.VISIBLE
        }
    }
}