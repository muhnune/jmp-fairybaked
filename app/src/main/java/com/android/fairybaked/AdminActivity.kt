package com.android.fairybaked

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.fairybaked.databinding.ActivityAdminBinding

class AdminActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminBinding
    private lateinit var viewModel: TokoViewModel
    private lateinit var pesananAdapter: PesananAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(TokoViewModel::class.java)

        setupRecyclerView()

        binding.btnKelolaProduk.setOnClickListener {
            val intent = Intent(this, KelolaProdukActivity::class.java)
            startActivity(intent)
        }

        // ProgressBar
        binding.progressBarAdmin.visibility = View.VISIBLE
        binding.tvEmptyStateAdmin.visibility = View.GONE
        binding.recyclerViewPesanan.visibility = View.GONE

        // Observe data pesanan
        viewModel.semuaPesanan.observe(this) { pesananList ->
            // Setelah data didapat, sembunyikan ProgressBar
            binding.progressBarAdmin.visibility = View.GONE

            if (pesananList.isEmpty()) {
                // Jika daftar pesanan kosong, tampilkan pesan
                binding.tvEmptyStateAdmin.visibility = View.VISIBLE
                binding.recyclerViewPesanan.visibility = View.GONE
            } else {
                // Jika ada data, tampilkan RecyclerView
                binding.tvEmptyStateAdmin.visibility = View.GONE
                binding.recyclerViewPesanan.visibility = View.VISIBLE
                pesananAdapter.updateData(pesananList)
            }
        }
    }

    private fun setupRecyclerView() {
        pesananAdapter = PesananAdapter(emptyList())
        binding.recyclerViewPesanan.adapter = pesananAdapter
        binding.recyclerViewPesanan.layoutManager = LinearLayoutManager(this)
    }
}