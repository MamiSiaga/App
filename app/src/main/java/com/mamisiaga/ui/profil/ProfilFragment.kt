package com.mamisiaga.ui.profil

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mamisiaga.R
import com.mamisiaga.dataClass.Ibu
import com.mamisiaga.databinding.FragmentProfilBinding
import com.mamisiaga.repository.IbuPreference
import com.mamisiaga.repository.LoginPreference
import com.mamisiaga.tools.ResultResponse
import com.mamisiaga.tools.isConnected
import com.mamisiaga.ui.MainActivity
import com.mamisiaga.viewmodel.IbuPreferenceViewModel
import com.mamisiaga.viewmodel.IbuViewModel
import com.mamisiaga.viewmodel.LoginPreferenceViewModel
import com.mamisiaga.viewmodel.UserViewModel
import com.mamisiaga.viewmodelfactory.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class ProfilFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentProfilBinding? = null
//    private var _ibuViewModel: IbuViewModel? = null
    private lateinit var ibu: Ibu
    private val binding get() = _binding!!
//    private val ibuViewModel get() = _ibuViewModel!!
    private lateinit var loginViewModel : LoginPreferenceViewModel
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        drawLayout()
        seeIbuResponse()

        binding.layoutOffline.buttonMuatUlang.setOnClickListener(this)
        binding.ButtonKeluar.setOnClickListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.button_muat_ulang -> {
                drawLayout()
            }
            R.id.ButtonKeluar -> {
                logout()
            }
        }
    }

    private fun setupViewModel(){
        loginViewModel = ViewModelProvider(
            this,
            ViewModelFactory.LoginPreferenceViewModelFactory(
                LoginPreference.getInstance(requireActivity().dataStore)
            )
        )[LoginPreferenceViewModel::class.java]

        userViewModel = ViewModelProvider(
            this,
            ViewModelFactory.UserViewModelFactory()
        )[UserViewModel::class.java]
    }

    private fun seeIbuResponse() {
        loginViewModel.getLogin().observe(viewLifecycleOwner){
            userViewModel.getUser(it.token).observe(viewLifecycleOwner){ resultResponse ->
                when(resultResponse) {
                    is ResultResponse.Loading -> {

                    }
                    is ResultResponse.Success -> {
                        val user = resultResponse.data.userData
                        binding.textviewEmail.text = user.email
                    }
                    is ResultResponse.Error -> {

                    }
                }
            }
        }
    }

    private fun logout() {
        loginViewModel.logout()
        startActivity(Intent(this@ProfilFragment.context, MainActivity::class.java))
        requireActivity().finish()
    }

    private fun drawLayout() {
        // Checking Internet connection
        if (isConnected(requireActivity().applicationContext)) {
            binding.layoutOnline.visibility = View.VISIBLE
            binding.layoutOffline.layoutOffline.visibility = View.GONE
        } else {
            binding.layoutOnline.visibility = View.GONE
            binding.layoutOffline.layoutOffline.visibility = View.VISIBLE
        }
    }
}