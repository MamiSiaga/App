package com.mamisiaga.ui

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import com.mamisiaga.R
import com.mamisiaga.databinding.ActivityEditKataSandiBinding
import com.mamisiaga.tools.isConnected
import com.mamisiaga.viewmodel.AutentikasiViewModel
import com.mamisiaga.viewmodelfactory.ViewModelFactory

class EditKataSandiActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityEditKataSandiBinding
    private lateinit var autentikasiViewModel: AutentikasiViewModel
    private lateinit var email: String

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        binding = ActivityEditKataSandiBinding.inflate(layoutInflater)

        setContentView(binding.root)

        email = intent.extras!!.getString(DaftarLanjutanActivity.EXTRA_IBU_EMAIL, "extraIbuEmail")

        autentikasiViewModel = ViewModelProvider(
            this,
            ViewModelFactory.AutentikasiViewModelFactory()
        )[AutentikasiViewModel::class.java]

        drawLayout()
        editTextListener()
        setButtonEnabled()

        binding.layoutOffline.buttonMuatUlang.setOnClickListener(this)
        binding.buttonSimpan.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.button_muat_ulang -> {
                drawLayout()
            }
            R.id.button_lanjut -> {
                seeEditKataSandiResponse()
            }
        }
    }

    private fun seeEditKataSandiResponse() {
        val email = binding.edittextKataSandi.text.toString()

        val dialog = Dialog(this)

        /*autentikasiViewModel.editKataSandi(email, password).observe(this) { resultResponse ->
            dialog.setContentView(R.layout.custom_dialog_memuat)
            dialog.setCanceledOnTouchOutside(false)
            dialog.setCancelable(false)

            when (resultResponse) {
                is ResultResponse.Loading -> {
                    dialog.show()
                }
                is ResultResponse.Success -> {
                    dialog.dismiss()

                    if (!resultResponse.data.error) {
                        val intent = Intent(this, DaftarActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                        startActivity(intent)

                        finish()
                    }
                }
                is ResultResponse.Error -> {
                    dialog.dismiss()

                    Toast.makeText(
                        this@LupaKataSandiActivity,
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
            edittextKataSandi.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    setButtonEnabled()
                    setVisibilityShowHide(tvShowHidePass, edittextKataSandi)
                }

                override fun afterTextChanged(p0: Editable?) {

                }

            })

            edittextKetikUlangKataSandi.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    setButtonEnabled()
                    setVisibilityShowHide(tvShowHidePassConfirm, edittextKetikUlangKataSandi)
                }

                override fun afterTextChanged(p0: Editable?) {

                }
            })
        }
    }

    private fun setButtonEnabled() {
        binding.apply {

            val password = edittextKataSandi.text.toString()
            val passwordConfirm = edittextKetikUlangKataSandi.text.toString()

            buttonSimpan.isEnabled = password.isNotEmpty() &&
                    passwordConfirm.isNotEmpty() && password == passwordConfirm
        }
    }

    private fun setVisibilityShowHide(textView: TextView, editText: EditText) {
        val password = editText.text
        if (!(password != null && password.toString().isNotEmpty() && password.length >= 8)) {
            textView.visibility = View.GONE
        } else {
            textView.visibility = View.VISIBLE
        }
    }

    private fun drawLayout() {
        // Checking Internet connection
        if (isConnected(applicationContext)) {
            binding.layoutOnline.visibility = View.VISIBLE
            binding.layoutOffline.layoutOffline.visibility = View.GONE
        } else {
            binding.layoutOnline.visibility = View.GONE
            binding.layoutOffline.layoutOffline.visibility = View.VISIBLE
        }
    }

    private fun showFailure(error: String) = when (error) {
        "No Internet Connection" -> getString(R.string.gagal_merespon_permintaan)
        else -> ""
    }

    companion object {
        const val EXTRA_IBU_EMAIL = "extraIbuEmail"
    }
}