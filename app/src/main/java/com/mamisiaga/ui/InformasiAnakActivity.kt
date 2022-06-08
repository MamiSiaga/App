package com.mamisiaga.ui

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mamisiaga.R
import com.mamisiaga.adapter.AnakDataAdapter
import com.mamisiaga.adapter.OpsiAdapter
import com.mamisiaga.api.AnakData
import com.mamisiaga.dataClass.Anak
import com.mamisiaga.dataClass.Ibu
import com.mamisiaga.dataClass.Opsi
import com.mamisiaga.databinding.ActivityInformasiAnakBinding
import com.mamisiaga.tools.ResultResponse
import com.mamisiaga.tools.isConnected
import com.mamisiaga.viewmodel.UserViewModel
import com.mamisiaga.viewmodelfactory.ViewModelFactory
import java.util.*
import kotlin.collections.ArrayList

class InformasiAnakActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityInformasiAnakBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var anakDataAdapter: AnakDataAdapter
    private var anakDataList = mutableListOf<AnakData>()
    private lateinit var ibu: Ibu
    private val responseCode =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == TambahAnakActivity.TAMBAH_ANAK_RESPONSE_CODE) {
                seeDaftarAnakResponse()

                Toast.makeText(
                    this,
                    "Penambahan data anak baru berhasil.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        binding = ActivityInformasiAnakBinding.inflate(layoutInflater)

        setContentView(binding.root)

        Locale.setDefault(Locale("id", "ID"))

        drawLayout()

        ibu = intent.getParcelableExtra<Ibu>(EXTRA_IBU) as Ibu

        userViewModel = ViewModelProvider(
            this,
            ViewModelFactory.UserViewModelFactory()
        )[UserViewModel::class.java]

        anakDataAdapter = AnakDataAdapter()

        anakDataAdapter.setCardViewListener = { anakData ->
            val anak = Anak(
                anakData.id,
                anakData.motherId,
                anakData.name,
                anakData.dateOfBirth,
                anakData.placeOfBirth,
                anakData.sex,
                anakData.bloodType
            )
            val intent = Intent(this@InformasiAnakActivity, AnakActivity::class.java)

            intent.putExtra(AnakActivity.EXTRA_IBU, ibu).putExtra(AnakActivity.EXTRA_ANAK, anak)

            startActivity(intent)
        }

        anakDataAdapter.setOpsiListener = { anakData ->
            val bottomSheetDialog = BottomSheetDialog(this)

            bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog_array)
            bottomSheetDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            val list = ArrayList<Opsi>()
            val listOpsi = ArrayList<Opsi>()
            val recyclerViewOpsi =
                bottomSheetDialog.findViewById<RecyclerView>(R.id.recyclerview_opsi)
            recyclerViewOpsi!!.layoutManager = LinearLayoutManager(this)
            val imageButtonTutup =
                bottomSheetDialog.findViewById<ImageButton>(R.id.imageButtonTutup)

            // Menu clicked in the bottom sheet dialog
            val opsiAdapter = OpsiAdapter { opsi ->
                val anak = Anak(
                    anakData.id,
                    anakData.motherId,
                    anakData.name,
                    anakData.dateOfBirth,
                    anakData.placeOfBirth,
                    anakData.sex,
                    anakData.bloodType
                )

                if (opsi.item == "Lihat data anak") {
                    startActivity(
                        Intent(
                            this@InformasiAnakActivity,
                            LihatAnakActivity::class.java
                        ).putExtra(LihatAnakActivity.EXTRA_ANAK, anak)
                    )

                    bottomSheetDialog.dismiss()
                } else if (opsi.item == "Hapus data anak") {

                    bottomSheetDialog.dismiss()
                }
            }

            val array = resources.getStringArray(R.array.opsi_anak_array)

            for (i in array.indices) {
                val opsi = Opsi(array[i])

                listOpsi.add(opsi)
            }

            list.addAll(listOpsi)

            opsiAdapter.submitList(list.toList())

            with(recyclerViewOpsi) {
                layoutManager = LinearLayoutManager(context)

                setHasFixedSize(true)

                adapter = opsiAdapter
            }

            bottomSheetDialog.show()

            imageButtonTutup!!.setOnClickListener {
                bottomSheetDialog.dismiss()
            }
        }

        seeDaftarAnakResponse()

        binding.layoutOffline.buttonMuatUlang.setOnClickListener(this)
        binding.imagebuttonKeluar.setOnClickListener(this)
        binding.tambahDataAnak.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.button_muat_ulang -> {
                recreate()
            }
            R.id.imagebutton_keluar -> {
                onBackPressed()
            }
            R.id.tambahDataAnak -> {
                responseCode.launch(
                    Intent(this, TambahAnakActivity::class.java).putExtra(
                        HomeActivity.EXTRA_IBU, ibu
                    )
                )
            }
        }
    }

    private fun seeDaftarAnakResponse() {
        userViewModel.getUser(ibu.token!!).observe(this) { resultResponse ->
            when (resultResponse) {
                is ResultResponse.Loading -> {
                    showLoadingSign(true)
                }
                is ResultResponse.Success -> {
                    showLoadingSign(false)

                    anakDataList =
                        resultResponse.data.userData.profileData.childrens.toMutableList()

                    anakDataAdapter.submitList(anakDataList)

                    if (resultResponse.data.userData.profileData.childrens.isEmpty()) {
                        binding.recyclerViewDataAnak.visibility = View.INVISIBLE
                        binding.textviewTidakAdaData.visibility = View.VISIBLE
                    } else {
                        binding.recyclerViewDataAnak.visibility = View.VISIBLE
                        binding.textviewTidakAdaData.visibility = View.GONE
                    }
                }
                is ResultResponse.Error -> {
                    showLoadingSign(false)

                    when (resultResponse.error) {
                        "No Internet Connection" -> drawLayout()
                        else -> {
                            binding.recyclerViewDataAnak.visibility = View.GONE
                            binding.textviewTidakAdaData.visibility = View.GONE

                            Toast.makeText(
                                this@InformasiAnakActivity,
                                "Gagal menampilkan data. Silahkan dicoba ulang.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }

            with(binding.recyclerViewDataAnak) {
                layoutManager = LinearLayoutManager(context)

                setHasFixedSize(true)

                adapter = anakDataAdapter
            }
        }

        userViewModel.getUser(ibu.token!!).removeObservers(this)
    }

    private fun drawLayout() {
        // Checking Internet connection
        if (isConnected(applicationContext)) {
            binding.layoutOnline.visibility = View.VISIBLE
            binding.layoutOffline.layoutOffline.visibility = View.GONE
        } else {
            binding.layoutOnline.visibility = View.GONE
            binding.layoutOffline.layoutOffline.visibility = View.VISIBLE
        }
    }

    private fun showLoadingSign(isLoading: Boolean) {
        binding.textviewTidakAdaData.visibility = View.GONE

        if (isLoading) {
            binding.progressbar.visibility = View.VISIBLE
        } else {
            binding.progressbar.visibility = View.GONE
        }
    }

    companion object {
        const val EXTRA_IBU = "extraIbu"
    }
}