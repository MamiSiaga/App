package com.mamisiaga.ui

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.mamisiaga.R
import com.mamisiaga.`class`.IbuDaftar
import com.mamisiaga.databinding.ActivityDaftarLanjutanBinding
import com.mamisiaga.tools.ResultResponse
import com.mamisiaga.tools.withDateFormat
import com.mamisiaga.viewmodel.AutentikasiViewModel
import com.mamisiaga.viewmodelfactory.ViewModelFactory
import java.util.*

class DaftarLanjutanActivity : AppCompatActivity(), View.OnClickListener,
    DatePickerDialog.OnDateSetListener {
    private lateinit var binding: ActivityDaftarLanjutanBinding
    private lateinit var autentikasiViewModel: AutentikasiViewModel
    private lateinit var email: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDaftarLanjutanBinding.inflate(layoutInflater)

        setContentView(binding.root)

        autentikasiViewModel = ViewModelProvider(
            this,
            ViewModelFactory.AutentikasiViewModelFactory()
        )[AutentikasiViewModel::class.java]

        email = intent.extras!!.getString(EXTRA_IBU_EMAIL, "extraIbuEmail")

        Locale.setDefault(Locale("id", "ID"))

        editTextListener()
        setButtonEnabled()

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
                val calendar = Calendar.getInstance()
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val day = calendar.get(Calendar.DAY_OF_MONTH)
                val datePickerDialog = DatePickerDialog(this, this, year, month, day)

                datePickerDialog.datePicker.maxDate = calendar.timeInMillis
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

        binding.edittextTglLahir.setText("$day-$month-$year".withDateFormat())
    }

    private fun seeDaftarResponse() {
        val name = binding.edittextNama.text.toString()
        val password = binding.edittextKataSandi.text.toString()
        val dateOfBirth = binding.edittextTglLahir.text.toString()
        val type = "ibu"
        val isParticipatingPosyandu =
            when (binding.radiogroupPertanyaanPosyandu.checkedRadioButtonId) {
                R.id.radiobutton_ya -> true
                else -> false
            }

        val ibuDaftar =
            IbuDaftar(name, email, password, dateOfBirth, type, isParticipatingPosyandu, "a")

        val dialog = Dialog(this)

        autentikasiViewModel.daftar(ibuDaftar).observe(this) { resultResponse ->
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
                        val intent = Intent(this, MasukActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                        startActivity(intent)

                        finish()
                    }
                }
                is ResultResponse.Error -> {
                    dialog.dismiss()

                    when (resultResponse.error) {
                        "No Internet Connection" -> showFailure()
                        //else -> showEmailTakenSign(true)
                    }
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

            edittextNik.addTextChangedListener(object : TextWatcher {
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
        }
    }

    private fun setButtonEnabled() {
        binding.apply {
            val nama = edittextNama.text.toString()
            val nik = edittextNik.text.toString()
            val tglLahir = edittextTglLahir.text.toString()
            val password = edittextKataSandi.text.toString()
            val passwordConfirm = edittextKetikUlangKataSandi.text.toString()

            buttonDaftar.isEnabled =
                nama.isNotEmpty() && nik.isNotEmpty() &&
                        tglLahir.isNotEmpty() && password.isNotEmpty() &&
                        passwordConfirm.isNotEmpty() && password.equals(passwordConfirm) &&
                        binding.radiogroupPertanyaanPosyandu.checkedRadioButtonId != -1
        }
    }

    private fun showFailure() {
        Toast.makeText(
            this@DaftarLanjutanActivity,
            getString(R.string.gagal_merespon_permintaan),
            Toast.LENGTH_SHORT
        ).show()

        //binding.buttonDaftar.isEnabled = enableButtonDaftar()
    }

    companion object {
        const val EXTRA_IBU_EMAIL = "extraIbuEmail"
    }
}