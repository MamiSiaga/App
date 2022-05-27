package com.mamisiaga.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mamisiaga.R
import com.mamisiaga.databinding.ActivityDetailImunisasiBinding
import com.mamisiaga.tools.ResultResponse
import com.mamisiaga.tools.isConnected
import com.mamisiaga.viewmodel.ImunisasiViewModel
import com.mamisiaga.viewmodelfactory.ViewModelFactory
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executors
import javax.net.ssl.HttpsURLConnection

class DetailImunisasiActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityDetailImunisasiBinding
    private lateinit var imunisasiViewModel: ImunisasiViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailImunisasiBinding.inflate(layoutInflater)

        setContentView(binding.root)

        imunisasiViewModel = ViewModelProvider(
            this,
            ViewModelFactory.ImunisasiViewModelFactory(applicationContext)
        )[ImunisasiViewModel::class.java]

        getSertifikatImunisasiResponse()

        binding.layoutOffline.buttonMuatUlang.setOnClickListener(this)
        binding.imagebuttonKeluar.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.button_muat_ulang -> {
                drawLayout()
            }
            R.id.imagebutton_keluar -> {
                onBackPressed()
            }
        }
    }

    private fun displayPDF() {
        val pdfUrl = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf"
        val myExecutor = Executors.newSingleThreadExecutor()
        val myHandler = Handler(Looper.getMainLooper())
        var inputStream: InputStream? = null
        var httpURLConnection: HttpURLConnection?

        myExecutor.execute {
            // Do something in background (back-end process)
            try {
                val url = URL(pdfUrl)
                httpURLConnection = url.openConnection() as HttpsURLConnection

                inputStream = BufferedInputStream(httpURLConnection?.inputStream)

                binding.pdfview.fromStream(inputStream).load()
            } catch (e: IOException) {

            }
        }

        myHandler.post {
            // Do something in UI (front-end process)
        }
    }

    private fun getSertifikatImunisasiResponse() {
        /*imunisasiViewModel.getSertifikatImunisasiResponse("anak1", "imunisasi1").observe(this) { resultResponse ->
            when (resultResponse) {
                is ResultResponse.Loading -> {
                    showLoadingSign(true)
                }
                is ResultResponse.Success -> {
                    showLoadingSign(false)

                    //...
                }
                is ResultResponse.Error -> {
                    showLoadingSign(false)

                    when (resultResponse.error) {
                        "No Internet Connection" -> drawLayout()
                        //else -> showMasukError(true)
                    }
                }
            }
        }
         */
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

    private fun showLoadingSign(isLoading: Boolean) {
        if (isLoading) {
            binding.layoutMemuat.layoutMemuat.visibility = View.VISIBLE
            binding.layoutOnline.visibility = View.GONE
        } else {
            binding.layoutMemuat.layoutMemuat.visibility = View.GONE
            binding.layoutOnline.visibility = View.VISIBLE
        }
    }
}