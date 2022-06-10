package com.mamisiaga.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mamisiaga.dataClass.Pertumbuhan
import com.mamisiaga.databinding.ItemScanKmsBinding

class ScanKMSAdapter(private val scanKMSList: ArrayList<Pertumbuhan>) :
    RecyclerView.Adapter<ScanKMSAdapter.ScanKMSViewHolder>() {
    inner class ScanKMSViewHolder(private val binding: ItemScanKmsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pertumbuhan: Pertumbuhan) {
            binding.pertumbuhan = pertumbuhan
            binding.edittextBeratBadan.setText(pertumbuhan.weight.toString())
            binding.textviewBulan.text = "Bulan ke ${pertumbuhan.age}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScanKMSViewHolder =
        ScanKMSViewHolder(
            ItemScanKmsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ScanKMSViewHolder, position: Int) {
        holder.bind(scanKMSList[position])
    }

    override fun getItemCount(): Int = scanKMSList.size
}