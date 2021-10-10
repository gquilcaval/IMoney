package com.example.imoney.data.entity


import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Transaccion(@PrimaryKey
                       val id: String,
                       val descripcion:String,
                       val persona:String,
                       val categoria:String,
                       val icono:String,
                       val tipo:String,
                       val monto:Float,
                       val fecha:Date )