package com.mamisiaga.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.mamisiaga.R
import com.mamisiaga.databinding.ActivityScanKmsBinding
import com.mamisiaga.tools.Classifier
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage


class ScanKMSActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityScanKmsBinding
    private lateinit var classifier: Classifier
    private val mModelPath = "kms_validation_model.tflite"
    private val mLabelPath = "label.txt"
    private val mInputSize = 150
    private lateinit var cameraPermission: Array<String>
    private lateinit var storagePermission: Array<String>
    private var photo: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        binding = ActivityScanKmsBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setButtonEnabled()

        cameraPermission =
            arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        storagePermission = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)

        binding.buttonAmbilGambar.setOnClickListener(this)
        binding.imagebuttonKeluar.setOnClickListener(this)
        binding.buttonLanjut.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.imagebutton_keluar -> {
                onBackPressed()
            }
            R.id.button_ambil_gambar -> {
                if (!checkCameraPermission()) {
                    requestCameraPermission()
                    requestStoragePermission()
                } else {
                    pickFromGallery()
                }
            }
            R.id.button_lanjut -> {
                validateKMS()
            }
        }
    }

    // Requesting camera and gallery permission if not given
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            CAMERA_REQUEST -> {
                if (grantResults.isNotEmpty()) {
                    val cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
                    val writeStorageaccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED
                    if (cameraAccepted && writeStorageaccepted) {
                        pickFromGallery()
                    } else {
                        Toast.makeText(
                            this,
                            getString(R.string.ijin_akses_kamera_dan_penyimpanan),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
            STORAGE_REQUEST -> {
                if (grantResults.isNotEmpty()) {
                    val writeStorageaccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
                    if (writeStorageaccepted) {
                        pickFromGallery()
                    } else {
                        Toast.makeText(this, getString(R.string.ijin_akses_penyimpanan), Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {
                photo = result.uri

                Picasso.with(this).load(photo).into(binding.imageviewGambar)

                setButtonEnabled()
            }
        }
    }

    private fun validateKMS() {
        classifier = Classifier(assets, mModelPath, mLabelPath, mInputSize)

        val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, photo)
        val result = classifier.recognizeImage(bitmap)
        val validity = result[0].title

        if (validity == "kms") {
            runOnUiThread {
                Toast.makeText(
                    this,
                    "KMS",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            runOnUiThread {
                Toast.makeText(
                    this,
                    getString(R.string.gambar_bukan_kms),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun recognizeText() {

    }

    // Pick image from gallery or camera
    private fun pickFromGallery() {
        CropImage.activity().start(this@ScanKMSActivity)
    }

    // Request camera permission
    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(this@ScanKMSActivity, cameraPermission, CAMERA_REQUEST)
    }

    // Request gallery permission
    private fun requestStoragePermission() {
        ActivityCompat.requestPermissions(this@ScanKMSActivity, storagePermission, STORAGE_REQUEST)
    }

    // Check camera permissions
    private fun checkCameraPermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
        val result1 = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
        return result && result1
    }

    private fun setButtonEnabled() {
        binding.apply {
            buttonLanjut.isEnabled = photo != null
        }
    }

    companion object {
        private const val CAMERA_REQUEST = 100
        private const val STORAGE_REQUEST = 200
    }
}