package com.mamisiaga.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import com.mamisiaga.R
import com.mamisiaga.databinding.ActivityDaftarBinding
import com.mamisiaga.tools.isConnected
import com.mamisiaga.tools.isEmailFormat
import com.mamisiaga.viewmodel.AutentikasiViewModel
import com.mamisiaga.viewmodelfactory.ViewModelFactory

class DaftarActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityDaftarBinding

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding = ActivityDaftarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        drawLayout()
        editTextListener()
        setButtonEnabled()

        binding.layoutOffline.buttonMuatUlang.setOnClickListener(this)
        binding.buttonLanjut.setOnClickListener(this)
        binding.textviewMasuk.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.button_muat_ulang -> {
                drawLayout()
            }
            R.id.button_lanjut -> {
                val email = binding.edittextEmail.text.toString()
                startActivity(
                    Intent(
                        this@DaftarActivity,
                        DaftarLanjutanActivity::class.java
                    ).putExtra(DaftarLanjutanActivity.EXTRA_IBU_EMAIL, email)
                )
            }
            R.id.textview_masuk -> {
                startActivity(Intent(this, MasukActivity::class.java))
                finish()
            }
        }
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
}