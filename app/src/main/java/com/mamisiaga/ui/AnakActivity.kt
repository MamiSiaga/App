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
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mamisiaga.R
import com.mamisiaga.adapter.OpsiAdapter
import com.mamisiaga.api.PertumbuhanData
import com.mamisiaga.dataClass.Anak
import com.mamisiaga.dataClass.Ibu
import com.mamisiaga.dataClass.Opsi
import com.mamisiaga.dataClass.Pertumbuhan
import com.mamisiaga.databinding.ActivityAnakBinding
import com.mamisiaga.tools.ResultResponse
import com.mamisiaga.tools.arrayBeratBadanLakilaki
import com.mamisiaga.tools.arrayBeratBadanPerempuan
import com.mamisiaga.tools.isConnected
import com.mamisiaga.viewmodel.PertumbuhanViewModel
import com.mamisiaga.viewmodelfactory.ViewModelFactory
import lecho.lib.hellocharts.model.*


class AnakActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityAnakBinding
    private lateinit var pertumbuhanViewModel: PertumbuhanViewModel
    private lateinit var ibu: Ibu
    private lateinit var anak: Anak
    private var age = 0
    private val responseCode =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            when (result.resultCode) {
                TambahEditPertumbuhanAnakActivity.TAMBAH_PERTUMBUHAN_ANAK_RESPONSE_CODE -> {
                    pertumbuhanViewModel.getPertumbuhan(anak.id!!).removeObservers(this)

                    seePertumbuhanResponse()

                    Toast.makeText(
                        this,
                        "Penambahan data pertumbuhan anak berhasil.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        binding = ActivityAnakBinding.inflate(layoutInflater)

        setContentView(binding.root)

        ibu = intent.getParcelableExtra<Ibu>(EXTRA_IBU) as Ibu
        anak = intent.getParcelableExtra<Anak>(EXTRA_ANAK) as Anak

        binding.textviewNamaAnak.text = anak.name

        pertumbuhanViewModel = ViewModelProvider(
            this,
            ViewModelFactory.PertumbuhanViewModelFactory(ibu.token!!)
        )[PertumbuhanViewModel::class.java]

        seePertumbuhanResponse()
        //showGrafikKMS()

        binding.imagebuttonKeluar.setOnClickListener(this)
        binding.layoutInfoImunisasi.setOnClickListener(this)
        binding.imageviewOpsiGrafikPertumbuhan.setOnClickListener(this)
        binding.buttonMulaiScanKmsAnak.setOnClickListener(this)
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
            R.id.button_mulai_scan_kms_anak -> {
                startActivity(Intent(this@AnakActivity, ScanKMSActivity::class.java))
            }
        }
    }

    private fun seePertumbuhanResponse() {
        pertumbuhanViewModel.getPertumbuhan(anak.id!!).observe(this) { resultResponse ->
            when (resultResponse) {
                is ResultResponse.Loading -> {
                    showLoadingSign(true)
                }
                is ResultResponse.Success -> {
                    showLoadingSign(false)

                    if (resultResponse.data.pertumbuhanData.isEmpty()) {
                        binding.tubuh2.visibility = View.GONE
                        binding.tubuh1.visibility = View.VISIBLE
                    } else {
                        binding.tubuh1.visibility = View.GONE
                        binding.tubuh2.visibility = View.VISIBLE
                        binding.plot.visibility = View.VISIBLE

                        age = resultResponse.data.pertumbuhanData.size

                        showGrafikKMS(resultResponse.data.pertumbuhanData)
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
        }
    }

    /*
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

     */

    private fun showGrafikKMS(listPertumbuhanData: List<PertumbuhanData>) {
        val lines = arrayListOf<Line>()
        val data = LineChartData()
        val axisValues = arrayListOf<AxisValue>()

        val yGrafikKMSValues = arrayListOf<PointValue>()

        val lineGrafikKMS = Line(yGrafikKMSValues).setColor(Color.parseColor("#0986DF"))

        val arrayBeratBadan = when (anak.sex) {
            1 -> arrayBeratBadanLakilaki
            else -> arrayBeratBadanPerempuan
        }

        binding.textViewJenisKelamin.text = when (anak.sex) {
            1 -> getString(R.string.laki_laki)
            else -> getString(R.string.perempuan)
        }

        val start = when (listPertumbuhanData.size) {
            in 1..12 -> 0
            else -> listPertumbuhanData.size - 12
        }

        val end = listPertumbuhanData.size

        for (i in arrayBeratBadan) {
            val yBeratBadan = arrayListOf<PointValue>()

            for (j in start until end) {
                yBeratBadan.add(PointValue(j.toFloat(), i[j].toFloat()))
            }

            val lineBeratBadan = Line(yBeratBadan)
            lineBeratBadan.setHasPoints(false)

            lines.add(lineBeratBadan)
        }

        for (i in start until end) {
            axisValues.add(i, AxisValue(i.toFloat()).setLabel(i.toString()))
        }

        for (i in start until end) {
            yGrafikKMSValues.add(PointValue(i.toFloat(), listPertumbuhanData[i].weight.toFloat()))
        }

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
                val pertumbuhan = Pertumbuhan(null, anak.id, null, age, null, null, null)

                responseCode.launch(
                    Intent(
                        this,
                        TambahEditPertumbuhanAnakActivity::class.java
                    ).putExtra(
                        RiwayatPertumbuhanActivity.EXTRA_IBU,
                        ibu
                    ).putExtra(TambahEditPertumbuhanAnakActivity.EXTRA_PERTUMBUHAN, pertumbuhan)
                )

                bottomSheetDialog.dismiss()
            } else if (opsi.item == "Lihat riwayat pertumbuhan") {
                startActivity(
                    Intent(this, RiwayatPertumbuhanActivity::class.java).putExtra(
                        RiwayatPertumbuhanActivity.EXTRA_IBU,
                        ibu
                    ).putExtra(RiwayatPertumbuhanActivity.EXTRA_ANAK, anak)
                )

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
            binding.tubuh1.visibility = View.GONE
            binding.tubuh2.visibility = View.GONE
            binding.plot.visibility = View.INVISIBLE
            binding.layoutMemuat.visibility = View.VISIBLE
        } else {
            binding.layoutMemuat.visibility = View.GONE
            binding.plot.visibility = View.INVISIBLE
        }
    }

    companion object {
        const val EXTRA_IBU = "extraIbu"
        const val EXTRA_ANAK = "extraAnak"
    }
}