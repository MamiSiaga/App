package com.mamisiaga.ui

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidplot.xy.LineAndPointFormatter
import com.androidplot.xy.PanZoom
import com.androidplot.xy.SimpleXYSeries
import com.androidplot.xy.XYGraphWidget
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mamisiaga.R
import com.mamisiaga.`class`.Anak
import com.mamisiaga.`class`.Opsi
import com.mamisiaga.adapter.OpsiAdapter
import com.mamisiaga.databinding.ActivityAnakBinding
import com.mamisiaga.tools.isConnected
import com.mamisiaga.viewmodel.AnakViewModel
import java.text.FieldPosition
import java.text.Format
import java.text.ParsePosition
import kotlin.math.roundToInt

class AnakActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityAnakBinding
    private lateinit var anakViewModel: AnakViewModel
    private lateinit var anak: Anak

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAnakBinding.inflate(layoutInflater)

        setContentView(binding.root)

        anak = intent.getParcelableExtra<Anak>(EXTRA_ANAK) as Anak

        binding.textviewNamaAnak.text = anak.name

        /*
        anakViewModel = ViewModelProvider(
            this,
            ViewModelFactory.AnakViewModelFactory(applicationContext)
        )[AnakViewModel::class.java]
         */

        seeAnakResponse()

        binding.imagebuttonKeluar.setOnClickListener(this)
        binding.layoutInfoImunisasi.setOnClickListener(this)
        binding.layoutScanPhoto.setOnClickListener(this)
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
            R.id.layoutScanPhoto -> {
                startActivity(Intent(this, KameraActivity::class.java))
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
            if (opsi.item == "Lihat riwayat pertumbuhan") {
                //startActivity(Intent(this@RiwayatPertumbuhanActivity, InformasiImunisasiActivity::class.java))

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

    private fun showGrafik() {
        val domainLabels = arrayOf<Number>(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
        val series1Numbers = arrayOf<Number>(5, 6.7, 7.2, 7.7, 8, 8.2, 8, 8.7, 9.3, 9.2, 9, 9.5)
        val series1 = SimpleXYSeries(
            listOf(* series1Numbers),
            SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,
            "Series 1"
        )
        val series1Format = LineAndPointFormatter(Color.BLUE, Color.BLACK, null, null)

        binding.plot.addSeries(series1, series1Format)
        binding.plot.graph.getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).format = object : Format() {
            override fun format(p0: Any, p1: StringBuffer, p2: FieldPosition): StringBuffer {
                val i = (p0 as Number).toFloat().roundToInt()

                return p1.append(domainLabels[i])
            }

            override fun parseObject(p0: String?, p1: ParsePosition?): Any? {
                return null
            }
        }

        PanZoom.attach(binding.plot)
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

    companion object {
        const val EXTRA_ANAK = "extraAnak"
    }
}