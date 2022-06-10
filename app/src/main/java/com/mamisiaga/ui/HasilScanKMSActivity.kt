package com.mamisiaga.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
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
    private var pertumbuhanListNew: ArrayList<Pertumbuhan> = ArrayList()

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

        pertumbuhanListNew = pertumbuhanList

        pertumbuhanViewModel = ViewModelProvider(
            this,
            ViewModelFactory.PertumbuhanViewModelFactory(ibu.token!!)
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

    private fun seeAddPertumbuhanResponse() {
        var isNotEmpty = true

        pertumbuhanList.forEachIndexed { index, pertumbuhan ->
            Log.d("Check", pertumbuhan.weight.toString())

            if (pertumbuhan.weight == null) {
                isNotEmpty = false
            }
        }

        if (isNotEmpty) {
            val intent = Intent(this@HasilScanKMSActivity, AnakActivity::class.java)

            intent.flags =
                Intent.FLAG_ACTIVITY_REORDER_TO_FRONT

            startActivity(
                intent.putExtra(AnakActivity.EXTRA_IBU, ibu).putExtra(AnakActivity.EXTRA_ANAK, anak)
            )
        } else {
            Toast.makeText(
                this@HasilScanKMSActivity,
                "Ada bagian yang kosong.",
                Toast.LENGTH_SHORT
            ).show()
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
            getString(R.string.apakah_anda_yakin_untuk_menyimpannya)
        pertanyaanKonfirmasiDeskripsi.text =
            getString(R.string.setelah_ini_akan_memasukkan_data_pertumbuhan_anak_secara_manual)

        alert.setView(view)

        val alertDialog: AlertDialog = alert.create()

        //alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        buttonYa.setOnClickListener {
            seeAddPertumbuhanResponse()

            alertDialog.dismiss()
        }

        buttonTidak.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.show()
    }

    private fun setAdapter() {
        val scanKMSAdapter = ScanKMSAdapter(pertumbuhanListNew)

        binding.recyclerviewPerkembanganAnak.apply {
            layoutManager = LinearLayoutManager(this@HasilScanKMSActivity)
            adapter = scanKMSAdapter
        }
    }

    private fun setButtonEnabled() {

    }

    companion object {
        const val EXTRA_LIST_BERAT_BADAN = "extraListBeratBadan"
        const val EXTRA_IBU = "extraIbu"
        const val EXTRA_ANAK = "extraAnak"
    }
}