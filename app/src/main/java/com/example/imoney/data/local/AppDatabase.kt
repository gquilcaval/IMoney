package com.example.imoney.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.imoney.data.local.dao.TransaccionDao
import com.example.imoney.data.clases.Converter
import com.example.imoney.data.entity.Prestamo
import com.example.imoney.data.entity.Transaccion

@Database(entities = [Transaccion::class, Prestamo::class], version = 1)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract val transaccionDao: TransaccionDao
    companion object{
        @Volatile
        private var INSTANCE : AppDatabase? = null
        fun getInstance(context: Context):AppDatabase{
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "imoneyDB"
                )
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}