package com.mamisiaga.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mamisiaga.`class`.Opsi
import com.mamisiaga.databinding.ItemOpsiBinding

/*
class OpsiAdapter(private val arrayOpsi: ArrayList<Opsi>, private val opsi: Opsi) :
    RecyclerView.Adapter<OpsiAdapter.OpsiViewHolder>() {
    private lateinit var binding: ItemOpsiBinding

    class OpsiViewHolder(private var binding: ItemOpsiBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): OpsiViewHolder {
        binding =
            ItemOpsiBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return OpsiViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: OpsiViewHolder, position: Int) {
        viewHolder.setIsRecyclable(false)

        val (item) = arrayOpsi[position]

        binding.textviewOpsi.text = item

        viewHolder.itemView.setOnClickListener {
            val opsi = arrayOpsi[position]

            if (opsi.item == "Buat jadwal") {
                /*viewHolder.itemView.context.startActivity(
                    Intent(
                        viewHolder.itemView.context,
                        TambahJadwalImunisasiActivity::class.java
                    )
                )
                 */
            }
        }
    }

    override fun getItemCount() = arrayOpsi.size
}
 */

class OpsiAdapter(private val setListener: (Opsi) -> Unit) :
    ListAdapter<Opsi, OpsiAdapter.OpsiViewHolder>(CALLBACK) {
    private var listOpsi = ArrayList<Opsi>()

    inner class OpsiViewHolder(private val binding: ItemOpsiBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(opsi: Opsi) {
            binding.textviewOpsi.text = opsi.item

            binding.cardviewOpsi.setOnClickListener {
                setListener(opsi)
            }
        }
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): OpsiAdapter.OpsiViewHolder {
        val binding =
            ItemOpsiBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return OpsiViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: OpsiViewHolder, position: Int) {
        val opsi = getItem(position)

        if (opsi != null) {
            viewHolder.bind(opsi)
        }
    }

    companion object {
        val CALLBACK: DiffUtil.ItemCallback<Opsi> = object : DiffUtil.ItemCallback<Opsi>() {
            override fun areItemsTheSame(oldItem: Opsi, newItem: Opsi) =
                oldItem.item == newItem.item

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: Opsi, newItem: Opsi) =
                oldItem == newItem
        }
    }
}