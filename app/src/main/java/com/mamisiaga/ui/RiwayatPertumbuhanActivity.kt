package com.mamisiaga.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.mamisiaga.R
import com.mamisiaga.adapter.PertumbuhanDataAdapter
import com.mamisiaga.dataClass.Pertumbuhan
import com.mamisiaga.databinding.ActivityRiwayatPertumbuhanBinding
import com.mamisiaga.tools.isConnected
import com.mamisiaga.viewmodel.AnakViewModel
import com.mamisiaga.viewmodelfactory.ViewModelFactory

class RiwayatPertumbuhanActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityRiwayatPertumbuhanBinding
    private lateinit var anakViewModel: AnakViewModel
    private lateinit var pertumbuhanDataAdapter: PertumbuhanDataAdapter
    private val responseCode =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == TambahEditPertumbuhanAnakActivity.EDIT_PERTUMBUHAN_ANAK_RESPONSE_CODE) {
                anakViewModel.getAnak("1").removeObservers(this)

                seeRiwayatPertumbuhanAnakResponse()

                Toast.makeText(
                    this,
                    "Berhasil mengubah data pertumbuhan anak.",
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
            val pertumbuhan = Pertumbuhan(
                "dateOfMeasurement",
                22,
                pertumbuhanData.weight,
                pertumbuhanData.height,
                pertumbuhanData.headDiameter
            )

            responseCode.launch(
                Intent(
                    this,
                    TambahEditPertumbuhanAnakActivity::class.java
                ).putExtra(TambahEditPertumbuhanAnakActivity.EXTRA_PERTUMBUHAN, pertumbuhan)
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
        /* anakViewModel.getRiwayatPertumbuhanAnak("ibu1").observe(this) { resultResponse ->
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
        binding.textviewTidakAdaData.visibility = View.GONE

        if (isLoading) {
            binding.progressbar.visibility = View.VISIBLE
        } else {
            binding.progressbar.visibility = View.GONE
        }
    }
}