package com.mamisiaga.adapter

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mamisiaga.dataClass.Pertumbuhan
import com.mamisiaga.databinding.ItemScanKmsBinding
import com.mamisiaga.tools.OnEditTextChanged

class ScanKMSAdapter(private val scanKMSList: ArrayList<Pertumbuhan>, val onEditTextChanged: OnEditTextChanged) :
    RecyclerView.Adapter<ScanKMSAdapter.ScanKMSViewHolder>() {

    inner class ScanKMSViewHolder(private val binding: ItemScanKmsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(pertumbuhan: Pertumbuhan) {
            binding.pertumbuhan = pertumbuhan
            binding.edittextBeratBadan.setText(pertumbuhan.weight.toString())
            binding.textviewBulan.text = "Bulan ke-${pertumbuhan.age}"

            binding.edittextBeratBadan.addTextChangedListener(object :
                TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    onEditTextChanged.onTextChanged(absoluteAdapterPosition, s.toString())
                }

                override fun afterTextChanged(s: Editable?) {

                }

            })
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