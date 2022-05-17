package com.mamisiaga.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mamisiaga.usecase.AnakUseCase

class AnakViewModel(private val anakUseCase: AnakUseCase) :
    ViewModel() {
    fun getAnak() = anakUseCase.getAnak().asLiveData()
}
