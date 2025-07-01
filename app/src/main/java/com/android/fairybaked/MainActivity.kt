package com.android.fairybaked // Pastikan package name sesuai

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.fairybaked.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: TokoViewModel
    private lateinit var rotiAdapter: RotiAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(TokoViewModel::class.java)

        setupRecyclerView()

        // Listener untuk tombol Area Admin
        binding.btnLihatPesanan.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        // Progress Bar
        binding.progressBarMain.visibility = View.VISIBLE
        binding.recyclerViewRoti.visibility = View.GONE
        binding.tvEmptyStateMain.visibility = View.GONE


        viewModel.semuaRoti.observe(this) { rotiList ->
            // Sembunyikan progress bar
            binding.progressBarMain.visibility = View.GONE

            if (rotiList.isEmpty()) {
                tambahDataRotiDummy()
                binding.tvEmptyStateMain.visibility = View.VISIBLE
                binding.recyclerViewRoti.visibility = View.GONE
            } else {
                // Jika ada data, tampilkan RecyclerView
                binding.tvEmptyStateMain.visibility = View.GONE
                binding.recyclerViewRoti.visibility = View.VISIBLE
                // Perbarui data di adapter
                rotiAdapter.updateData(rotiList)
            }
        }
    }

    private fun setupRecyclerView() {
        rotiAdapter = RotiAdapter(emptyList()) { roti ->
            // Intent ke CheckoutActivity saat tombol "Beli" diklik
            val intent = Intent(this, CheckoutActivity::class.java).apply {
                // Mengirim data roti yang dipilih ke CheckoutActivity
                putExtra("ROTI_ID", roti.id)
                putExtra("ROTI_NAMA", roti.nama)
                putExtra("ROTI_DESKRIPSI", roti.deskripsi)
                putExtra("ROTI_HARGA", roti.harga)
            }
            startActivity(intent)
        }
        binding.recyclerViewRoti.adapter = rotiAdapter
        binding.recyclerViewRoti.layoutManager = LinearLayoutManager(this)
    }

    private fun tambahDataRotiDummy() {
        viewModel.insertOrUpdateRoti(Roti(nama = "Roti Tawar Gandum", deskripsi = "Roti tawar gandum premium, lembut dan sehat.", harga = 15000))
        viewModel.insertOrUpdateRoti(Roti(nama = "Butter Croissant", deskripsi = "Croissant mentega renyah dan wangi dari Prancis.", harga = 12000))
        viewModel.insertOrUpdateRoti(Roti(nama = "Donat Cokelat Klasik", deskripsi = "Donat empuk dengan topping cokelat meises melimpah.", harga = 8000))
        viewModel.insertOrUpdateRoti(Roti(nama = "Bagelen Keju", deskripsi = "Roti kering renyah dengan topping keju manis.", harga = 18000))
    }
}