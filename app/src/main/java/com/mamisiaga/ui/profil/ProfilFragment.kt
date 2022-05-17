package com.mamisiaga.ui.profil

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mamisiaga.R
import com.mamisiaga.databinding.FragmentProfilBinding
import com.mamisiaga.tools.isConnected

class ProfilFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentProfilBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //val dashboardViewModel =
        //ViewModelProvider(this)[IbuViewModel::class.java]

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