package com.mamisiaga.ui

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mamisiaga.R
import com.mamisiaga.adapter.ScanKMSAdapter
import com.mamisiaga.dataClass.Anak
import com.mamisiaga.dataClass.Ibu
import com.mamisiaga.dataClass.Pertumbuhan
import com.mamisiaga.databinding.ActivityHasilScanKmsBinding
import com.mamisiaga.viewmodel.PertumbuhanViewModel
import com.mamisiaga.viewmodelfactory.ViewModelFactory

class HasilScanKMSActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityHasilScanKmsBinding
    private lateinit var pertumbuhanViewModel: PertumbuhanViewModel
    private lateinit var ibu: Ibu
    private lateinit var anak: Anak
    private lateinit var pertumbuhanList: ArrayList<Pertumbuhan>

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        binding = DataBindingUtil.setContentView(this, R.layout.activity_hasil_scan_kms)

        ibu = intent.getParcelableExtra<Ibu>(EXTRA_IBU) as Ibu
        anak = intent.getParcelableExtra<Anak>(EXTRA_ANAK) as Anak
        pertumbuhanList =
            intent.getParcelableArrayListExtra<Pertumbuhan>(EXTRA_LIST_BERAT_BADAN) as ArrayList<Pertumbuhan>

        pertumbuhanViewModel = ViewModelProvider(
            this,
            ViewModelFactory.PertumbuhanViewModelFactory("9|4GgQ7ufHhmiMRZ289qHshRM79vFaGquYo3JHJ54z")
        )[PertumbuhanViewModel::class.java]

        binding.textviewPerkembanganAnak.text = getString(R.string.perkembangan_anak, anak.name)

        setAdapter()

        binding.imagebuttonKeluar.setOnClickListener(this)
        binding.buttonSimpan.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.imagebutton_keluar -> {
                onBackPressed()
            }
            R.id.button_simpan -> {
                showKonfirmasi()
            }
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
            seeAddPertumbuhanResponse()

            alertDialog.dismiss()
        }

        buttonCancel.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.show()
    }

    private fun setAdapter() {
        val scanKMSAdapter = ScanKMSAdapter(pertumbuhanList)

        binding.recyclerviewPerkembanganAnak.apply {
            layoutManager = LinearLayoutManager(this@HasilScanKMSActivity)
            adapter = scanKMSAdapter
        }
    }

    private fun seeAddPertumbuhanResponse() {

    }

    companion object {
        const val EXTRA_LIST_BERAT_BADAN = "extraListBeratBadan"
        const val EXTRA_IBU = "extraIbu"
        const val EXTRA_ANAK = "extraAnak"
    }
}