package com.android.fairybaked

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "roti_table")
data class Roti(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nama: String,
    val deskripsi: String,
    val harga: Int
)
