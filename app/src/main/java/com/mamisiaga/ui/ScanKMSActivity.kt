package com.mamisiaga.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.mamisiaga.R
import com.mamisiaga.dataClass.Pertumbuhan
import com.mamisiaga.databinding.ActivityScanKmsBinding
import com.mamisiaga.tools.Classifier
import com.mamisiaga.tools.keepNumbers
import com.mamisiaga.tools.uriToFile
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
    private lateinit var bitmap: Bitmap
    private var isPhotoValid = 0

    private val cropActivityResultContracts = object : ActivityResultContract<Any?, Uri?>() {
        override fun createIntent(context: Context, input: Any?): Intent {
            return CropImage.activity()
                .getIntent(this@ScanKMSActivity)
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
            return CropImage.getActivityResult(intent)?.uri
        }
    }

    private val cropActivityResultLauncher =
        registerForActivityResult(cropActivityResultContracts) {
            it?.let {
                photo = it
                Picasso.with(this)
                    .load(photo)
                    .into(binding.imageviewGambar)
                setButtonEnabled()
            }
            val result = uriToFile(photo!!, this)
            bitmap = BitmapFactory.decodeFile(result.path)
        }

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
        binding.buttonTextRecognition.setOnClickListener(this)
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
                validateKMS(bitmap)
            }
            R.id.button_text_recognition -> {
                recognizeText(bitmap)
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
                        Toast.makeText(
                            this,
                            getString(R.string.ijin_akses_penyimpanan),
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }
                }
            }
        }
    }

    private fun validateKMS(bitmap: Bitmap) {
        classifier = Classifier(assets, mModelPath, mLabelPath, mInputSize)
        val result = classifier.recognizeImage(bitmap)
        val validity = result[0].title

        if (validity == "kms") {
            isPhotoValid = 1

            recognizeText(bitmap)
        } else {
            runOnUiThread {
                Toast.makeText(
                    this,
                    getString(R.string.gambar_bukan_kms),
                    Toast.LENGTH_SHORT
                ).show()
            }
            isPhotoValid = -1
        }
    }

    private fun recognizeText(bitmap: Bitmap) {
        when (isPhotoValid) {
            1 -> {
                val recognition = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
                val image = InputImage.fromBitmap(bitmap, 0)

                recognition.process(image)
                    .addOnSuccessListener {
                        val listPertumbuhan: ArrayList<Pertumbuhan> = ArrayList()
                        val text = keepNumbers(it.text)
                        val listBeratBadan: List<String> = text.split(" ")
                        var age = 0

                        val size = listBeratBadan.size

                        for (i in listBeratBadan) {
                            listPertumbuhan.add(
                                Pertumbuhan(
                                    null,
                                    1,
                                    null,
                                    age,
                                    i.toInt(),
                                    null,
                                    null
                                )
                            )

                            age += 1
                        }

                        startActivity(
                            Intent(
                                this@ScanKMSActivity,
                                HasilScanKMSActivity::class.java
                            ).putExtra(HasilScanKMSActivity.EXTRA_LIST_BERAT_BADAN, listPertumbuhan)
                        )

                        Toast.makeText(this, "Teks berhasil dideteksi", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { e ->
                        e.printStackTrace()
                        Toast.makeText(this, "Gagal mendeteksi teks", Toast.LENGTH_SHORT).show()
                    }
            }
            -1 -> {
                Toast.makeText(this, getString(R.string.gambar_bukan_kms), Toast.LENGTH_SHORT)
                    .show()
            }
            0 -> {
                Toast.makeText(this, "Cek validasi gambar terlebih dahulu!", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }

    // Pick image from gallery or camera
    private fun pickFromGallery() {
        cropActivityResultLauncher.launch(null)
//        CropImage.activity().start(this@ScanKMSActivity)
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
            buttonTextRecognition.isEnabled = photo != null
        }
    }

    companion object {
        private const val CAMERA_REQUEST = 100
        private const val STORAGE_REQUEST = 200
    }
}