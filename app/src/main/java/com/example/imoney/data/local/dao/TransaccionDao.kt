package com.example.imoney.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.imoney.data.entity.Prestamo
import com.example.imoney.data.entity.Transaccion
import java.util.*

@Dao
interface TransaccionDao {


    @Query("SELECT * FROM Transaccion ")
    fun getAll(): LiveData<List<Transaccion>>

    @Query("SELECT * FROM Prestamo  ")
    fun getAllPrestamos(): LiveData<List<Prestamo>>

    @Query("select  Sum(CASE tipo WHEN 'ingreso' THEN monto ELSE 0 END) as 'ingreso',Sum(CASE tipo WHEN 'gasto' THEN monto ELSE 0 END)  as 'gasto',strftime('%Y/%m/%d', datetime(fecha/1000, 'unixepoch')) as 'mes' from transaccion where cast(strftime('%Y', datetime(fecha/1000, 'unixepoch')) AS int) = :year group by strftime('%m', datetime(fecha/1000, 'unixepoch'))")
    fun getTransactionGorupByMonth(year: String): LiveData<List<DtoTranGroupByMonth>>
    // You can also define this class in a separate file.
    data class DtoTranGroupByMonth(val ingreso: Float?, val gasto: Float?, val mes: String)

    @Query("SELECT t.tipo as tipo,t.categoria as categoria ,sum(t.monto) as monto FROM  Transaccion as t where cast(strftime('%m', datetime(fecha/1000, 'unixepoch')) AS int) = :mes and cast(strftime('%Y', datetime(fecha/1000, 'unixepoch')) AS int) = :year group by t.categoria,t.tipo")
    fun getTransaccionesWithCategory(mes:String,year:String): LiveData<List<DtoTransaccion>>

    // You can also define this class in a separate file.
    data class DtoTransaccion(val tipo: String?, val categoria: String?, val monto: Float)

    @Query("SELECT * FROM  Transaccion where fecha = :fecha")
    fun getAllWithFecha(fecha:Date): LiveData<List<Transaccion>>

    @Query("SELECT * FROM  Transaccion where fecha = :fecha")
    fun getSaldo(fecha:Date): LiveData<List<Transaccion>>

    @Query("SELECT * FROM  Transaccion where cast(strftime('%m', datetime(fecha/1000, 'unixepoch')) AS int) = :mes and cast(strftime('%Y', datetime(fecha/1000, 'unixepoch')) AS int) = :year group by fecha")
    fun getTransaccionesGroup(mes:String,year:String): LiveData<List<Transaccion>>

    @Query("SELECT * FROM  Transaccion where cast(strftime('%m', datetime(fecha/1000, 'unixepoch')) AS int) = :mes and cast(strftime('%Y', datetime(fecha/1000, 'unixepoch')) AS int) = :year ")
    fun getTransaccionesWithMonthAndYear(mes:String,year:String): LiveData<List<Transaccion>>
    @Insert
    suspend fun insertAll(vararg transaccion: Transaccion)

    @Insert
    suspend fun insertPrestamo(vararg prestamo: Prestamo)

    @Update
    suspend fun updatePrestamo(vararg prestamo: Prestamo)

    @Update
    suspend fun updateTransaction(vararg transaccion: Transaccion)
/*



    @Update
    suspend fun update(carrito: Carrito)


    @Query("DELETE FROM carrito WHERE uid=:id")
    suspend fun delete(id: String)*/
}