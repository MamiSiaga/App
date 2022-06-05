package com.mamisiaga.ui

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import com.mamisiaga.R
import com.mamisiaga.databinding.ActivityRencanaPersalinanBinding
import com.mamisiaga.tools.ResultResponse
import com.mamisiaga.tools.withDateFormat
import com.mamisiaga.viewmodel.KehamilanViewModel
import com.mamisiaga.viewmodelfactory.ViewModelFactory
import java.util.*

class RencanaPersalinanActivity : AppCompatActivity(), View.OnClickListener,
    DatePickerDialog.OnDateSetListener {
    private lateinit var binding: ActivityRencanaPersalinanBinding
    private lateinit var kehamilanViewModel: KehamilanViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

        binding = ActivityRencanaPersalinanBinding.inflate(layoutInflater)

        setContentView(binding.root)

        editTextListener()
        setButtonEnabled()

        kehamilanViewModel = ViewModelProvider(
            this,
            ViewModelFactory.KehamilanViewModelFactory("9|4GgQ7ufHhmiMRZ289qHshRM79vFaGquYo3JHJ54z")
        )[KehamilanViewModel::class.java]

        binding.imagebuttonKeluar.setOnClickListener(this)
        binding.edittextTglPersalinan.setOnClickListener(this)
        binding.buttonSimpan.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.imagebutton_keluar -> {
                onBackPressed()
            }
            R.id.edittext_tgl_persalinan -> {
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
                seeAddRencanaPersalinanResponse()
            }
        }
    }

    // Picking the selected date from the DatePicker
    override fun onDateSet(datePicker: DatePicker, year: Int, month: Int, day: Int) {
        val year = datePicker.year
        val month = datePicker.month + 1
        val day = datePicker.dayOfMonth

        binding.edittextTglPersalinan.setText("$day-$month-$year".withDateFormat())
    }

    private fun seeAddRencanaPersalinanResponse() {
        val dialog = Dialog(this)

        /*kehamilanViewModel.addRencanaPersalinan(persalinan).observe(this) { resultResponse ->
            dialog.setContentView(R.layout.custom_dialog_memuat)
            dialog.setCanceledOnTouchOutside(false)
            dialog.setCancelable(false)

            when (resultResponse) {
                is ResultResponse.Loading -> {
                    dialog.show()
                }
                is ResultResponse.Success -> {
                    dialog.dismiss()

                    setResult(TambahAnakActivity.TAMBAH_RENCANA_PERSALINAN_RESPONSE_CODE)

                    finish()
                }
                is ResultResponse.Error -> {
                    dialog.dismiss()

                    Toast.makeText(
                        this@TambahAnakActivity,
                        showFailure(resultResponse.error),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
         */
    }

    private fun editTextListener() {
        binding.apply {
            edittextLokasiRumahSakit.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    setButtonEnabled()
                }

                override fun afterTextChanged(s: Editable?) {

                }

            })

            edittextTglPersalinan.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    setButtonEnabled()
                }

                override fun afterTextChanged(s: Editable?) {

                }

            })
        }
    }

    private fun setButtonEnabled() {
        binding.apply {
            val lokasiRS = edittextLokasiRumahSakit.text.toString()
            val tgl = edittextTglPersalinan.text.toString()

            buttonSimpan.isEnabled =
                lokasiRS.isNotEmpty() && tgl.isNotEmpty()
        }
    }

    private fun closeKeyboard() {
        val view = this.currentFocus

        if (view != null) {
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}