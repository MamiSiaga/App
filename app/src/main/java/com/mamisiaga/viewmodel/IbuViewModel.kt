package com.mamisiaga.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mamisiaga.usecase.IbuUseCase

class IbuViewModel(private val ibuUseCase: IbuUseCase) :
    ViewModel() {
    fun getIbuById(id: Int) = ibuUseCase.getIbuById(id).asLiveData()

    fun getIbuByToken() = ibuUseCase.getIbuByToken().asLiveData()
}