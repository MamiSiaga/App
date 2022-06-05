package com.mamisiaga.ui.home

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
import com.mamisiaga.databinding.FragmentHomeBinding
import com.mamisiaga.repository.LoginPreference
import com.mamisiaga.tools.ResultResponse
import com.mamisiaga.tools.isConnected
import com.mamisiaga.ui.InformasiAnakActivity
import com.mamisiaga.ui.InformasiIbuHamilActivity
import com.mamisiaga.ui.ScanKMSActivity
import com.mamisiaga.viewmodel.LoginPreferenceViewModel
import com.mamisiaga.viewmodel.UserViewModel
import com.mamisiaga.viewmodelfactory.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class HomeFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var userViewModel: UserViewModel
    private lateinit var loginPrefViewModel: LoginPreferenceViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        drawLayout()
        seeIbuResponse()

        binding.layoutOffline.buttonMuatUlang.setOnClickListener(this)
        binding.informasiIbuHamil.setOnClickListener(this)
        binding.informasiAnak.setOnClickListener(this)
        binding.konten.setOnClickListener(this)
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
            R.id.informasi_ibu_hamil -> {
                val intent = Intent(activity, InformasiIbuHamilActivity::class.java)
                startActivity(intent)
            }
            R.id.informasi_anak -> {
                startActivity(Intent(activity, InformasiAnakActivity::class.java))
            }
            R.id.konten -> {
                startActivity(Intent(activity, ScanKMSActivity::class.java))
            }
        }
    }

    private fun setupViewModel() {
        userViewModel = ViewModelProvider(
            this,
            ViewModelFactory.UserViewModelFactory()
        )[UserViewModel::class.java]

        loginPrefViewModel = ViewModelProvider(
            this,
            ViewModelFactory.LoginPreferenceViewModelFactory(
                LoginPreference.getInstance(requireActivity().dataStore)
            )
        )[LoginPreferenceViewModel::class.java]
    }

    private fun seeIbuResponse() {
        loginPrefViewModel.getLogin().observe(viewLifecycleOwner) {
            userViewModel.getUser(it.token).observe(viewLifecycleOwner) { resultResponse ->
                when (resultResponse) {
                    is ResultResponse.Loading -> {

                    }
                    is ResultResponse.Success -> {
                        val user = resultResponse.data.userData
                        binding.textviewNama.text = getString(R.string.hai, user.name)
                    }
                    is ResultResponse.Error -> {

                    }
                }
            }
        }
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