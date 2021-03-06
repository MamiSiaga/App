package com.mamisiaga.ui

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.mamisiaga.R
import com.mamisiaga.dataClass.Ibu
import com.mamisiaga.dataClass.LoginModel
import com.mamisiaga.databinding.ActivityMasukBinding
import com.mamisiaga.repository.LoginPreference
import com.mamisiaga.tools.ResultResponse
import com.mamisiaga.tools.isConnected
import com.mamisiaga.tools.isEmailFormat
import com.mamisiaga.viewmodel.AutentikasiViewModel
import com.mamisiaga.viewmodel.IbuPreferenceViewModel
import com.mamisiaga.viewmodel.LoginPreferenceViewModel
import com.mamisiaga.viewmodelfactory.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MasukActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMasukBinding
    private lateinit var autentikasiViewModel: AutentikasiViewModel
    private lateinit var login: LoginPreferenceViewModel
    private var isPasswordShown = false

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding = ActivityMasukBinding.inflate(layoutInflater)
        setContentView(binding.root)

        drawLayout()
        editTextListener()
        setButtonEnabled()
        setupViewModel()

        binding.layoutOffline.buttonMuatUlang.setOnClickListener(this)
        binding.textviewLupaKataSandi.setOnClickListener(this)
        binding.buttonMasuk.setOnClickListener(this)
        binding.textviewDaftar.setOnClickListener(this)
        binding.textviewShowHidePass.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.button_muat_ulang -> {
                drawLayout()
            }
            R.id.textview_lupa_kata_sandi -> {
                startActivity(Intent(this@MasukActivity, LupaKataSandiActivity::class.java))
            }
            R.id.button_masuk -> {
                val inputMethodManager =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(this.currentFocus?.windowToken, 0)

                seeMasukResponse()
            }
            R.id.textview_daftar -> {
                startActivity(Intent(this, DaftarActivity::class.java))

                finish()
            }
            R.id.textview_show_hide_pass -> {
                setShowHidePass()
            }
        }
    }

    private fun setupViewModel() {
        autentikasiViewModel = ViewModelProvider(
            this,
            ViewModelFactory.AutentikasiViewModelFactory()
        )[AutentikasiViewModel::class.java]

        login = ViewModelProvider(
            this,
            ViewModelFactory.LoginPreferenceViewModelFactory(
                LoginPreference.getInstance(dataStore)
            )
        )[LoginPreferenceViewModel::class.java]
    }

    private fun seeMasukResponse() {
        val email = binding.edittextEmail.text.toString()
        val password = binding.edittextKataSandi.text.toString()

        val dialog = Dialog(this)

        // When the ibu logs in
        autentikasiViewModel.masuk(email, password).observe(this) { resultResponse ->
            dialog.setContentView(R.layout.custom_dialog_memuat)
            dialog.setCanceledOnTouchOutside(false)
            dialog.setCancelable(false)

            when (resultResponse) {
                is ResultResponse.Loading -> {
                    dialog.show()
                }
                is ResultResponse.Success -> {
                    dialog.dismiss()

                    val token = resultResponse.data.masukData.token
                    login.saveLogin(
                        LoginModel(
                            token,
                            isMasuk = true
                        )
                    )
                    val intent = Intent(this, HomeActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK

                    startActivity(intent.putExtra(HomeActivity.EXTRA_IBU, Ibu(null, token, true)))
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

    private fun setShowHidePass(){
        binding.apply {
            if(!isPasswordShown){
                edittextKataSandi.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                textviewShowHidePass.text = getString(R.string.sembunyikan)
                isPasswordShown = true
            } else {
                edittextKataSandi.transformationMethod =
                    PasswordTransformationMethod.getInstance()
                textviewShowHidePass.text = getString(R.string.tampilkan)
                isPasswordShown = false
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

            edittextKataSandi.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    setButtonEnabled()
                    setVisibilityShowHide()
                }

                override fun afterTextChanged(p0: Editable?) {

                }

            })
        }
    }

    private fun setButtonEnabled() {
        val email = binding.edittextEmail.text.toString()
        val password = binding.edittextKataSandi.text.toString()

        binding.buttonMasuk.isEnabled =
            email.isNotEmpty() && isEmailFormat(email)
                    && password.isNotEmpty() && password.length >= 8
    }

    private fun setVisibilityShowHide(){
        val password = binding.edittextKataSandi.text
        if (!(password != null && password.toString().isNotEmpty() && password.length >= 8)) {
            binding.textviewShowHidePass.visibility = View.GONE
        } else {
            binding.textviewShowHidePass.visibility = View.VISIBLE
        }
    }

    private fun showFailure(error: String) = when (error) {
        "No Internet Connection" -> getString(R.string.gagal_merespon_permintaan)
        else -> getString(R.string.email_atau_kata_sandi_salah)
    }
}