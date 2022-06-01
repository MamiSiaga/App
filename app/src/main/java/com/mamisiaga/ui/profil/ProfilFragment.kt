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
import com.mamisiaga.tools.isConnected
import com.mamisiaga.ui.MainActivity
import com.mamisiaga.viewmodel.IbuPreferenceViewModel
import com.mamisiaga.viewmodel.IbuViewModel
import com.mamisiaga.viewmodelfactory.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class ProfilFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentProfilBinding? = null
    private var _ibuViewModel: IbuViewModel? = null
    private lateinit var ibu: Ibu
    private val binding get() = _binding!!
    private val ibuViewModel get() = _ibuViewModel!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _ibuViewModel = ViewModelProvider(
            this,
            ViewModelFactory.IbuViewModelFactory(requireActivity().applicationContext)
        )[IbuViewModel::class.java]

        _binding = FragmentProfilBinding.inflate(inflater, container, false)

        //val textView: TextView = binding.textProfil
        //dashboardViewModel.text.observe(viewLifecycleOwner) {
        //textView.text = it
        //}
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        drawLayout()

        binding.layoutOffline.buttonMuatUlang.setOnClickListener(this)
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

    private fun logout() {
        val ibuPreferenceViewModel = ViewModelProvider(
            this,
            ViewModelFactory.IbuPreferenceViewModelFactory(IbuPreference.getInstance(requireActivity().dataStore))
        )[IbuPreferenceViewModel::class.java]

        ibuPreferenceViewModel.keluar()

        requireActivity().finish()

        startActivity(Intent(this@ProfilFragment.context, MainActivity::class.java))
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