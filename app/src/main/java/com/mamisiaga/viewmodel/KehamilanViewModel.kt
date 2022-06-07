package com.mamisiaga.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mamisiaga.dataClass.Kehamilan
import com.mamisiaga.usecase.KehamilanUseCase

class KehamilanViewModel(private val kehamilanUseCase: KehamilanUseCase) :
    ViewModel() {
    fun getKehamilan(id: Int) = kehamilanUseCase.getKehamilan(id).asLiveData()

    fun addKehamilan(kehamilan: Kehamilan) = kehamilanUseCase.addKehamilan(kehamilan).asLiveData()
}