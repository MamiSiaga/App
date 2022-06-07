package com.mamisiaga.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mamisiaga.api.PertumbuhanData
import com.mamisiaga.databinding.ItemPertumbuhanBinding

class PertumbuhanDataAdapter(private val editDataListener: (PertumbuhanData) -> Unit) :
    ListAdapter<PertumbuhanData, PertumbuhanDataAdapter.PertumbuhanDataViewHolder>(CALLBACK) {
    private var listAnak = ArrayList<PertumbuhanData>()

    inner class PertumbuhanDataViewHolder(private val binding: ItemPertumbuhanBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(pertumbuhanData: PertumbuhanData) {
            binding.textViewBulan.text = "Usia: ${pertumbuhanData.age.toString()} bulan"
            binding.textViewTglPemeriksaan.text =
                "Date of measurement"
            binding.textViewBeratBadan.text = "Berat badan: ${pertumbuhanData.weight} kg"
            binding.textViewTinggiBadan.text = "Tinggi badan: ${pertumbuhanData.height} cm"
            binding.textViewLingkarKepala.text = "Lingkar kepala: ${pertumbuhanData.headDiameter} cm"

            /*
            binding.cardviewPertumbuhan.setOnClickListener {
                val pertumbuhan = Pertumbuhan(
                    pertumbuhanData.date_of_measurement,
                    pertumbuhanData.age,
                    pertumbuhanData.weight,
                    pertumbuhanData.height,
                    pertumbuhanData.headDiameter
                )
                val intent = Intent(itemView.context, AnakActivity::class.java)

                //intent.putExtra(AnakActivity.EXTRA_ANAK, pertumbuhanData)

                itemView.context.startActivity(intent)
            }
             */

            binding.textViewEditData.setOnClickListener {
                editDataListener(pertumbuhanData)
            }
        }
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): PertumbuhanDataViewHolder {
        val binding =
            ItemPertumbuhanBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return PertumbuhanDataViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: PertumbuhanDataViewHolder, position: Int) {
        val pertumbuhanData = getItem(position)

        if (pertumbuhanData != null) {
            viewHolder.bind(pertumbuhanData)
        }
    }

    companion object {
        val CALLBACK: DiffUtil.ItemCallback<PertumbuhanData> =
            object : DiffUtil.ItemCallback<PertumbuhanData>() {
                override fun areItemsTheSame(oldItem: PertumbuhanData, newItem: PertumbuhanData) =
                    oldItem.age == newItem.age

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(
                    oldItem: PertumbuhanData,
                    newItem: PertumbuhanData
                ) =
                    oldItem == newItem
            }
    }
}