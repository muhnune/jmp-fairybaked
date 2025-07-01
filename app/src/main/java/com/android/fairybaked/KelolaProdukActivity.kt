package com.android.fairybaked

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.fairybaked.databinding.ActivityKelolaProdukBinding
import com.android.fairybaked.databinding.DialogTambahEditProdukBinding

class KelolaProdukActivity : AppCompatActivity() {

    private lateinit var binding: ActivityKelolaProdukBinding
    private lateinit var viewModel: TokoViewModel
    private lateinit var adapter: KelolaProdukAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKelolaProdukBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(TokoViewModel::class.java)

        setupRecyclerView()

        binding.progressBarProduk.visibility = View.VISIBLE
        binding.tvEmptyStateProduk.visibility = View.GONE
        binding.recyclerViewKelolaProduk.visibility = View.GONE

        // Mengamati perubahan pada daftar roti
        viewModel.semuaRoti.observe(this) { rotiList ->
            adapter.updateData(rotiList)
            binding.progressBarProduk.visibility = View.GONE
            if (rotiList.isEmpty()) {
                binding.tvEmptyStateProduk.visibility = View.VISIBLE
                binding.recyclerViewKelolaProduk.visibility = View.GONE
                Toast.makeText(this, "Belum ada produk.", Toast.LENGTH_SHORT).show()
            } else {
                binding.tvEmptyStateProduk.visibility = View.GONE
                binding.recyclerViewKelolaProduk.visibility = View.VISIBLE
                adapter.updateData(rotiList)
            }
        }

        // Listener untuk tombol Tambah (+)
        binding.fabTambahProduk.setOnClickListener {
            // Panggil dialog dengan parameter null untuk mode "Tambah"
            showTambahEditDialog(null)
        }
    }

    private fun setupRecyclerView() {
        adapter = KelolaProdukAdapter(
            emptyList(),
            onEditClick = { roti ->
                // Panggil dialog dengan data roti untuk mode "Edit"
                showTambahEditDialog(roti)
            },
            onHapusClick = { roti ->
                // Panggil konfirmasi sebelum menghapus
                konfirmasiHapus(roti)
            }
        )
        binding.recyclerViewKelolaProduk.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewKelolaProduk.adapter = adapter
    }

    private fun konfirmasiHapus(roti: Roti) {
        AlertDialog.Builder(this)
            .setTitle("Konfirmasi Hapus")
            .setMessage("Anda yakin ingin menghapus produk '${roti.nama}'?")
            .setPositiveButton("Hapus") { _, _ ->
                viewModel.deleteRoti(roti)
                Toast.makeText(this, "Produk berhasil dihapus", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun showTambahEditDialog(roti: Roti?) {
        val dialogBinding = DialogTambahEditProdukBinding.inflate(LayoutInflater.from(this))
        val dialogTitle = if (roti == null) "Tambah Produk Baru" else "Edit Produk"

        // Jika mode edit, isi form dengan data yang ada
        roti?.let {
            dialogBinding.etNamaProduk.setText(it.nama)
            dialogBinding.etDeskripsiProduk.setText(it.deskripsi)
            dialogBinding.etHargaProduk.setText(it.harga.toString())
        }

        AlertDialog.Builder(this)
            .setTitle(dialogTitle)
            .setView(dialogBinding.root)
            .setPositiveButton("Simpan", null)
            .setNegativeButton("Batal", null)
            .create()
            .apply {
                // Custom listener untuk tombol Simpan agar bisa validasi dulu
                setOnShowListener {
                    getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                        val nama = dialogBinding.etNamaProduk.text.toString().trim()
                        val deskripsi = dialogBinding.etDeskripsiProduk.text.toString().trim()
                        val hargaString = dialogBinding.etHargaProduk.text.toString().trim()

                        if (nama.isEmpty() || deskripsi.isEmpty() || hargaString.isEmpty()) {
                            Toast.makeText(this@KelolaProdukActivity, "Semua field harus diisi", Toast.LENGTH_SHORT).show()
                            return@setOnClickListener
                        }

                        val harga = hargaString.toIntOrNull()
                        if (harga == null) {
                            Toast.makeText(this@KelolaProdukActivity, "Harga harus berupa angka", Toast.LENGTH_SHORT).show()
                            return@setOnClickListener
                        }

                        val rotiBaru = Roti(
                            id = roti?.id ?: 0,
                            nama = nama,
                            deskripsi = deskripsi,
                            harga = harga
                        )

                        viewModel.insertOrUpdateRoti(rotiBaru)
                        Toast.makeText(this@KelolaProdukActivity, "Produk berhasil disimpan", Toast.LENGTH_SHORT).show()
                        dismiss()
                    }
                }
            }
            .show()
    }
}