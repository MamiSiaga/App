package com.mamisiaga.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mamisiaga.dataClass.IbuDaftar
import com.mamisiaga.usecase.AutentikasiUseCase

class AutentikasiViewModel(private val autentikasiUseCase: AutentikasiUseCase) :
    ViewModel() {
    fun daftar(ibuDaftar: IbuDaftar) =
        autentikasiUseCase.daftar(ibuDaftar).asLiveData()

    fun masuk(email: String, kataSandi: String) =
        autentikasiUseCase.masuk(email, kataSandi).asLiveData()
}