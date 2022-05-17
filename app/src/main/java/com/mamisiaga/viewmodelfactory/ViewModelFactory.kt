package com.mamisiaga.viewmodelfactory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mamisiaga.repository.IbuPreference
import com.mamisiaga.tools.Injection
import com.mamisiaga.viewmodel.AnakViewModel
import com.mamisiaga.viewmodel.AutentikasiViewModel
import com.mamisiaga.viewmodel.IbuPreferenceViewModel
import com.mamisiaga.viewmodel.IbuViewModel

class ViewModelFactory {
    class IbuPreferenceViewModelFactory(private val ibuPreference: IbuPreference) :
        ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = when {
            modelClass.isAssignableFrom(IbuPreferenceViewModel::class.java) -> IbuPreferenceViewModel(
                ibuPreference
            ) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    class AutentikasiViewModelFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AutentikasiViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AutentikasiViewModel(Injection.provideAutentikasiUseCase()) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    class IbuViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(IbuViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return IbuViewModel(Injection.provideIbuUseCase(context)) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    class AnakViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(IbuViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AnakViewModel(Injection.provideAnakUseCase()) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    /*
    class StoryViewModelFactory(
        private val context: Context,
        private val token: String,
        private val location: Int
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(StoryViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return StoryViewModel(Injection.provideRepository(context, token, location)) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
     */
}