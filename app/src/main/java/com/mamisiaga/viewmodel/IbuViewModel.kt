package com.mamisiaga.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mamisiaga.usecase.IbuUseCase

class IbuViewModel(private val ibuUseCase: IbuUseCase) :
    ViewModel() {
    fun getIbu() = ibuUseCase.getIbu().asLiveData()
}