package com.mamisiaga.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.mamisiaga.`class`.Ibu
import com.mamisiaga.databinding.ActivityMainBinding
import com.mamisiaga.repository.IbuPreference
import com.mamisiaga.viewmodel.IbuPreferenceViewModel
import com.mamisiaga.viewmodelfactory.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var ibuPreferenceViewModel: IbuPreferenceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        //getLoginIbu()

        val ibu = Ibu("aaaaa", "mama@mail.com", "id", true)

        startActivity(
            Intent(
                this@MainActivity,
                HomeActivity::class.java
            ).putExtra(HomeActivity.EXTRA_IBU, ibu)
        )

        finish()
    }

    private fun getLoginIbu() {
        ibuPreferenceViewModel = ViewModelProvider(
            this,
            ViewModelFactory.IbuPreferenceViewModelFactory(IbuPreference.getInstance(dataStore))
        )[IbuPreferenceViewModel::class.java]

        ibuPreferenceViewModel.getIbu().observe(this) { ibu ->
            // When the ibu does not log out
            if (ibu.isMasuk) {
                startActivity(
                    Intent(
                        this@MainActivity,
                        HomeActivity::class.java
                    ).putExtra(HomeActivity.EXTRA_IBU, ibu)
                )

                finish()
            }
            // Otherwise
            else {
                startActivity(Intent(this, DaftarActivity::class.java))

                finish()
            }
        }
    }
}