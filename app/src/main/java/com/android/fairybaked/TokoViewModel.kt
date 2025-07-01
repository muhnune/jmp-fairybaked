package com.android.fairybaked

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TokoViewModel(application: Application) : AndroidViewModel(application) {
    private val dao: DataAccessObject

    val semuaRoti: LiveData<List<Roti>>
    val semuaPesanan: LiveData<List<Pesanan>>

    init {
        val db = TokoRotiDatabase.getDatabase(application)
        dao = db.tokoRotiDao()
        semuaRoti = dao.getAllRoti()
        semuaPesanan = dao.getAllPesanan()
    }

    fun insertOrUpdateRoti(roti: Roti) = viewModelScope.launch {
        dao.insertOrUpdateRoti(roti)
    }

    fun updateRoti(roti: Roti) = viewModelScope.launch {
        dao.updateRoti(roti)
    }

    fun deleteRoti(roti: Roti) = viewModelScope.launch {
        dao.deleteRoti(roti)
    }

    fun tambahPesanan(pesanan: Pesanan) = viewModelScope.launch {
        dao.insertPesanan(pesanan)
    }
}