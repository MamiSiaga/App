package com.mamisiaga.ui

import android.app.DatePickerDialog
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.mamisiaga.R
import com.mamisiaga.databinding.ActivityRencanaMelahirkanBinding
import com.mamisiaga.tools.withDateFormat
import com.mamisiaga.viewmodel.KehamilanViewModel
import com.mamisiaga.viewmodelfactory.ViewModelFactory
import java.util.*

class RencanaMelahirkanActivity : AppCompatActivity(), View.OnClickListener,
    DatePickerDialog.OnDateSetListener {
    private lateinit var binding: ActivityRencanaMelahirkanBinding
    private lateinit var kehamilanViewModel: KehamilanViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

        binding = ActivityRencanaMelahirkanBinding.inflate(layoutInflater)

        setContentView(binding.root)

        kehamilanViewModel = ViewModelProvider(
            this,
            ViewModelFactory.KehamilanViewModelFactory(applicationContext)
        )[KehamilanViewModel::class.java]

        binding.imagebuttonKeluar.setOnClickListener(this)
        binding.edittextTglMelahirkan.setOnClickListener(this)
        binding.buttonSimpan.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.imagebutton_keluar -> {
                onBackPressed()
            }
            R.id.edittext_tgl_melahirkan -> {
                closeKeyboard()

                val calendar = Calendar.getInstance()
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val day = calendar.get(Calendar.DAY_OF_MONTH)
                val datePickerDialog = DatePickerDialog(this, this, year, month, day)

                datePickerDialog.datePicker.maxDate = calendar.timeInMillis
                datePickerDialog.show()
            }
            R.id.button_simpan -> {
                seeAddRencanaMelahirkanResponse()
            }
        }
    }

    // Picking the selected date from the DatePicker
    override fun onDateSet(datePicker: DatePicker, year: Int, month: Int, day: Int) {
        val year = datePicker.year
        val month = datePicker.month + 1
        val day = datePicker.dayOfMonth

        binding.edittextTglMelahirkan.setText("$day-$month-$year".withDateFormat())
    }

    private fun seeAddRencanaMelahirkanResponse() {

    }

    private fun closeKeyboard() {
        val view = this.currentFocus

        if (view != null) {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}