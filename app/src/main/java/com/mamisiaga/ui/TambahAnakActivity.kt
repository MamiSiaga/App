package com.mamisiaga.ui

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.mamisiaga.R
import com.mamisiaga.`class`.AnakTambah
import com.mamisiaga.databinding.ActivityTambahAnakBinding
import com.mamisiaga.tools.ResultResponse
import com.mamisiaga.tools.withDateFormat
import com.mamisiaga.viewmodel.AnakViewModel
import com.mamisiaga.viewmodelfactory.ViewModelFactory
import java.util.*

class TambahAnakActivity : AppCompatActivity(), View.OnClickListener,
    DatePickerDialog.OnDateSetListener {
    private lateinit var binding: ActivityTambahAnakBinding
    private lateinit var anakViewModel: AnakViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTambahAnakBinding.inflate(layoutInflater)

        setContentView(binding.root)

        anakViewModel = ViewModelProvider(
            this,
            ViewModelFactory.AnakViewModelFactory(applicationContext)
        )[AnakViewModel::class.java]

        binding.imagebuttonKeluar.setOnClickListener(this)
        binding.edittextTglLahir.setOnClickListener(this)
        binding.buttonTambahAnak.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.imagebutton_keluar -> {
                onBackPressed()
            }
            R.id.edittext_tgl_hamil -> {
                val calendar = Calendar.getInstance()
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val day = calendar.get(Calendar.DAY_OF_MONTH)
                val datePickerDialog = DatePickerDialog(this, this, year, month, day)

                datePickerDialog.datePicker.maxDate = calendar.timeInMillis
                datePickerDialog.show()
            }
            R.id.button_tambah_anak -> {
                seeAddAnakResponse()
            }
        }
    }

    // Picking the selected date from the DatePicker
    override fun onDateSet(datePicker: DatePicker, year: Int, month: Int, day: Int) {
        val year = datePicker.year
        val month = datePicker.month + 1
        val day = datePicker.dayOfMonth

        binding.edittextTglLahir.setText("$day-$month-$year".withDateFormat())
    }

    private fun seeAddAnakResponse() {
        val anakTambah = AnakTambah(
            binding.edittextNama.text.toString(),
            binding.edittextNik.text.toString(),
            binding.edittextTglLahir.text.toString(),
            "Perempuan",
            "A",
            binding.edittextBeratBadan.text.toString().toDouble(),
            binding.edittextTinggiBadan.text.toString().toDouble(),
            binding.edittextLingkarKepala.text.toString().toDouble()
        )

        val dialog = Dialog(this)

        anakViewModel.addAnak(anakTambah).observe(this) { resultResponse ->
            dialog.setContentView(R.layout.custom_loading_dialog)
            dialog.setCanceledOnTouchOutside(false)
            dialog.setCancelable(false)

            when (resultResponse) {
                is ResultResponse.Loading -> {
                    dialog.show()
                }
                is ResultResponse.Success -> {
                    dialog.dismiss()

                    if (!resultResponse.data.error) {
                        finish()
                    }
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
    }
}