package com.mamisiaga.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mamisiaga.usecase.UserUseCase

class UserViewModel(private val userUseCase: UserUseCase) : ViewModel() {

    fun getUser(token: String) = userUseCase.getUser(token).asLiveData()
}