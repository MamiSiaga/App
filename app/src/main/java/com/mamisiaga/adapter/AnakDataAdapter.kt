package com.mamisiaga.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mamisiaga.dataClass.Anak
import com.mamisiaga.api.AnakData
import com.mamisiaga.databinding.ItemAnakBinding
import com.mamisiaga.tools.withDateFormat
import com.mamisiaga.ui.AnakActivity

class AnakDataAdapter(private val setOpsiListener: (AnakData) -> Unit) :
    ListAdapter<AnakData, AnakDataAdapter.AnakDataViewHolder>(CALLBACK) {
    private var listAnak = ArrayList<AnakData>()

    inner class AnakDataViewHolder(private val binding: ItemAnakBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(anakData: AnakData) {
            binding.textViewNamaAnak.text = anakData.name
            binding.textViewTglLahirAnak.text = anakData.dateOfBirth.toString().withDateFormat()

            binding.cardviewAnak.setOnClickListener {
                val anak = Anak(
                    anakData.id,
                    anakData.name,
                    null,
                    null,
                    null
                )
                val intent = Intent(itemView.context, AnakActivity::class.java)

                intent.putExtra(AnakActivity.EXTRA_ANAK, anak)

                itemView.context.startActivity(intent)
            }

            binding.opsi.setOnClickListener {
                setOpsiListener(anakData)
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