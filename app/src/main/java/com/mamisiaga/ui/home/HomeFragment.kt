package com.mamisiaga.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mamisiaga.R
import com.mamisiaga.`class`.Ibu
import com.mamisiaga.databinding.FragmentHomeBinding
import com.mamisiaga.tools.isConnected
import com.mamisiaga.ui.AnakActivity
import com.mamisiaga.ui.InformasiAnakActivity
import com.mamisiaga.ui.InformasiIbuHamilActivity
import com.mamisiaga.viewmodel.IbuViewModel
import com.mamisiaga.viewmodelfactory.ViewModelFactory

class HomeFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentHomeBinding? = null
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

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //showGraph()

        //showLineChart()

        ibu = requireActivity().intent.getParcelableExtra<Ibu>("extraIbu") as Ibu

        drawLayout()

        setText()

        binding.layoutOffline.buttonMuatUlang.setOnClickListener(this)
        binding.informasiIbuHamil.setOnClickListener(this)
        binding.informasiAnak.setOnClickListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _ibuViewModel = null
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
                val intent = Intent(activity, InformasiAnakActivity::class.java)

                startActivity(intent)
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

    @SuppressLint("SetTextI18n")
    private fun setText() {
        binding.textviewNama.text = "${binding.textviewNama.text}, ${ibu.name}!"
    }
}