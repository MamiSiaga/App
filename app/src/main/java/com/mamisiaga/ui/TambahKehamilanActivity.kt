package com.mamisiaga.ui

import android.annotation.SuppressLint
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
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import com.mamisiaga.R
import com.mamisiaga.databinding.ActivityTambahKehamilanBinding
import com.mamisiaga.tools.withDateFormat
import com.mamisiaga.viewmodel.KehamilanViewModel
import com.mamisiaga.viewmodelfactory.ViewModelFactory
import java.util.*

class TambahKehamilanActivity : AppCompatActivity(), View.OnClickListener,
    DatePickerDialog.OnDateSetListener {
    private lateinit var binding: ActivityTambahKehamilanBinding
    private lateinit var kehamilanViewModel: KehamilanViewModel

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        binding = ActivityTambahKehamilanBinding.inflate(layoutInflater)

        setContentView(binding.root)

        editTextListener()
        setButtonEnabled()

        kehamilanViewModel = ViewModelProvider(
            this,
            ViewModelFactory.KehamilanViewModelFactory("9|4GgQ7ufHhmiMRZ289qHshRM79vFaGquYo3JHJ54z")
        )[KehamilanViewModel::class.java]

        binding.imagebuttonKeluar.setOnClickListener(this)
        binding.edittextTglHamil.setOnClickListener(this)
        binding.buttonTambah.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.imagebutton_keluar -> {
                onBackPressed()
            }
            R.id.edittext_tgl_hamil -> {
                closeKeyboard()

                val calendar = Calendar.getInstance()
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val day = calendar.get(Calendar.DAY_OF_MONTH)
                val datePickerDialog = DatePickerDialog(this, this, year, month, day)

                datePickerDialog.datePicker.maxDate = calendar.timeInMillis
                datePickerDialog.show()
            }
            R.id.button_tambah -> {
                //seeTambahKehamilanResponse()
            }
        }
    }

    // Picking the selected date from the DatePicker
    override fun onDateSet(datePicker: DatePicker, year: Int, month: Int, day: Int) {
        val year = datePicker.year
        val month = datePicker.month + 1
        val day = datePicker.dayOfMonth

        binding.edittextTglHamil.setText("$day-$month-$year".withDateFormat())
    }

    private fun seeTambahKehamilanResponse() {
        val dialog = Dialog(this)

        /*
        kehamilanViewModel.addKehamilan("ibu1").observe(this) { resultResponse ->
            dialog.setContentView(R.layout.custom_dialog_memuat)
            dialog.setCanceledOnTouchOutside(false)
            dialog.setCancelable(false)

            when (resultResponse) {
                is ResultResponse.Loading -> {
                    dialog.show()
                }
                is ResultResponse.Success -> {
                    dialog.dismiss()

                    setResult(TambahAnakActivity.TAMBAH_ANAK_RESPONSE_CODE)

                    finish()
                }
                is ResultResponse.Error -> {
                    dialog.dismiss()

                    Toast.makeText(
                        this@TambahKehamilanActivity,
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
            edittextTglHamil.addTextChangedListener(object : TextWatcher {
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
            val tgl = edittextTglHamil.text.toString()

            buttonTambah.isEnabled = tgl.isNotEmpty()
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

    private fun showFailure(error: String) = when (error) {
        "No Internet Connection" -> getString(R.string.gagal_merespon_permintaan)
        else -> ""
    }
}