package com.android.fairybaked

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.android.fairybaked.databinding.ActivityCheckoutBinding
import com.google.android.gms.location.LocationServices

class CheckoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheckoutBinding
    private lateinit var viewModel: TokoViewModel

    private var latitude: Double? = null
    private var longitude: Double? = null

    // Launcher untuk meminta izin lokasi
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                // Izin diberikan, panggil fungsi untuk mendapatkan lokasi
                getLokasiPelanggan()
            } else {
                // Izin ditolak, beri tahu pengguna
                Toast.makeText(this, "Izin lokasi diperlukan untuk memesan.", Toast.LENGTH_LONG).show()
                binding.tvLokasiStatus.text = "Lokasi: Izin ditolak."
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(TokoViewModel::class.java)

        // Ambil data roti dari Intent
        val namaRoti = intent.getStringExtra("ROTI_NAMA") ?: "Roti Tidak Ditemukan"
        val hargaRoti = intent.getIntExtra("ROTI_HARGA", 0)

        binding.tvDetailRoti.text = "Memesan: $namaRoti (Rp $hargaRoti)"

        // Minta izin lokasi
        cekDanMintaIzinLokasi()

        binding.btnKonfirmasiPesan.setOnClickListener {
            val namaPelanggan = binding.etNamaPelanggan.text.toString().trim()

            // Validasi input
            if (namaPelanggan.isEmpty()) {
                Toast.makeText(this, "Nama pelanggan tidak boleh kosong.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (latitude == null || longitude == null) {
                Toast.makeText(this, "Lokasi tidak ditemukan. Coba lagi.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Buat objek Pesanan dan simpan ke database
            val pesanan = Pesanan(
                namaPelanggan = namaPelanggan,
                namaRoti = namaRoti,
                hargaRoti = hargaRoti,
                latitude = latitude!!,
                longitude = longitude!!
            )
            viewModel.tambahPesanan(pesanan)

            Toast.makeText(this, "Pesanan berhasil dibuat!", Toast.LENGTH_LONG).show()
            finish() // Kembali ke MainActivity setelah pesanan selesai
        }
    }

    private fun cekDanMintaIzinLokasi() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                // Izin sudah ada, langsung dapatkan lokasi
                getLokasiPelanggan()
            }
            else -> {
                // Izin belum ada, minta izin ke pengguna
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    private fun getLokasiPelanggan() {
        // Pastikan izin sudah dicek lagi sebelum memanggil fungsi ini
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }

        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    this.latitude = location.latitude
                    this.longitude = location.longitude
                    binding.tvLokasiStatus.text = "Lokasi: Koordinat berhasil didapat!"
                } else {
                    binding.tvLokasiStatus.text = "Lokasi: Gagal mendapatkan koordinat. Pastikan GPS aktif."
                    Toast.makeText(this, "Tidak dapat menemukan lokasi. Pastikan GPS Anda aktif.", Toast.LENGTH_LONG).show()
                }
            }
            .addOnFailureListener {
                binding.tvLokasiStatus.text = "Lokasi: Terjadi kesalahan."
                Toast.makeText(this, "Error: ${it.message}", Toast.LENGTH_LONG).show()
            }
    }
}