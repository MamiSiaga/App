package com.mamisiaga.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mamisiaga.R
import com.mamisiaga.databinding.ActivityDetailImunisasiBinding
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executors
import javax.net.ssl.HttpsURLConnection

class DetailImunisasiActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityDetailImunisasiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailImunisasiBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.imagebuttonKeluar.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
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
}