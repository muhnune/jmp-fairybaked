package com.android.fairybaked

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Roti::class, Pesanan::class], version = 1, exportSchema = false)
abstract class TokoRotiDatabase : RoomDatabase() {

    abstract fun tokoRotiDao(): DataAccessObject

    companion object {
        @Volatile
        private var INSTANCE: TokoRotiDatabase? = null

        fun getDatabase(context: Context): TokoRotiDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TokoRotiDatabase::class.java,
                    "tokoroti_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}