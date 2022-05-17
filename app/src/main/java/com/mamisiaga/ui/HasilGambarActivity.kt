package com.mamisiaga.ui

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.mamisiaga.R
import com.mamisiaga.databinding.ActivityHasilGambarBinding
import java.io.File

class HasilGambarActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityHasilGambarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHasilGambarBinding.inflate(layoutInflater)

        setContentView(binding.root)

        // ambil intent
        setToViewGambar(intent)

        binding.buttonAmbilUlang.setOnClickListener(this)
        binding.imagebuttonKeluar.setOnClickListener(this)
        binding.buttonSimpan.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.imagebutton_keluar -> {
                onBackPressed()
            }
            R.id.button_ambil_ulang -> {
                val intent = Intent(this, KameraActivity::class.java)

                launcherIntentCameraX.launch(intent)

                finish()
            }
            R.id.button_simpan -> {
                //...

                finish()
            }
        }
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            setToViewGambar(it.data)
        }
    }

    private fun setToViewGambar(intent: Intent?) {
        val photo = intent?.getSerializableExtra("picture") as File
        val result = BitmapFactory.decodeFile(photo.path)

        binding.imageviewGambar.setImageBitmap(result)
    }

    companion object {
        const val CAMERA_X_RESULT = 200
    }
}