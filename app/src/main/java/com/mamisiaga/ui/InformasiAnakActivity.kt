package com.mamisiaga.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mamisiaga.R
import com.mamisiaga.`class`.Opsi
import com.mamisiaga.adapter.AnakDataAdapter
import com.mamisiaga.adapter.OpsiAnakAdapter
import com.mamisiaga.databinding.ActivityInformasiAnakBinding
import com.mamisiaga.tools.ResultResponse
import com.mamisiaga.viewmodel.AnakViewModel
import com.mamisiaga.viewmodelfactory.ViewModelFactory

class InformasiAnakActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityInformasiAnakBinding
    private lateinit var anakViewModel: AnakViewModel
    private lateinit var anakDataAdapter: AnakDataAdapter

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

            val list = ArrayList<Opsi>()
            val listOpsi = ArrayList<Opsi>()

            if (anakData.id == "1") {
                val array = resources.getStringArray(R.array.imunisasi_mulai_array)

                for (i in array.indices) {
                    val opsi = Opsi(array[i])

                    listOpsi.add(opsi)
                }
            }

            list.addAll(listOpsi)

            val opsiAdapter = OpsiAnakAdapter(list, anakData)
            val recyclerViewOpsi =
                bottomSheetDialog.findViewById<RecyclerView>(R.id.recyclerview_opsi)
            recyclerViewOpsi!!.layoutManager = LinearLayoutManager(this)
            val imageButtonTutup =
                bottomSheetDialog.findViewById<ImageButton>(R.id.imageButtonTutup)
            recyclerViewOpsi.adapter = opsiAdapter

            bottomSheetDialog.show()

            imageButtonTutup!!.setOnClickListener {
                bottomSheetDialog.dismiss()
            }
        }

        binding.tambahDataAnak.setOnClickListener(this)
        binding.imagebuttonKeluar.setOnClickListener(this)

        lifecycleScope.launchWhenStarted {
            seeDaftarAnakResponse()
        }
    }

    override fun onResume() {
        super.onResume()

        anakViewModel.getAnak("1").removeObservers(this)
        seeDaftarAnakResponse()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.imagebutton_keluar -> {
                onBackPressed()
            }
            R.id.tambahDataAnak -> {
                startActivity(Intent(this, TambahAnakActivity::class.java))
            }
        }
    }

    private fun seeDaftarAnakResponse() {
        anakViewModel.getAnak("ibu1").observe(this) { resultResponse ->
            when (resultResponse) {
                is ResultResponse.Loading -> {

                }
                is ResultResponse.Success -> {
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
                    when (resultResponse.error) {
                        //"No Internet Connection" -> showFailure()
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
}