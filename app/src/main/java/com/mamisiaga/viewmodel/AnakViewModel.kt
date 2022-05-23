package com.mamisiaga.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mamisiaga.`class`.AnakEdit
import com.mamisiaga.`class`.AnakTambah
import com.mamisiaga.usecase.AnakUseCase

class AnakViewModel(private val anakUseCase: AnakUseCase) :
    ViewModel() {
    fun getAnak(id: String) = anakUseCase.getAnak(id).asLiveData()

    fun addAnak(anakTambah: AnakTambah) = anakUseCase.addAnak(anakTambah).asLiveData()

    fun editAnak(anakEdit: AnakEdit) = anakUseCase.editAnak(anakEdit).asLiveData()

    fun deleteAnak(id: String) = anakUseCase.getAnak(id).asLiveData()
}
