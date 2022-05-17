package com.mamisiaga.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mamisiaga.R
import com.mamisiaga.databinding.ActivityDaftarBinding
import com.mamisiaga.tools.isConnected
import com.mamisiaga.tools.isEmailFormat

class DaftarActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityDaftarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDaftarBinding.inflate(layoutInflater)

        setContentView(binding.root)

        drawLayout()
        editTextListener()
        setButtonEnabled()

        binding.layoutOffline.buttonMuatUlang.setOnClickListener(this)
        binding.buttonLanjut.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.button_muat_ulang -> {
                drawLayout()
            }
            R.id.button_lanjut -> {
                startActivity(
                    Intent(
                        this@DaftarActivity,
                        DaftarLanjutanActivity::class.java
                    ).putExtra(DaftarLanjutanActivity.EXTRA_IBU_EMAIL, "mama@mail.com")
                )
            }
            R.id.textview_masuk -> {
                startActivity(Intent(this, MasukActivity::class.java))

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
}