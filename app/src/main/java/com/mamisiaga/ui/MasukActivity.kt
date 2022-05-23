package com.mamisiaga.ui

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.mamisiaga.R
import com.mamisiaga.`class`.Ibu
import com.mamisiaga.databinding.ActivityMasukBinding
import com.mamisiaga.repository.IbuPreference
import com.mamisiaga.tools.ResultResponse
import com.mamisiaga.tools.isConnected
import com.mamisiaga.tools.isEmailFormat
import com.mamisiaga.viewmodel.AutentikasiViewModel
import com.mamisiaga.viewmodel.IbuPreferenceViewModel
import com.mamisiaga.viewmodelfactory.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MasukActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMasukBinding
    private lateinit var ibuPreferenceViewModel: IbuPreferenceViewModel
    private lateinit var autentikasiViewModel: AutentikasiViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMasukBinding.inflate(layoutInflater)

        setContentView(binding.root)

        drawLayout()
        editTextListener()
        setButtonEnabled()

        autentikasiViewModel = ViewModelProvider(
            this,
            ViewModelFactory.AutentikasiViewModelFactory()
        )[AutentikasiViewModel::class.java]

        binding.layoutOffline.buttonMuatUlang.setOnClickListener(this)
        binding.buttonMasuk.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.button_muat_ulang -> {
                drawLayout()
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

    private fun seeMasukResponse() {
        val email = binding.edittextEmail.text.toString()
        val password = binding.edittextKataSandi.text.toString()

        //autentikasiViewModel.masuk(email, password)

        val dialog = Dialog(this)

        // When the ibu logs in
        autentikasiViewModel.masuk(email, password).observe(this) { resultResponse ->
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
                        ibuPreferenceViewModel = ViewModelProvider(
                            this,
                            ViewModelFactory.IbuPreferenceViewModelFactory(
                                IbuPreference.getInstance(
                                    dataStore
                                )
                            )
                        )[IbuPreferenceViewModel::class.java]

                        val ibu = Ibu(
                            resultResponse.data.masukResult.name,
                            resultResponse.data.masukResult.email,
                            resultResponse.data.masukResult.id,
                            true
                        )

                        ibuPreferenceViewModel.masuk(ibu)

                        val intent = Intent(this, HomeActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                        intent.putExtra(HomeActivity.EXTRA_IBU, ibu)

                        startActivity(intent)

                        finish()
                    }
                }
                is ResultResponse.Error -> {
                    dialog.dismiss()

                    when (resultResponse.error) {
                        "No Internet Connection" -> showFailure()
                        //else -> showMasukError(true)
                    }
                }
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
                    && password.isNotEmpty()
    }

    /*
    private fun showMasukError(isError: Boolean) {
        if (isError) binding.textviewMasukError.visibility = View.VISIBLE
        else binding.textviewMasukError.visibility = View.GONE
    }
     */

    private fun showFailure() {
        Toast.makeText(
            this@MasukActivity,
            getString(R.string.gagal_merespon_permintaan),
            Toast.LENGTH_SHORT
        ).show()

        //binding.buttonMasuk.isEnabled = enableButtonMasuk()
    }
}