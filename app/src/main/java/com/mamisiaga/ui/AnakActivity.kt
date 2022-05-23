package com.mamisiaga.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.androidplot.xy.LineAndPointFormatter
import com.androidplot.xy.PanZoom
import com.androidplot.xy.SimpleXYSeries
import com.androidplot.xy.XYGraphWidget
import com.mamisiaga.R
import com.mamisiaga.`class`.Anak
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

        // Checking Internet connection
        if (!isConnected(applicationContext)) {

        }

        //anak = intent.getParcelableExtra<Anak>(EXTRA_ANAK) as Anak

        /*
        anakViewModel = ViewModelProvider(
            this,
            ViewModelFactory.AnakViewModelFactory(applicationContext)
        )[AnakViewModel::class.java]
         */

        seeAnakResponse()

        binding.imagebuttonKeluar.setOnClickListener(this)
        binding.layoutInfoImunisasi.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.imagebutton_keluar -> {
                onBackPressed()
            }
            R.id.layoutInfoImunisasi -> {
                startActivity(Intent(this, InformasiImunisasiActivity::class.java))
            }
        }
    }

    private fun seeAnakResponse() {
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

    companion object {
        const val EXTRA_ANAK = "extraAnak"
    }
}