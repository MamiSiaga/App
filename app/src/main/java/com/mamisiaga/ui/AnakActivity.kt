package com.mamisiaga.ui

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mamisiaga.R
import com.mamisiaga.adapter.OpsiAdapter
import com.mamisiaga.dataClass.Anak
import com.mamisiaga.dataClass.Opsi
import com.mamisiaga.dataClass.Pertumbuhan
import com.mamisiaga.databinding.ActivityAnakBinding
import com.mamisiaga.tools.arrayBeratBadanLakilaki
import com.mamisiaga.tools.isConnected
import com.mamisiaga.viewmodel.AnakViewModel
import com.mamisiaga.viewmodelfactory.ViewModelFactory
import lecho.lib.hellocharts.model.*


class AnakActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityAnakBinding
    private lateinit var anakViewModel: AnakViewModel
    private lateinit var anak: Anak
    private val responseCode =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            when (result.resultCode) {
                TambahEditPertumbuhanAnakActivity.TAMBAH_PERTUMBUHAN_ANAK_RESPONSE_CODE -> {
                    //anakViewModel.getPertumbuhanAnak("1").removeObservers(this)

                    //seeAnakResponse()

                    Toast.makeText(
                        this,
                        "Berhasil menambahkan data pertumbuhan anak baru.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAnakBinding.inflate(layoutInflater)

        setContentView(binding.root)

        anak = intent.getParcelableExtra<Anak>(EXTRA_ANAK) as Anak

        binding.textviewNamaAnak.text = anak.name

        anakViewModel = ViewModelProvider(
            this,
            ViewModelFactory.AnakViewModelFactory(applicationContext)
        )[AnakViewModel::class.java]

        seeAnakResponse()
        showGrafikKMS()

        binding.imagebuttonKeluar.setOnClickListener(this)
        binding.layoutInfoImunisasi.setOnClickListener(this)
        binding.imageviewOpsiGrafikPertumbuhan.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.imagebutton_keluar -> {
                onBackPressed()
            }
            R.id.layoutInfoImunisasi -> {
                startActivity(Intent(this, InformasiImunisasiActivity::class.java))
            }
            R.id.imageview_opsi_grafik_pertumbuhan -> {
                showOpsi()
            }
        }
    }

    private fun seeAnakResponse() {
        /*anakViewModel.getPertumbuhanAnak("anak1").observe(this) { resultResponse ->
            when (resultResponse) {
                is ResultResponse.Loading -> {
                    showLoadingSign(true)
                }
                is ResultResponse.Success -> {
                    showLoadingSign(false)


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

    private fun showGrafikKMS() {
        val yGrafikKMS = arrayOf<Number>(
            5,
            6.7,
            7.2,
            7.7,
            8,
            8.2,
            8,
            8.7,
            9.3,
            9.2,
            9,
            9.5,
            9.8,
            9,
            9,
            9,
            10,
            10,
            11,
            12,
            13,
            14,
            15
        )

        val lines = arrayListOf<Line>()
        val data = LineChartData()
        val axisValues = arrayListOf<AxisValue>()

        val yGrafikKMSValues = arrayListOf<PointValue>()

        val lineGrafikKMS = Line(yGrafikKMSValues).setColor(Color.parseColor("#0986DF"))

        for (i in arrayBeratBadanLakilaki) {
            val yBeratBadanLakilaki = arrayListOf<PointValue>()

            for (j in i.indices) {
                yBeratBadanLakilaki.add(PointValue(j.toFloat(), i[j].toFloat()))
            }

            val lineBeratBadan = Line(yBeratBadanLakilaki)
            lineBeratBadan.setHasPoints(false)

            lines.add(lineBeratBadan)
        }

        for (i in 0..60) {
            axisValues.add(i, AxisValue(i.toFloat()).setLabel(i.toString()))
        }

        for (i in yGrafikKMS.indices) {
            yGrafikKMSValues.add(PointValue(i.toFloat(), yGrafikKMS[i].toFloat()))
        }

        /*
        for (i in plus3SD.indices) {
            yPlus3SDValues.add(PointValue(i.toFloat(), plus3SD[i].toFloat()))
        }
        */

        lines.add(lineGrafikKMS)

        data.lines = lines

        binding.plot.lineChartData = data
        binding.plot.maxZoom = 1f

        val axis = Axis().setName("Usia (bulan)")
        axis.values = axisValues
        data.axisXBottom = axis

        val yAxis = Axis().setName("Berat badan (kg)")
        data.axisYLeft = yAxis
    }

    private fun showOpsi() {
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
            if (opsi.item == "Tambah pertumbuhan anak") {
                val pertumbuhan = Pertumbuhan(null,22, null, null, null)

                responseCode.launch(
                    Intent(
                        this,
                        TambahEditPertumbuhanAnakActivity::class.java
                    ).putExtra(TambahEditPertumbuhanAnakActivity.EXTRA_PERTUMBUHAN, pertumbuhan)
                )

                bottomSheetDialog.dismiss()
            } else if (opsi.item == "Lihat riwayat pertumbuhan") {
                startActivity(Intent(this, RiwayatPertumbuhanActivity::class.java))

                bottomSheetDialog.dismiss()
            }
        }

        val array = resources.getStringArray(R.array.opsi_grafik_pertumbuhan_array)

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

    private fun showKonfirmasi() {
        val alert = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.custom_dialog, null)
        val buttonOK = view.findViewById<Button>(R.id.button_ok)
        val buttonCancel = view.findViewById<Button>(R.id.button_cancel)

        alert.setView(view)

        val alertDialog: AlertDialog = alert.create()

        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        buttonOK.setOnClickListener {
            alertDialog.dismiss()
        }

        buttonCancel.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.show()
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
        const val EXTRA_ANAK = "extraAnak"
    }
}