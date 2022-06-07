package com.mamisiaga.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mamisiaga.api.AnakData
import com.mamisiaga.databinding.ItemAnakBinding
import com.mamisiaga.tools.withDateFormatID
import com.mamisiaga.tools.withDateFormatID2

class AnakDataAdapter :
    ListAdapter<AnakData, AnakDataAdapter.AnakDataViewHolder>(CALLBACK) {

    var setCardViewListener: ((AnakData) -> Unit?)? = null
    var setOpsiListener: ((AnakData) -> Unit?)? = null

    inner class AnakDataViewHolder(private val binding: ItemAnakBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(anakData: AnakData) {
            binding.textViewNamaAnak.text = anakData.name
            binding.textViewTglLahirAnak.text = anakData.dateOfBirth.withDateFormatID2()

            binding.cardviewAnak.setOnClickListener {
                setCardViewListener?.invoke(anakData)
            }

            binding.opsi.setOnClickListener {
                setOpsiListener?.invoke(anakData)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): AnakDataViewHolder {
        val binding =
            ItemAnakBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return AnakDataViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: AnakDataViewHolder, position: Int) {
        val anakData = getItem(position)

        if (anakData != null) {
            viewHolder.bind(anakData)
        }
    }

    companion object {
        val CALLBACK: DiffUtil.ItemCallback<AnakData> = object : DiffUtil.ItemCallback<AnakData>() {
            override fun areItemsTheSame(oldItem: AnakData, newItem: AnakData) =
                oldItem.id == newItem.id

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: AnakData, newItem: AnakData) =
                oldItem == newItem
        }
    }
}