package com.mamisiaga.ui

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import com.mamisiaga.R
import com.mamisiaga.dataClass.Ibu
import com.mamisiaga.dataClass.Pertumbuhan
import com.mamisiaga.databinding.ActivityTambahEditPertumbuhanAnakBinding
import com.mamisiaga.tools.ResultResponse
import com.mamisiaga.viewmodel.PertumbuhanViewModel
import com.mamisiaga.viewmodelfactory.ViewModelFactory

class TambahEditPertumbuhanAnakActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityTambahEditPertumbuhanAnakBinding
    private lateinit var pertumbuhanViewModel: PertumbuhanViewModel
    private var isEdit = false
    private lateinit var ibu: Ibu
    private var pertumbuhan: Pertumbuhan? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        binding = ActivityTambahEditPertumbuhanAnakBinding.inflate(layoutInflater)

        setContentView(binding.root)

        ibu = intent.getParcelableExtra<Ibu>(EXTRA_IBU) as Ibu
        pertumbuhan = intent.getParcelableExtra<Pertumbuhan>(EXTRA_PERTUMBUHAN) as Pertumbuhan

        binding.textviewUsiaAnak.text = "${binding.textviewUsiaAnak.text}${pertumbuhan!!.age!!}"

        pertumbuhanViewModel = ViewModelProvider(
            this,
            ViewModelFactory.PertumbuhanViewModelFactory(ibu.token!!)
        )[PertumbuhanViewModel::class.java]

        if (pertumbuhan?.id != null) {
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
            buttonTambahSimpan.setOnClickListener(this@TambahEditPertumbuhanAnakActivity)
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.imagebutton_keluar -> {
                onBackPressed()
            }
            R.id.button_tambah_simpan -> {
                if (isEdit) {
                    pertumbuhan = Pertumbuhan(
                        pertumbuhan!!.id, pertumbuhan!!.childrenId,
                        null, pertumbuhan!!.age,
                        binding.edittextBeratBadan.text.toString().toInt(),
                        binding.edittextTinggiBadan.text.toString().toInt(),
                        binding.edittextLingkarKepala.text.toString().toInt()
                    )

                    seeEditPertumbuhanAnakResponse()
                } else {
                    pertumbuhan = Pertumbuhan(
                        null, pertumbuhan!!.childrenId,
                        null, pertumbuhan!!.age,
                        binding.edittextBeratBadan.text.toString().toInt(),
                        binding.edittextTinggiBadan.text.toString().toInt(),
                        binding.edittextLingkarKepala.text.toString().toInt()
                    )

                    seeAddPertumbuhanAnakResponse()
                }
            }
        }
    }

    private fun seeAddPertumbuhanAnakResponse() {
        val dialog = Dialog(this)

        pertumbuhanViewModel.addPertumbuhan(pertumbuhan!!).observe(this) { resultResponse ->
            dialog.setContentView(R.layout.custom_dialog_memuat)
            dialog.setCanceledOnTouchOutside(false)
            dialog.setCancelable(false)

            when (resultResponse) {
                is ResultResponse.Loading -> {
                    dialog.show()
                }
                is ResultResponse.Success -> {
                    dialog.dismiss()

                    setResult(TAMBAH_PERTUMBUHAN_ANAK_RESPONSE_CODE)

                    finish()
                }
                is ResultResponse.Error -> {
                    dialog.dismiss()

                    Toast.makeText(
                        this@TambahEditPertumbuhanAnakActivity,
                        showFailure(resultResponse.error),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun seeEditPertumbuhanAnakResponse() {
        val dialog = Dialog(this)

        pertumbuhanViewModel.editPertumbuhan(pertumbuhan!!).observe(this) { resultResponse ->
            dialog.setContentView(R.layout.custom_dialog_memuat)
            dialog.setCanceledOnTouchOutside(false)
            dialog.setCancelable(false)

            when (resultResponse) {
                is ResultResponse.Loading -> {
                    dialog.show()
                }
                is ResultResponse.Success -> {
                    dialog.dismiss()

                    setResult(EDIT_PERTUMBUHAN_ANAK_RESPONSE_CODE)

                    finish()
                }
                is ResultResponse.Error -> {
                    dialog.dismiss()

                    Toast.makeText(
                        this@TambahEditPertumbuhanAnakActivity,
                        showFailure(resultResponse.error),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
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

    private fun editTextListener() {
        binding.apply {
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
        else -> "Gagal melakukan perubahan data pertumbuhan."
    }

    private fun setButtonEnabled() {
        binding.apply {
            val bbAnak = edittextBeratBadan.text.toString()
            val tbAnak = edittextTinggiBadan.text.toString()
            val lkAnak = edittextLingkarKepala.text.toString()

            buttonTambahSimpan.isEnabled = bbAnak.isNotEmpty()
                    && tbAnak.isNotEmpty() && lkAnak.isNotEmpty()
        }
    }

    companion object {
        const val EXTRA_PERTUMBUHAN = "extra_pertumbuhan"
        const val EXTRA_IBU = "extra_ibu"
        const val TAMBAH_PERTUMBUHAN_ANAK_RESPONSE_CODE = 1
        const val EDIT_PERTUMBUHAN_ANAK_RESPONSE_CODE = 2
    }
}