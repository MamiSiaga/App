package com.mamisiaga.ui

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
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
import com.mamisiaga.dataClass.Anak
import com.mamisiaga.dataClass.Ibu
import com.mamisiaga.databinding.ActivityTambahAnakBinding
import com.mamisiaga.tools.ResultResponse
import com.mamisiaga.tools.convertToDate
import com.mamisiaga.tools.withDateFormat
import com.mamisiaga.viewmodel.AnakViewModel
import com.mamisiaga.viewmodelfactory.ViewModelFactory
import java.util.*


class TambahAnakActivity : AppCompatActivity(), View.OnClickListener,
    DatePickerDialog.OnDateSetListener {
    private lateinit var binding: ActivityTambahAnakBinding
    private lateinit var anakViewModel: AnakViewModel
    private lateinit var ibu: Ibu

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        binding = ActivityTambahAnakBinding.inflate(layoutInflater)

        setContentView(binding.root)

        Locale.setDefault(Locale("id", "ID"))

        ibu = intent.getParcelableExtra<Ibu>(HomeActivity.EXTRA_IBU) as Ibu

        editTextListener()
        setButtonEnabled()

        anakViewModel = ViewModelProvider(
            this,
            ViewModelFactory.AnakViewModelFactory(ibu.token)
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
            R.id.edittext_tgl_lahir -> {
                closeKeyboard()

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
        val golonganDarah = when (binding.radiogroupGolonganDarah.checkedRadioButtonId) {
            R.id.radiobutton_a -> "A"
            R.id.radiobutton_b -> "B"
            R.id.radiobutton_ab -> "AB"
            else -> "O"
        }
        val anak = Anak(
            null,
            binding.edittextNama.text.toString(),
            binding.edittextTglLahir.text.toString(),
            binding.edittextTempatLahir.text.toString(),
            golonganDarah
        )

        val dialog = Dialog(this)

        anakViewModel.addAnak(anak).observe(this) { resultResponse ->
            dialog.setContentView(R.layout.custom_dialog_memuat)
            dialog.setCanceledOnTouchOutside(false)
            dialog.setCancelable(false)

            when (resultResponse) {
                is ResultResponse.Loading -> {
                    dialog.show()
                }
                 is ResultResponse.Success -> {
                    dialog.dismiss()

                    setResult(TAMBAH_ANAK_RESPONSE_CODE)

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
    }

    private fun editTextListener() {
        binding.apply {
            edittextNama.addTextChangedListener(object : TextWatcher {
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

            edittextTempatLahir.addTextChangedListener(object : TextWatcher {
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

            edittextTglLahir.addTextChangedListener(object : TextWatcher {
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

            radiogroupGolonganDarah.setOnCheckedChangeListener { group, checkedId ->
                setButtonEnabled()
            }
        }
    }

    private fun setButtonEnabled() {
        binding.apply {
            val nama = edittextNama.text.toString()
            val tempatLahir = edittextTempatLahir.text.toString()
            val tglLahir = edittextNama.text.toString()
            val bbAnak = edittextNama.text.toString()
            val tbAnak = edittextNama.text.toString()
            val lkAnak = edittextNama.text.toString()

            buttonTambahAnak.isEnabled =
                nama.isNotEmpty() && tempatLahir.isNotEmpty() && tglLahir.isNotEmpty() &&
                        binding.radiogroupGolonganDarah.checkedRadioButtonId != -1
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

    companion object {
        const val TAMBAH_ANAK_RESPONSE_CODE = 101
        const val EXTRA_IBU = "extraIbu"
    }
}