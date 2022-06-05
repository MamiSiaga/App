package com.mamisiaga.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mamisiaga.databinding.FragmentBelumImunisasiBinding
import com.mamisiaga.tools.ResultResponse
import com.mamisiaga.viewmodel.ImunisasiViewModel
import com.mamisiaga.viewmodelfactory.ViewModelFactory

class BelumImunisasiFragment : Fragment() {
    private var _binding: FragmentBelumImunisasiBinding? = null
    private var _imunisasiViewModel: ImunisasiViewModel? = null

    //private lateinit var imunisasi: Imunisasi
    private val binding get() = _binding!!
    private val imunisasiViewModel get() = _imunisasiViewModel!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _imunisasiViewModel = ViewModelProvider(
            this,
            ViewModelFactory.ImunisasiViewModelFactory("9|4GgQ7ufHhmiMRZ289qHshRM79vFaGquYo3JHJ54z")
        )[ImunisasiViewModel::class.java]

        _binding = FragmentBelumImunisasiBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //getImunisasiNotDoneResponse()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _imunisasiViewModel = null
    }

    private fun getImunisasiNotDoneResponse() {
        imunisasiViewModel.getImunisasiNotDoneResponse("anak1")
            .observe(viewLifecycleOwner) { resultResponse ->
                when (resultResponse) {
                    is ResultResponse.Loading -> {

                    }
                    is ResultResponse.Success -> {
                        /*
                        imunisasiDataAdapter.submitList(resultResponse.data.imunisasiData)

                        if (resultResponse.data.imunisasiData.isEmpty()) {
                            binding.recyclerViewDataAnak.visibility = View.GONE
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
    }
}