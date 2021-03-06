package com.mamisiaga.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.mamisiaga.dataClass.Ibu
import com.mamisiaga.databinding.ActivityMainBinding
import com.mamisiaga.repository.LoginPreference
import com.mamisiaga.viewmodel.LoginPreferenceViewModel
import com.mamisiaga.viewmodelfactory.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var loginViewModel: LoginPreferenceViewModel

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getLoginIbu()

        /*
        val ibu = Ibu("aaaaa", "mama@mail.com", "id", true)

        startActivity(
            Intent(
                this@MainActivity,
                HomeActivity::class.java
            ).putExtra(HomeActivity.EXTRA_IBU, ibu)
        )

        finish()
         */

    }

    private fun getLoginIbu() {
        loginViewModel = ViewModelProvider(
            this,
            ViewModelFactory.LoginPreferenceViewModelFactory(LoginPreference.getInstance(dataStore))
        )[LoginPreferenceViewModel::class.java]

        loginViewModel.getLogin().observe(this) {
            if (it.isMasuk) {
                startActivity(
                    Intent(
                        this@MainActivity,
                        HomeActivity::class.java
                    ).putExtra(HomeActivity.EXTRA_IBU, Ibu(null, it.token, it.isMasuk))
                )

                finish()
            } else {
                startActivity(Intent(this, DaftarActivity::class.java))

                finish()
            }
        }

//        ibuPreferenceViewModel = ViewModelProvider(
//            this,
//            ViewModelFactory.IbuPreferenceViewModelFactory(IbuPreference.getInstance(dataStore))
//        )[IbuPreferenceViewModel::class.java]

//        ibuPreferenceViewModel.getIbu().observe(this) { ibu ->
//            // When the ibu does not log out
//            if (ibu.isMasuk) {
//                startActivity(
//                    Intent(
//                        this@MainActivity,
//                        HomeActivity::class.java
//                    ).putExtra(HomeActivity.EXTRA_IBU, ibu)
//                )
//
//                finish()
//            }
//            // Otherwise
//            else {
//                startActivity(Intent(this, DaftarActivity::class.java))
//
//                finish()
//            }
//        }
    }
}