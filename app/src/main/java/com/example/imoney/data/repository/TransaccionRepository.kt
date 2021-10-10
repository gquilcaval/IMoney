package com.example.imoney.data.repository

import androidx.lifecycle.LiveData
import com.example.imoney.data.local.AppDatabase
import com.example.imoney.data.entity.Transaccion
import javax.inject.Inject

class TransaccionRepository  @Inject constructor(
    private val db :AppDatabase
) {


    suspend fun  insertTransaccion(transaccion: Transaccion){
        db.transaccionDao.insertAll(transaccion)
    }

    suspend fun getAllTransaccion(): LiveData<List<Transaccion>> {

        val reponse = db.transaccionDao.getAll()

        return reponse
    }


}