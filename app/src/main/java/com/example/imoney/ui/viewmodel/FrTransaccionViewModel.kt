package com.example.imoney.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.imoney.data.local.AppDatabase
import com.example.imoney.data.local.dao.TransaccionDao
import com.example.imoney.data.entity.Prestamo
import com.example.imoney.data.entity.Transaccion
import kotlinx.coroutines.launch
import java.util.*


class FrTransaccionViewModel(application: Application): AndroidViewModel(application){

        private val db:AppDatabase = AppDatabase.getInstance(getApplication())




    fun getAllTransacciones(): LiveData<List<Transaccion>> {
        return db.transaccionDao.getAll()
    }

    fun getAllPrestamos(): LiveData<List<Prestamo>> {
        return db.transaccionDao.getAllPrestamos()
    }

    fun getTransaccionesWithDate(fecha: Date): LiveData<List<Transaccion>> {
        return db.transaccionDao.getAllWithFecha(fecha)
    }
    fun getTransaccionesWithMonthAndYear(mes: String, año: String): LiveData<List<Transaccion>> {
        return db.transaccionDao.getTransaccionesWithMonthAndYear(mes ,año)
    }

    fun getTransaccionesWithCategory(mes: String, año: String): LiveData<List<TransaccionDao.DtoTransaccion>> {
        return db.transaccionDao.getTransaccionesWithCategory(mes ,año)
    }
    fun getTransaccionesGroupMonth(año: String): LiveData<List<TransaccionDao.DtoTranGroupByMonth>> {
        return db.transaccionDao.getTransactionGorupByMonth(año)
    }


    fun getTransaccionesGroup(mes: String, año: String): LiveData<List<Transaccion>> {
        return db.transaccionDao.getTransaccionesGroup(mes,año)
    }
    fun insert(transaccion: Transaccion){

        viewModelScope.launch {
            db.transaccionDao.insertAll(transaccion)
        }
    }
    fun insertPrestamo(prestamo: Prestamo){

        viewModelScope.launch {
            db.transaccionDao.insertPrestamo(prestamo)
        }
    }

    fun updatePrestamo(prestamo: Prestamo){

        viewModelScope.launch {
            db.transaccionDao.updatePrestamo(prestamo)
        }
    }

    fun updateTransaction(transaccion: Transaccion){

        viewModelScope.launch {
            db.transaccionDao.updateTransaction(transaccion)
        }
    }
}

