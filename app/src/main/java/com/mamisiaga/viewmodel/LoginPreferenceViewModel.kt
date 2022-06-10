package com.mamisiaga.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mamisiaga.dataClass.LoginModel
import com.mamisiaga.repository.LoginPreference
import kotlinx.coroutines.launch

class LoginPreferenceViewModel (private val pref: LoginPreference) : ViewModel() {

    fun getLogin(): LiveData<LoginModel> {
        return pref.getLogin().asLiveData()
    }

    fun saveLogin(user: LoginModel){
        viewModelScope.launch{
            pref.saveLogin(user)
        }
    }

    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }
}