package com.android.fairybaked

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.fairybaked.databinding.ItemPesananBinding
import java.text.SimpleDateFormat
import java.util.*

class PesananAdapter(private var pesananList: List<Pesanan>) : RecyclerView.Adapter<PesananAdapter.PesananViewHolder>() {

    inner class PesananViewHolder(private val binding: ItemPesananBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(pesanan: Pesanan) {
            binding.tvNamaPelanggan.text = pesanan.namaPelanggan
            binding.tvDetailPesananRoti.text = "Pesanan: ${pesanan.namaRoti} - Rp ${pesanan.hargaRoti}"
            binding.tvKoordinat.text = "Koordinat: %.5f, %.5f".format(pesanan.latitude, pesanan.longitude)

            // Format timestamp menjadi tanggal yang mudah dibaca
            val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            val date = Date(pesanan.timestamp)
            binding.tvWaktuPesan.text = "Waktu: ${sdf.format(date)}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PesananViewHolder {
        val binding = ItemPesananBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PesananViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PesananViewHolder, position: Int) {
        holder.bind(pesananList[position])
    }

    override fun getItemCount() = pesananList.size

    fun updateData(newPesananList: List<Pesanan>) {
        this.pesananList = newPesananList
        notifyDataSetChanged()
    }
}