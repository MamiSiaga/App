package com.mamisiaga.ui

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import com.mamisiaga.R
import com.mamisiaga.databinding.ActivityLupaKataSandiBinding
import com.mamisiaga.tools.isConnected
import com.mamisiaga.tools.isEmailFormat
import com.mamisiaga.viewmodel.AutentikasiViewModel
import com.mamisiaga.viewmodelfactory.ViewModelFactory

class LupaKataSandiActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityLupaKataSandiBinding
    private lateinit var autentikasiViewModel: AutentikasiViewModel

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        binding = ActivityLupaKataSandiBinding.inflate(layoutInflater)

        setContentView(binding.root)

        autentikasiViewModel = ViewModelProvider(
            this,
            ViewModelFactory.AutentikasiViewModelFactory()
        )[AutentikasiViewModel::class.java]

        drawLayout()
        editTextListener()
        setButtonEnabled()

        binding.layoutOffline.buttonMuatUlang.setOnClickListener(this)
        binding.buttonLanjut.setOnClickListener(this)
        binding.buttonLanjut.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.button_muat_ulang -> {
                drawLayout()
            }
            R.id.button_lanjut -> {
                seeCekEmailResponse()
            }
        }
    }

    private fun seeCekEmailResponse() {
        val email = binding.edittextEmail.text.toString()

        val dialog = Dialog(this)

        /*autentikasiViewModel.cekEmailTersedia(email).observe(this) { resultResponse ->
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
                        startActivity(
                            Intent(
                                this@LupaKataSandiActivity,
                                EditKataSandiActivity::class.java
                            ).putExtra(DaftarLanjutanActivity.EXTRA_IBU_EMAIL, email)
                        )
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
            edittextEmail.addTextChangedListener(object : TextWatcher {
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
        val email = binding.edittextEmail.text.toString()

        binding.buttonLanjut.isEnabled =
            email.isNotEmpty() && isEmailFormat(email)
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
}