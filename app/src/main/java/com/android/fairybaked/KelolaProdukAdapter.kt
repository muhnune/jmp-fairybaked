package com.android.fairybaked

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.fairybaked.databinding.ItemKelolaRotiBinding


class KelolaProdukAdapter(
    private var rotiList: List<Roti>,
    private val onEditClick: (Roti) -> Unit,
    private val onHapusClick: (Roti) -> Unit
) : RecyclerView.Adapter<KelolaProdukAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemKelolaRotiBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(roti: Roti) {
            binding.tvNamaRoti.text = roti.nama
            binding.tvHargaRoti.text = "Rp ${roti.harga}"
            binding.btnEdit.setOnClickListener { onEditClick(roti) }
            binding.btnHapus.setOnClickListener { onHapusClick(roti) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemKelolaRotiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(rotiList[position])
    }

    override fun getItemCount() = rotiList.size

    fun updateData(newRotiList: List<Roti>) {
        this.rotiList = newRotiList
        notifyDataSetChanged()
    }
}