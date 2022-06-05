package com.mamisiaga.ui

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mamisiaga.R
import com.mamisiaga.dataClass.Ibu
import com.mamisiaga.databinding.ActivityHomeBinding
import com.mamisiaga.viewmodel.IbuViewModel
import com.mamisiaga.viewmodelfactory.ViewModelFactory

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var ibu: Ibu

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding = ActivityHomeBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_home)
        navView.setupWithNavController(navController)
    }

    companion object {
        const val EXTRA_IBU = "extraIbu"
    }
}