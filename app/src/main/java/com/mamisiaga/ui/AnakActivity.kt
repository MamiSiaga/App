package com.mamisiaga.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
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
import com.mamisiaga.tools.*
import com.mamisiaga.viewmodel.PertumbuhanViewModel
import com.mamisiaga.viewmodelfactory.ViewModelFactory
import lecho.lib.hellocharts.model.*


class AnakActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityAnakBinding
    private lateinit var pertumbuhanViewModel: PertumbuhanViewModel
    private lateinit var ibu: Ibu
    private lateinit var anak: Anak
    private var age = 0
    private var comparison = 0
    private val responseCode =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            when (result.resultCode) {
                TambahEditPertumbuhanAnakActivity.TAMBAH_PERTUMBUHAN_ANAK_RESPONSE_CODE -> {
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

        binding.layoutOffline.buttonMuatUlang.setOnClickListener(this)
        binding.imagebuttonKeluar.setOnClickListener(this)
        binding.layoutInfoImunisasi.setOnClickListener(this)
        binding.imageviewOpsiGrafikPertumbuhan.setOnClickListener(this)
        binding.buttonMulaiScanKmsAnak.setOnClickListener(this)
        binding.buttonLewatiBagianIni.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.button_muat_ulang -> {
                recreate()
            }
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
                val intent = Intent(this@AnakActivity, ScanKMSActivity::class.java)

                startActivity(
                    intent.putExtra(ScanKMSActivity.EXTRA_IBU, ibu)
                        .putExtra(ScanKMSActivity.EXTRA_ANAK, anak)
                        .putExtra(ScanKMSActivity.EXTRA_AGE, age)
                )
            }
            R.id.button_lewati_bagian_ini -> {
                showKonfirmasi()
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

                    binding.textViewJenisKelamin.text = resources.getString(R.string.jenis_kelamin_anak,
                        when (anak.sex) {
                            1 -> getString(R.string.laki_laki)
                            else -> getString(R.string.perempuan)
                        })

                    comparison = getComparisonWithCurrentDate(anak.dateOfBirth!!).toInt()

                    if (resultResponse.data.pertumbuhanData.isEmpty() &&
                        comparison > 0
                    ) {
                        binding.tubuh2.visibility = View.GONE
                        binding.tubuh1.visibility = View.VISIBLE

                        binding.textviewStatus.text =
                            getString(R.string.status_usia_anak, comparison.toString())
                    } else {
                        binding.tubuh1.visibility = View.GONE
                        binding.tubuh2.visibility = View.VISIBLE

                        if (resultResponse.data.pertumbuhanData.isEmpty()) {
                            binding.plot.visibility = View.INVISIBLE
                            binding.textviewTidakAdaData.visibility = View.VISIBLE
                        } else {
                            binding.textviewTidakAdaData.visibility = View.GONE
                            binding.plot.visibility = View.VISIBLE

                            age = resultResponse.data.pertumbuhanData.size

                            showGrafikKMS(resultResponse.data.pertumbuhanData)
                        }
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

        pertumbuhanViewModel.getPertumbuhan(anak.id!!).removeObservers(this)
    }

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

        val start = when (listPertumbuhanData.size) {
            in 1..12 -> 0
            else -> listPertumbuhanData.size - 12
        }

        val end = listPertumbuhanData.size - 1

        for (i in arrayBeratBadan) {
            val yBeratBadan = arrayListOf<PointValue>()

            for (j in start..end) {
                yBeratBadan.add(PointValue(j.toFloat(), i[j].toFloat()))
            }

            val lineBeratBadan = Line(yBeratBadan)
            lineBeratBadan.setHasPoints(false)

            lines.add(lineBeratBadan)
        }

        for (i in 0..end) {
            axisValues.add(i, AxisValue(i.toFloat()).setLabel(i.toString()))
        }

        for (i in start..end) {
            yGrafikKMSValues.add(PointValue(i.toFloat(), listPertumbuhanData[i].weight.toFloat()))
        }

        lines.add(lineGrafikKMS)

        data.lines = lines

        val axis = Axis().setName("Usia (bulan)")
        axis.values = axisValues
        data.axisXBottom = axis

        val yAxis = Axis().setName("Berat badan (kg)")
        data.axisYLeft = yAxis

        binding.plot.lineChartData = data
        binding.plot.maxZoom = 0f
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
                if (age <= 60) {
                    if (age <= comparison) {
                        val pertumbuhan = Pertumbuhan(null, anak.id, null, age, null, null, null)

                        responseCode.launch(
                            Intent(
                                this,
                                TambahEditPertumbuhanAnakActivity::class.java
                            ).putExtra(
                                TambahEditPertumbuhanAnakActivity.EXTRA_IBU,
                                ibu
                            ).putExtra(
                                TambahEditPertumbuhanAnakActivity.EXTRA_PERTUMBUHAN,
                                pertumbuhan
                            )
                        )
                    } else {
                        Toast.makeText(
                            this,
                            "Anak Anda, ${anak.name}, masih berusia $comparison bulan.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this,
                        "Anak Anda, ${anak.name}, telah berusia lebih dari 60 bulan.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

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
        val view = layoutInflater.inflate(R.layout.custom_dialog_konfirmasi, null)
        val buttonYa = view.findViewById<Button>(R.id.button_ya)
        val buttonTidak = view.findViewById<Button>(R.id.button_tidak)
        val pertanyaanKonfirmasi = view.findViewById<TextView>(R.id.textview_pertanyaan_konfirmasi)
        val pertanyaanKonfirmasiDeskripsi =
            view.findViewById<TextView>(R.id.textview_pertanyaan_konfirmasi_deskripsi)

        pertanyaanKonfirmasi.text =
            getString(R.string.apakah_anda_yakin_untuk_tidak_melakukan_scan_kms_anak)
        pertanyaanKonfirmasiDeskripsi.text =
            getString(R.string.anda_akan_memasukkan_data_pertumbuhan_anak_secara_manual)

        alert.setView(view)

        val alertDialog: AlertDialog = alert.create()

        //alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        buttonYa.setOnClickListener {
            val pertumbuhan = Pertumbuhan(null, anak.id, null, age, null, null, null)

            responseCode.launch(
                Intent(
                    this,
                    TambahEditPertumbuhanAnakActivity::class.java
                ).putExtra(
                    TambahEditPertumbuhanAnakActivity.EXTRA_IBU,
                    ibu
                ).putExtra(TambahEditPertumbuhanAnakActivity.EXTRA_PERTUMBUHAN, pertumbuhan)
            )

            alertDialog.dismiss()
        }

        buttonTidak.setOnClickListener {
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