package com.mamisiaga.ui

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import com.mamisiaga.R
import com.mamisiaga.dataClass.IbuDaftar
import com.mamisiaga.databinding.ActivityDaftarLanjutanBinding
import com.mamisiaga.tools.ResultResponse
import com.mamisiaga.tools.convertToDateString
import com.mamisiaga.tools.withDateFormatID
import com.mamisiaga.viewmodel.AutentikasiViewModel
import com.mamisiaga.viewmodelfactory.ViewModelFactory
import java.util.*

class DaftarLanjutanActivity : AppCompatActivity(), View.OnClickListener,
    DatePickerDialog.OnDateSetListener {
    private lateinit var binding: ActivityDaftarLanjutanBinding
    private lateinit var autentikasiViewModel: AutentikasiViewModel
    private lateinit var email: String

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding = ActivityDaftarLanjutanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Locale.setDefault(Locale("id", "ID"))

        setupViewModel()
        editTextListener()
        setButtonEnabled()

        email = intent.extras!!.getString(EXTRA_IBU_EMAIL, "extraIbuEmail")
        binding.textviewEmail.text = email

        binding.imagebuttonKeluar.setOnClickListener(this)
        binding.edittextTglLahir.setOnClickListener(this)
        binding.buttonDaftar.setOnClickListener(this)
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

                datePickerDialog.datePicker.maxDate = calendar.timeInMillis - 600000000000
                datePickerDialog.show()
            }
            R.id.button_daftar -> {
                val inputMethodManager =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(this.currentFocus?.windowToken, 0)

                var isRadioButtonNotChecked = false
                var isPasswordInvalid = false

                if (binding.radiogroupPertanyaanPosyandu.checkedRadioButtonId <= 0) {
                    isRadioButtonNotChecked = true
                    binding.radiobuttonTidak.error = "Pilih item"
                }

                if (binding.edittextKataSandi.text.toString() != binding.edittextKetikUlangKataSandi.text.toString()) {
                    isPasswordInvalid = true
                    binding.edittextKetikUlangKataSandi.error = "Kata sandi tidak sesuai"
                }

                if (!isRadioButtonNotChecked && !isPasswordInvalid) {
                    seeDaftarResponse()
                }
            }
        }
    }

    // Picking the selected date from the DatePicker
    override fun onDateSet(datePicker: DatePicker, year: Int, month: Int, day: Int) {
        val year = datePicker.year
        val month = datePicker.month + 1
        val day = datePicker.dayOfMonth

        binding.edittextTglLahir.setText("$day-$month-$year".withDateFormatID())
    }

    private fun setupViewModel() {
        autentikasiViewModel = ViewModelProvider(
            this,
            ViewModelFactory.AutentikasiViewModelFactory()
        )[AutentikasiViewModel::class.java]
    }

    private fun seeDaftarResponse() {
        val name = binding.edittextNama.text.toString()
        val password = binding.edittextKataSandi.text.toString()
        val passwordConfirm = binding.edittextKetikUlangKataSandi.text.toString()
        val placeOfBirth = binding.edittextTempatLahir.text.toString()
        val dateOfBirth = convertToDateString(binding.edittextTglLahir.text.toString())
        val profileType = "MOTHER"
        val isParticipatingPosyandu =
            when (binding.radiogroupPertanyaanPosyandu.checkedRadioButtonId) {
                R.id.radiobutton_ya -> true
                else -> false
            }

        val ibuDaftar =
            IbuDaftar(name, email, password, passwordConfirm, placeOfBirth, dateOfBirth, profileType)

        val dialog = Dialog(this)

        autentikasiViewModel.daftar(ibuDaftar).observe(this) { resultResponse ->
            dialog.setContentView(R.layout.custom_dialog_memuat)
            dialog.setCanceledOnTouchOutside(false)
            dialog.setCancelable(false)

            when (resultResponse) {
                is ResultResponse.Loading -> {
                    dialog.show()
                }
                is ResultResponse.Success -> {
                    dialog.dismiss()

                    if (resultResponse.data.message == null) {
                        Toast.makeText(
                            this@DaftarLanjutanActivity,
                            getString(R.string.pendaftaran_berhasil),
                            Toast.LENGTH_SHORT
                        ).show()

                        val intent = Intent(this, MasukActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                        startActivity(intent)
                    } else Toast.makeText(
                        this,
                        resultResponse.data.message, Toast.LENGTH_SHORT
                    ).show()
                }
                is ResultResponse.Error -> {
                    dialog.dismiss()

                    Log.e(
                        "DaftarLanjutanActivity",
                        "daftar lanjutan activity: ${resultResponse.error}"
                    )
                    Toast.makeText(
                        this,
                        showFailure(resultResponse.error), Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun editTextListener() {
        binding.apply {
            edittextNama.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    setButtonEnabled()
                }

                override fun afterTextChanged(p0: Editable?) {

                }

            })

            edittextTempatLahir.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    setButtonEnabled()
                }

                override fun afterTextChanged(p0: Editable?) {

                }

            })

            edittextTglLahir.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    setButtonEnabled()
                }

                override fun afterTextChanged(p0: Editable?) {

                }

            })

            edittextKataSandi.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    setButtonEnabled()
                }

                override fun afterTextChanged(p0: Editable?) {

                }

            })

            edittextKetikUlangKataSandi.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    setButtonEnabled()
                }

                override fun afterTextChanged(p0: Editable?) {

                }
            })

            radiogroupPertanyaanPosyandu.setOnCheckedChangeListener { _, _ ->
                setButtonEnabled()
            }
        }
    }

    private fun setButtonEnabled() {
        binding.apply {
            val nama = edittextNama.text.toString()
            val tempatLahir = edittextTempatLahir.text.toString()
            val tglLahir = edittextTglLahir.text.toString()
            val password = edittextKataSandi.text.toString()
            val passwordConfirm = edittextKetikUlangKataSandi.text.toString()

            buttonDaftar.isEnabled =
                nama.isNotEmpty() && tglLahir.isNotEmpty()
                        && password.isNotEmpty() && passwordConfirm.isNotEmpty() &&
                        password.length >= 8 && password == passwordConfirm &&
                        binding.radiogroupPertanyaanPosyandu.checkedRadioButtonId != -1
        }
    }

    private fun showFailure(text: String) =
        when (text) {
            "No Internet Connection" -> getString(R.string.gagal_merespon_permintaan)
            else -> "Pendaftaran gagal."
        }

    private fun closeKeyboard() {
        val view = this.currentFocus

        if (view != null) {
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    companion object {
        const val EXTRA_IBU_EMAIL = "extraIbuEmail"
    }
}