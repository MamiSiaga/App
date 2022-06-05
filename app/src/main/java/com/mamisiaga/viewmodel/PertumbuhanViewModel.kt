package com.mamisiaga.viewmodel

import androidx.lifecycle.ViewModel
import com.mamisiaga.usecase.AnakUseCase
import com.mamisiaga.usecase.PertumbuhanUseCase

class PertumbuhanViewModel(private val pertumbuhanUseCase: PertumbuhanUseCase) :
    ViewModel() {
}