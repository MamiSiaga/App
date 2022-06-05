package com.mamisiaga.ui

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.mamisiaga.R
import com.mamisiaga.dataClass.Pertumbuhan
import com.mamisiaga.databinding.ActivityTambahEditPertumbuhanAnakBinding
import com.mamisiaga.tools.withDateFormat
import java.util.*

class TambahEditPertumbuhanAnakActivity : AppCompatActivity(), View.OnClickListener,
    DatePickerDialog.OnDateSetListener {
    private lateinit var binding: ActivityTambahEditPertumbuhanAnakBinding
    private var isEdit = false
    private var age: Int = 0
    private var pertumbuhan: Pertumbuhan? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        binding = ActivityTambahEditPertumbuhanAnakBinding.inflate(layoutInflater)

        setContentView(binding.root)

        pertumbuhan = intent.getParcelableExtra(EXTRA_PERTUMBUHAN)

        if (pertumbuhan?.dateOfMeasurement != null) {
            age = intent.getIntExtra(EXTRA_AGE, 0)
            isEdit = true
        }

        if (isEdit) {
            binding.apply {
                textviewMenu.text = getString(R.string.edit_pertumbuhan_anak)
                buttonTambahSimpan.text = getString(R.string.simpan)
            }
        }

        editTextListener()
        setButtonEnabled()

        binding.apply {
            imagebuttonKeluar.setOnClickListener(this@TambahEditPertumbuhanAnakActivity)
            edittextTglPemeriksaan.setOnClickListener(this@TambahEditPertumbuhanAnakActivity)
            buttonTambahSimpan.setOnClickListener(this@TambahEditPertumbuhanAnakActivity)
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.imagebutton_keluar -> {
                onBackPressed()
            }
            R.id.edittext_tgl_pemeriksaan -> {
                closeKeyboard()

                val calendar = Calendar.getInstance()
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val day = calendar.get(Calendar.DAY_OF_MONTH)
                val datePickerDialog = DatePickerDialog(this, this, year, month, day)

                datePickerDialog.datePicker.maxDate = calendar.timeInMillis
                datePickerDialog.show()
            }
            R.id.button_tambah_simpan -> {
                pertumbuhan = Pertumbuhan(
                    null, null,
                    binding.edittextBeratBadan.text.toString().toInt(),
                    binding.edittextTinggiBadan.text.toString().toInt(),
                    binding.edittextLingkarKepala.text.toString().toInt()
                )
                
                if (isEdit) {
                    seeEditPertumbuhanAnakResponse()
                } else {
                    seeAddPertumbuhanAnakResponse()
                }
            }
        }
    }

    override fun onDateSet(datePicker: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        val tanggal = "${datePicker.dayOfMonth}-${datePicker.month + 1}-${datePicker.year}"

        binding.edittextTglPemeriksaan.setText(tanggal.withDateFormat())
    }

    private fun seeAddPertumbuhanAnakResponse() {
        /*
        val dialog = Dialog(this)

        anakViewModel.addPertumbuhanAnakResponse(pertumbuhan).observe(this) { resultResponse ->
            dialog.setContentView(R.layout.custom_dialog_memuat)
            dialog.setCanceledOnTouchOutside(false)
            dialog.setCancelable(false)

            when (resultResponse) {
                is ResultResponse.Loading -> {
                    dialog.show()
                }
                is ResultResponse.Success -> {
                    dialog.dismiss()

                    setResult(RESULT_ADD)

                    finish()
                }
                is ResultResponse.Error -> {
                    dialog.dismiss()

                    Toast.makeText(
                        this@MasukActivity,
                        showFailure(resultResponse.error),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
         */
    }

    private fun seeEditPertumbuhanAnakResponse() {
        /*
        val dialog = Dialog(this)

        anakViewModel.editPertumbuhanAnakResponse(pertumbuhan).observe(this) { resultResponse ->
            dialog.setContentView(R.layout.custom_dialog_memuat)
            dialog.setCanceledOnTouchOutside(false)
            dialog.setCancelable(false)

            when (resultResponse) {
                is ResultResponse.Loading -> {
                    dialog.show()
                }
                is ResultResponse.Success -> {
                    dialog.dismiss()

                    setResult(RESULT_EDIT)

                    finish()
                }
                is ResultResponse.Error -> {
                    dialog.dismiss()

                    Toast.makeText(
                        this@MasukActivity,
                        showFailure(resultResponse.error),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
         */
    }

    private fun closeKeyboard() {
        val view = this.currentFocus

        if (view != null) {
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun editTextListener() {
        binding.apply {
            edittextTglPemeriksaan.addTextChangedListener(object : TextWatcher {
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

            edittextBeratBadan.addTextChangedListener(object : TextWatcher {
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

            edittextTinggiBadan.addTextChangedListener(object : TextWatcher {
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

            edittextLingkarKepala.addTextChangedListener(object : TextWatcher {
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

    private fun showFailure(error: String) = when (error) {
        "No Internet Connection" -> getString(R.string.gagal_merespon_permintaan)
        else -> ""
    }

    private fun setButtonEnabled() {
        binding.apply {
            val tglPeriksa = edittextTglPemeriksaan.text.toString()
            val bbAnak = edittextBeratBadan.text.toString()
            val tbAnak = edittextTinggiBadan.text.toString()
            val lkAnak = edittextLingkarKepala.text.toString()

            buttonTambahSimpan.isEnabled =
                tglPeriksa.isNotEmpty() && bbAnak.isNotEmpty()
                        && tbAnak.isNotEmpty() && lkAnak.isNotEmpty()
        }
    }

    companion object {
        const val EXTRA_PERTUMBUHAN = "extra_pertumbuhan"
        const val EXTRA_AGE = "extra_age"
        const val TAMBAH_PERTUMBUHAN_ANAK_RESPONSE_CODE = 1
        const val EDIT_PERTUMBUHAN_ANAK_RESPONSE_CODE = 2
    }
}