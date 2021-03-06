package com.mamisiaga.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mamisiaga.dataClass.Anak
import com.mamisiaga.usecase.AnakUseCase

class AnakViewModel(private val anakUseCase: AnakUseCase) :
    ViewModel() {
    fun getAnak(id: Int) = anakUseCase.getAnak(id).asLiveData()

    fun addAnak(anak: Anak) = anakUseCase.addAnak(anak).asLiveData()

    fun editAnak(anak: Anak) = anakUseCase.editAnak(anak).asLiveData()

    //fun deleteAnak(id: String) = anakUseCase.getAnak(id).asLiveData()
}
