package com.android.fairybaked

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.fairybaked.databinding.ItemRotiBinding

class RotiAdapter(
    private var rotiList: List<Roti>,
    private val onBeliClicked: (Roti) -> Unit
) : RecyclerView.Adapter<RotiAdapter.RotiViewHolder>() {

    inner class RotiViewHolder(private val binding: ItemRotiBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(roti: Roti) {
            binding.tvNamaRoti.text = roti.nama
            binding.tvDeskripsiRoti.text = roti.deskripsi
            binding.tvHargaRoti.text = "Rp ${roti.harga}"
            binding.btnBeli.setOnClickListener {
                onBeliClicked(roti)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RotiViewHolder {
        val binding = ItemRotiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RotiViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RotiViewHolder, position: Int) {
        holder.bind(rotiList[position])
    }

    override fun getItemCount(): Int {
        return rotiList.size
    }

    // Fungsi untuk memperbarui data di adapter
    fun updateData(newRotiList: List<Roti>) {
        this.rotiList = newRotiList
        notifyDataSetChanged()
    }


}