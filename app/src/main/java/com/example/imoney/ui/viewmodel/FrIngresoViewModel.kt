package com.example.imoney.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.imoney.data.local.AppDatabase
import com.example.imoney.data.entity.Transaccion
import kotlinx.coroutines.launch


class FrIngresoViewModel (application: Application): AndroidViewModel(application) {


    private val db:AppDatabase = AppDatabase.getInstance(getApplication())



    fun insert(transaccion: Transaccion){

        viewModelScope.launch {
            db.transaccionDao.insertAll(transaccion)
        }
    }
}