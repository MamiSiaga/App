package com.mamisiaga.ui

import android.app.Dialog
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mamisiaga.R
import com.mamisiaga.`class`.Anak
import com.mamisiaga.`class`.Opsi
import com.mamisiaga.adapter.AnakDataAdapter
import com.mamisiaga.adapter.OpsiAdapter
import com.mamisiaga.api.AnakData
import com.mamisiaga.databinding.ActivityInformasiAnakBinding
import com.mamisiaga.tools.ResultResponse
import com.mamisiaga.tools.isConnected
import com.mamisiaga.viewmodel.AnakViewModel
import com.mamisiaga.viewmodelfactory.ViewModelFactory

class InformasiAnakActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityInformasiAnakBinding
    private lateinit var anakViewModel: AnakViewModel
    private lateinit var anakDataAdapter: AnakDataAdapter
    private var anakDataList = mutableListOf<AnakData>()
    private val responseCode =
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

        binding = ActivityInformasiAnakBinding.inflate(layoutInflater)

        setContentView(binding.root)

        anakViewModel = ViewModelProvider(
            this,
            ViewModelFactory.AnakViewModelFactory(applicationContext)
        )[AnakViewModel::class.java]

        anakDataAdapter = AnakDataAdapter { anakData ->
            val bottomSheetDialog = BottomSheetDialog(this)

            bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog_array)
            bottomSheetDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            val list = ArrayList<Opsi>()
            val listOpsi = ArrayList<Opsi>()
            val recyclerViewOpsi =
                bottomSheetDialog.findViewById<RecyclerView>(R.id.recyclerview_opsi)
            recyclerViewOpsi!!.layoutManager = LinearLayoutManager(this)
            val imageButtonTutup =
                bottomSheetDialog.findViewById<ImageButton>(R.id.imageButtonTutup)

            // Menu clicked in the bottom sheet dialog
            val opsiAdapter = OpsiAdapter { opsi ->
                if (opsi.item == "Lihat data anak") {
                    startActivity(
                        Intent(
                            this@InformasiAnakActivity,
                            InformasiImunisasiActivity::class.java
                        )
                    )

                    bottomSheetDialog.dismiss()
                } else if (opsi.item == "Hapus data anak") {
                    val anak = Anak(
                        null,
                        "AAAA",
                        "111",
                        "23 May 2018",
                        "Perempuan",
                        "A",
                        2.2,
                        2.2,
                        5.5
                    )

                    val dialog = Dialog(this)

                    anakViewModel.addAnak(anak).observe(this) { resultResponse ->
                        dialog.setContentView(R.layout.custom_dialog_memuat)
                        dialog.setCanceledOnTouchOutside(false)
                        dialog.setCancelable(false)

                        when (resultResponse) {
                            is ResultResponse.Loading -> {
                                dialog.show()
                            }
                            is ResultResponse.Success -> {
                                dialog.dismiss()

                                anakDataAdapter.submitList(anakDataList)
                            }
                            is ResultResponse.Error -> {
                                dialog.dismiss()

                                when (resultResponse.error) {
                                    //"No Internet Connection" -> showFailure()
                                    //else -> showMasukError(true)
                                }
                            }
                        }
                    }

                    bottomSheetDialog.dismiss()
                }
            }

            val array = resources.getStringArray(R.array.opsi_anak_array)

            for (i in array.indices) {
                val opsi = Opsi(array[i])

                listOpsi.add(opsi)
            }

            list.addAll(listOpsi)

            opsiAdapter.submitList(list.toList())

            with(recyclerViewOpsi) {
                layoutManager = LinearLayoutManager(context)

                setHasFixedSize(true)

                adapter = opsiAdapter
            }

            bottomSheetDialog.show()

            imageButtonTutup!!.setOnClickListener {
                bottomSheetDialog.dismiss()
            }
        }

        binding.layoutOffline.buttonMuatUlang.setOnClickListener(this)
        binding.imagebuttonKeluar.setOnClickListener(this)
        binding.tambahDataAnak.setOnClickListener(this)

//        lifecycleScope.launchWhenStarted {
        seeDaftarAnakResponse()
//        }
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
                responseCode.launch(Intent(this, TambahAnakActivity::class.java))
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

                    anakDataList = resultResponse.data.anakData.toMutableList()

                    anakDataAdapter.submitList(anakDataList)

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

                    when (resultResponse.error) {
                        "No Internet Connection" -> drawLayout()
                        //else -> showMasukError(true)
                    }
                }
            }

            with(binding.recyclerViewDataAnak) {
                layoutManager = LinearLayoutManager(context)

                setHasFixedSize(true)

                adapter = anakDataAdapter
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