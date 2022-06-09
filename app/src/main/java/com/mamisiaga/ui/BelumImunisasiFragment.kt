package com.mamisiaga.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mamisiaga.R
import com.mamisiaga.dataClass.Anak
import com.mamisiaga.dataClass.Ibu
import com.mamisiaga.databinding.FragmentBelumImunisasiBinding
import com.mamisiaga.tools.isConnected
import com.mamisiaga.viewmodel.ImunisasiViewModel
import com.mamisiaga.viewmodelfactory.ViewModelFactory

class BelumImunisasiFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentBelumImunisasiBinding? = null
    private var _imunisasiViewModel: ImunisasiViewModel? = null

    //private lateinit var imunisasi: Imunisasi
    private val binding get() = _binding!!
    private val imunisasiViewModel get() = _imunisasiViewModel!!
    private var _ibu: Ibu? = null
    private val ibu: Ibu get() = _ibu!!
    private var _anak: Anak? = null
    private val anak: Anak get() = _anak!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBelumImunisasiBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _ibu = requireActivity().intent.getParcelableExtra<Ibu>("extraIbu") as Ibu
        _anak = requireActivity().intent.getParcelableExtra<Anak>("extraAnak") as Anak

        _imunisasiViewModel = ViewModelProvider(
            this,
            ViewModelFactory.ImunisasiViewModelFactory(ibu.token!!)
        )[ImunisasiViewModel::class.java]

        //getImunisasiNotDoneResponse()

        binding.layoutOffline.buttonMuatUlang.setOnClickListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _imunisasiViewModel = null
        _ibu = null
        _anak = null
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.button_muat_ulang -> {
                requireActivity().recreate()
            }
        }
    }

    private fun getImunisasiNotDoneResponse() {
        /*imunisasiViewModel.getImunisasiResponse(anak.id!!)
            .observe(viewLifecycleOwner) { resultResponse ->
                when (resultResponse) {
                    is ResultResponse.Loading -> {

                    }
                    is ResultResponse.Success -> {
                        /*
                        imunisasiDataAdapter.submitList(resultResponse.data.imunisasiData)

                        if (resultResponse.data.imunisasiData.isEmpty()) {
                            binding.recyclerViewDataAnak.visibility = View.INVISIBLE
                            binding.textviewTidakAdaData.visibility = View.VISIBLE
                        } else {
                            binding.recyclerViewDataAnak.visibility = View.VISIBLE
                            binding.textviewTidakAdaData.visibility = View.GONE
                        }
                         */
                    }
                    is ResultResponse.Error -> {
                        when (resultResponse.error) {
                            //"No Internet Connection" -> drawLayout()
                            //else -> showMasukError(true)
                        }
                    }
                }

                with(binding.recyclerviewImunisasiRekomendasi) {
                    layoutManager = LinearLayoutManager(context)
                    setHasFixedSize(true)
                    //adapter = imunisasiDataAdapter
                }
            }

         */
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