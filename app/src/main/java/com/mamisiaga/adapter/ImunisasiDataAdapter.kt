package com.mamisiaga.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mamisiaga.api.ImunisasiData
import com.mamisiaga.databinding.ItemBelumImunisasiBinding
import com.mamisiaga.databinding.ItemSudahImunisasiBinding

class BelumImunisasiDataAdapter :
    ListAdapter<ImunisasiData, BelumImunisasiDataAdapter.ImunisasiDataViewHolder>(CALLBACK) {

    var setCardViewListener: ((ImunisasiData) -> Unit?)? = null
    var setOpsiListener: ((ImunisasiData) -> Unit?)? = null

    inner class ImunisasiDataViewHolder(private val binding: ItemBelumImunisasiBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(imunisasiData: ImunisasiData) {
            binding.textviewIsiImunisasiAnak.text = imunisasiData.name
            //binding.textViewTanggalImunisasi.text = imunisasiData.date.withDateFormatID2()

            binding.cardviewBelumImunisasi.setOnClickListener {
                setCardViewListener?.invoke(imunisasiData)
            }

            binding.opsi.setOnClickListener {
                setOpsiListener?.invoke(imunisasiData)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ImunisasiDataViewHolder {
        val binding =
            ItemBelumImunisasiBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ImunisasiDataViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ImunisasiDataViewHolder, position: Int) {
        val imunisasiData = getItem(position)

        if (imunisasiData != null) {
            viewHolder.bind(imunisasiData)
        }
    }

    companion object {
        val CALLBACK: DiffUtil.ItemCallback<ImunisasiData> =
            object : DiffUtil.ItemCallback<ImunisasiData>() {
                override fun areItemsTheSame(oldItem: ImunisasiData, newItem: ImunisasiData) =
                    oldItem.id == newItem.id

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldItem: ImunisasiData, newItem: ImunisasiData) =
                    oldItem == newItem
            }
    }
}

class SudahImunisasiDataAdapter :
    ListAdapter<ImunisasiData, SudahImunisasiDataAdapter.ImunisasiDataViewHolder>(CALLBACK) {

    var setCardViewListener: ((ImunisasiData) -> Unit?)? = null

    inner class ImunisasiDataViewHolder(private val binding: ItemSudahImunisasiBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(imunisasiData: ImunisasiData) {
            binding.textviewIsiImunisasiAnak.text = imunisasiData.name
            //binding.chipStatusImunisasi.text = imunisasiData.date.withDateFormatID2()

            binding.cardViewSudahImunisasi.setOnClickListener {
                setCardViewListener?.invoke(imunisasiData)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ImunisasiDataViewHolder {
        val binding =
            ItemSudahImunisasiBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ImunisasiDataViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ImunisasiDataViewHolder, position: Int) {
        val imunisasiData = getItem(position)

        if (imunisasiData != null) {
            viewHolder.bind(imunisasiData)
        }
    }

    companion object {
        val CALLBACK: DiffUtil.ItemCallback<ImunisasiData> =
            object : DiffUtil.ItemCallback<ImunisasiData>() {
                override fun areItemsTheSame(oldItem: ImunisasiData, newItem: ImunisasiData) =
                    oldItem.id == newItem.id

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldItem: ImunisasiData, newItem: ImunisasiData) =
                    oldItem == newItem
            }
    }
}