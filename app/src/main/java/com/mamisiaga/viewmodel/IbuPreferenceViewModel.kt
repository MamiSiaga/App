package com.mamisiaga.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mamisiaga.`class`.Ibu
import com.mamisiaga.repository.IbuPreference
import kotlinx.coroutines.launch

class IbuPreferenceViewModel(private val ibuPreference: IbuPreference) : ViewModel() {
    fun getIbu(): LiveData<Ibu> = ibuPreference.getIbu().asLiveData()

    fun masuk(ibu: Ibu) {
        viewModelScope.launch {
            ibuPreference.masuk(ibu)
        }
    }

    fun keluar() {
        viewModelScope.launch {
            ibuPreference.keluar()
        }
    }
}