package com.example.imoney.ui.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imoney.R
import com.example.imoney.data.entity.Transaccion
import com.example.imoney.data.local.dao.TransaccionDao

import com.example.imoney.databinding.CardviewContainerTransaccionBinding
import com.example.imoney.databinding.CardviewTransaccionMonthBinding
import com.example.imoney.ui.viewmodel.FrTransaccionViewModel

import java.text.SimpleDateFormat
import java.util.*


class TransaccionMonthAdapter(private val transacciones: List<TransaccionDao.DtoTranGroupByMonth>,
                              private val tvIngresoTot: TextView,
                              private val tvGastoTot: TextView,
                              private val tvSaldoNeto: TextView)
    : RecyclerView.Adapter<TransaccionMonthAdapter.ViewHolder>(){
    var totIngreso =0.00
    var totGasto = 0.00

    val sdfDayNumber = SimpleDateFormat("dd",Locale("es","pe"))
    val sdfDate = SimpleDateFormat("yyyy/MM/dd",Locale("es","pe"))

init {
    if (transacciones.isEmpty()){
        tvIngresoTot.text = "S/ " + String.format("%.2f", 0f)
        tvGastoTot.text = "S/ " + String.format("%.2f", 0f)
        tvSaldoNeto.text = "S/ " + String.format("%.2f", 0f)
    }
}
    val sdfMonthLetter = SimpleDateFormat("MMM",Locale("es","pe"))
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : ViewHolder {
        val v: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview_transaccion_month,parent,false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var año = sdfDate.parse(transacciones[position].mes)
         holder.tvMonth.text = sdfMonthLetter.format(año).toString()
        holder.tvIngreso.text = transacciones[position].ingreso.toString()
        holder.tvGasto.text = transacciones[position].gasto.toString()

        totIngreso += transacciones[position].ingreso!!
        tvIngresoTot.text = "S/ " + String.format("%.2f", totIngreso)


        totGasto += transacciones[position].gasto!!
        tvGastoTot.text = "S/ " + String.format("%.2f", totGasto)

        tvSaldoNeto.text = "S/ " + String.format("%.2f", totIngreso - totGasto)


    }

    override fun getItemCount(): Int {
        return transacciones.size
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    class ViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){
        var binding = CardviewTransaccionMonthBinding.bind(itemView)

        val tvIngreso = binding.tvIngreso
        val tvGasto = binding.tvGasto
        val tvMonth = binding.tvMonth




    }


}


