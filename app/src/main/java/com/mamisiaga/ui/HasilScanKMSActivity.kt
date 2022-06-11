package com.mamisiaga.ui

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
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
import com.mamisiaga.tools.OnEditTextChanged
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

    private fun seeAddPertumbuhanAnakResponse() {
        val dialog = Dialog(this)

        /*pertumbuhanViewModel.addPertumbuhan(pertumbuhan!!).observe(this) { resultResponse ->
            dialog.setContentView(R.layout.custom_dialog_memuat)
            dialog.setCanceledOnTouchOutside(false)
            dialog.setCancelable(false)

            when (resultResponse) {
                is ResultResponse.Loading -> {
                    dialog.show()
                }
                is ResultResponse.Success -> {
                    dialog.dismiss()

                    val intent = Intent(this@HasilScanKMSActivity, AnakActivity::class.java)

                    intent.flags =
                        Intent.FLAG_ACTIVITY_REORDER_TO_FRONT

                    startActivity(
                        intent.putExtra(AnakActivity.EXTRA_IBU, ibu).putExtra(AnakActivity.EXTRA_ANAK, anak)
                    )
                }
                is ResultResponse.Error -> {
                    dialog.dismiss()

                    Toast.makeText(
                        this@HasilScanKMSActivity,
                        showFailure(resultResponse.error),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
         */
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
            seeAddPertumbuhanAnakResponse()

            alertDialog.dismiss()
        }

        buttonTidak.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.show()
    }

    private fun setAdapter() {
        val scanKMSAdapter = ScanKMSAdapter(pertumbuhanList, object : OnEditTextChanged {
            override fun onTextChanged(position: Int, charSeq: String?) {
                var isNotEmpty = true

                try {
                    pertumbuhanList[position].weight = charSeq!!.toInt()
                } catch (e: Exception) {
                    pertumbuhanList[position].weight = null

                    isNotEmpty = false
                } finally {
                    checkIsNotEmpty(isNotEmpty)
                }
            }
        })

        binding.recyclerviewPerkembanganAnak.apply {
            layoutManager = LinearLayoutManager(this@HasilScanKMSActivity)
            adapter = scanKMSAdapter
        }
    }

    private fun checkIsNotEmpty(isNotEmpty: Boolean) {
        var isNotEmpty = isNotEmpty

        if (!isNotEmpty) setButtonEnabled(false)
        else {
            for (i in pertumbuhanList) {
                if (i.weight == null) {
                    isNotEmpty = false

                    break
                }
            }

            setButtonEnabled(isNotEmpty)
        }
    }

    private fun setButtonEnabled(isNotEmpty: Boolean) {
        binding.buttonSimpan.isEnabled = isNotEmpty
    }

    companion object {
        const val EXTRA_LIST_BERAT_BADAN = "extraListBeratBadan"
        const val EXTRA_IBU = "extraIbu"
        const val EXTRA_ANAK = "extraAnak"
    }
}