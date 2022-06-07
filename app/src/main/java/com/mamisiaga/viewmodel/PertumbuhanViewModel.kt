package com.mamisiaga.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mamisiaga.dataClass.Pertumbuhan
import com.mamisiaga.usecase.PertumbuhanUseCase

class PertumbuhanViewModel(private val pertumbuhanUseCase: PertumbuhanUseCase) :
    ViewModel() {
    fun getPertumbuhan(id: Int) = pertumbuhanUseCase.getPertumbuhan(id).asLiveData()

    fun addPertumbuhan(pertumbuhan: Pertumbuhan) =
        pertumbuhanUseCase.addPertumbuhan(pertumbuhan).asLiveData()

    fun editPertumbuhan(pertumbuhan: Pertumbuhan) =
        pertumbuhanUseCase.editPertumbuhan(pertumbuhan).asLiveData()
}