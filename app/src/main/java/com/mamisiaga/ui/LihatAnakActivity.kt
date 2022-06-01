package com.mamisiaga.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mamisiaga.dataClass.Anak
import com.mamisiaga.databinding.ActivityLihatAnakBinding
import com.mamisiaga.tools.isConnected

class LihatAnakActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLihatAnakBinding
    private lateinit var anak: Anak

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLihatAnakBinding.inflate(layoutInflater)

        setContentView(binding.root)

        anak = intent.getParcelableExtra<Anak>(EXTRA_ANAK) as Anak

        binding.textviewNamaAnak.text = anak.name

        binding.imagebuttonKeluar.setOnClickListener { onBackPressed() }
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

    companion object {
        const val EXTRA_ANAK = "extra_anak"
    }
}