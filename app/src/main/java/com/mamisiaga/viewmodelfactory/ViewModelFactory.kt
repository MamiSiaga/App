package com.mamisiaga.viewmodelfactory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mamisiaga.repository.IbuPreference
import com.mamisiaga.usecase.Injection
import com.mamisiaga.viewmodel.*

class ViewModelFactory {
    class IbuPreferenceViewModelFactory(private val ibuPreference: IbuPreference) :
        ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = when {
            modelClass.isAssignableFrom(IbuPreferenceViewModel::class.java) -> IbuPreferenceViewModel(
                ibuPreference
            ) as T
            else -> throw IllegalArgumentException("Unknown ViewModel dataClass: " + modelClass.name)
        }
    }

    class AutentikasiViewModelFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AutentikasiViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AutentikasiViewModel(Injection.provideAutentikasiUseCase()) as T
            }
            throw IllegalArgumentException("Unknown ViewModel dataClass")
        }
    }

    class IbuViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(IbuViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return IbuViewModel(Injection.provideIbuUseCase(context)) as T
            }
            throw IllegalArgumentException("Unknown ViewModel dataClass")
        }
    }

    class KehamilanViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(KehamilanViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return KehamilanViewModel(Injection.provideKehamilanUseCase()) as T
            }
            throw IllegalArgumentException("Unknown ViewModel dataClass")
        }
    }

    class AnakViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AnakViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AnakViewModel(Injection.provideAnakUseCase()) as T
            }
            throw IllegalArgumentException("Unknown ViewModel dataClass")
        }
    }

    class PertumbuhanViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PertumbuhanViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return PertumbuhanViewModel(Injection.providePertumbuhanUseCase()) as T
            }
            throw IllegalArgumentException("Unknown ViewModel dataClass")
        }
    }

    class ImunisasiViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ImunisasiViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ImunisasiViewModel(Injection.provideImunisasiUseCase()) as T
            }
            throw IllegalArgumentException("Unknown ViewModel dataClass")
        }
    }
}