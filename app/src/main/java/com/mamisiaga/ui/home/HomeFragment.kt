package com.mamisiaga.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mamisiaga.R
import com.mamisiaga.databinding.FragmentHomeBinding
import com.mamisiaga.ui.AnakActivity
import com.mamisiaga.ui.InformasiIbuHamilActivity

class HomeFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        /*
        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        */

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //showGraph()

        //showLineChart()

        binding.informasiIbuHamil.setOnClickListener(this)
        binding.informasiAnak.setOnClickListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.informasi_ibu_hamil -> {
                val intent = Intent(activity, InformasiIbuHamilActivity::class.java)

                startActivity(intent)
            }
            R.id.informasi_anak -> {
                val intent = Intent(activity, AnakActivity::class.java)

                startActivity(intent)
            }
        }
    }
}