package com.android.fairybaked

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DataAccessObject {

    // --- Query untuk Roti ---
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateRoti(roti: Roti)

    @Update
    suspend fun updateRoti(roti: Roti)

    @Delete
    suspend fun deleteRoti(roti: Roti)

    @Query("SELECT * FROM roti_table ORDER BY nama ASC")
    fun getAllRoti(): LiveData<List<Roti>>


    // Query untuk Pesanan
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPesanan(pesanan: Pesanan)

    @Query("SELECT * FROM pesanan_table ORDER BY timestamp DESC")
    fun getAllPesanan(): LiveData<List<Pesanan>>
}