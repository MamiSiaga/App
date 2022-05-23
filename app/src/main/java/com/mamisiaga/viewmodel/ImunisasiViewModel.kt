package com.mamisiaga.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mamisiaga.usecase.ImunisasiUseCase

class ImunisasiViewModel(private val imunisasiUseCase: ImunisasiUseCase) :
    ViewModel() {
    fun getImunisasiNotDoneResponse(id: String) =
        imunisasiUseCase.getImunisasiNotDone(id).asLiveData()

    fun getImunisasiDoneResponse(id: String) = imunisasiUseCase.getImunisasiDone(id).asLiveData()

    //fun getImunisasiByJenisResponse(name: String) = imunisasiUseCase.getImunisasiByJenis(name).asLiveData()

    //fun addImunisasi(imunisasiTambah: ImunisasiTambah) = imunisasiUseCase.addImunisasi(imunisasiTambah).asLiveData()

    //fun editImunisasi(imunisasiEdit: ImunisasiEdit) = imunisasiUseCase.editImunisasi(imunisasiEdit).asLiveData()

    //fun deleteImunisasi(id: String) = imunisasiUseCase.getImunisasi(id).asLiveData()
}