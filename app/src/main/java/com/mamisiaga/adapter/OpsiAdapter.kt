package com.mamisiaga.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mamisiaga.`class`.Opsi
import com.mamisiaga.api.AnakData
import com.mamisiaga.databinding.ItemOpsiBinding

class OpsiAnakAdapter(private val arrayOpsi: ArrayList<Opsi>, private val anakData: AnakData) :
    RecyclerView.Adapter<OpsiAnakAdapter.OpsiAnakViewHolder>() {
    private lateinit var binding: ItemOpsiBinding

    class OpsiAnakViewHolder(private var binding: ItemOpsiBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): OpsiAnakViewHolder {
        binding =
            ItemOpsiBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return OpsiAnakViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: OpsiAnakViewHolder, position: Int) {
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