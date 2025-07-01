package com.android.fairybaked

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pesanan_table")
data class Pesanan(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val namaPelanggan: String,
    val namaRoti: String,
    val hargaRoti: Int,
    val latitude: Double,
    val longitude: Double,
    val timestamp: Long = System.currentTimeMillis()
)
