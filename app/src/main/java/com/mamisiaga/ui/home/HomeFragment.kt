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
import com.mamisiaga.dataClass.Ibu
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
    private var _loginViewModel: LoginPreferenceViewModel? = null
    private val loginViewModel get() = _loginViewModel!!
    private var _userViewModel: UserViewModel? = null
    private val userViewModel get() = _userViewModel!!
    private var _ibu: Ibu? = null
    private val ibu: Ibu get() = _ibu!!

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

        _ibu = requireActivity().intent.getParcelableExtra<Ibu>("extraIbu") as Ibu

        binding.layoutOffline.buttonMuatUlang.setOnClickListener(this)
        binding.informasiIbuHamil.setOnClickListener(this)
        binding.informasiAnak.setOnClickListener(this)
        binding.konten.setOnClickListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
        _loginViewModel = null
        _userViewModel = null
        _ibu = null
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.button_muat_ulang -> {
                requireActivity().recreate()
            }
            R.id.informasi_ibu_hamil -> {
                val intent = Intent(activity, InformasiIbuHamilActivity::class.java)
                startActivity(intent)
            }
            R.id.informasi_anak -> {
                startActivity(
                    Intent(activity, InformasiAnakActivity::class.java).putExtra(
                        InformasiAnakActivity.EXTRA_IBU,
                        ibu
                    )
                )
            }
            R.id.konten -> {
                startActivity(Intent(activity, ScanKMSActivity::class.java))
            }
        }
    }

    private fun setupViewModel() {
        _userViewModel = ViewModelProvider(
            this,
            ViewModelFactory.UserViewModelFactory()
        )[UserViewModel::class.java]

        _loginViewModel = ViewModelProvider(
            this,
            ViewModelFactory.LoginPreferenceViewModelFactory(
                LoginPreference.getInstance(requireActivity().dataStore)
            )
        )[LoginPreferenceViewModel::class.java]
    }

    private fun seeIbuResponse() {
        loginViewModel.getLogin().observe(viewLifecycleOwner) {
            userViewModel.getUser(it.token).observe(viewLifecycleOwner) { resultResponse ->
                when (resultResponse) {
                    is ResultResponse.Loading -> {

                    }
                    is ResultResponse.Success -> {
                        _ibu = Ibu(resultResponse.data.userData.id, ibu.token, ibu.isMasuk)
                        val user = resultResponse.data.userData
                        binding.textviewNama.text = getString(R.string.hai, user.profileData.name)
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