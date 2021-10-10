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
import com.example.imoney.databinding.CardviewContainerTransaccionBinding
import com.example.imoney.ui.viewmodel.FrTransaccionViewModel

import java.text.SimpleDateFormat
import java.util.*

class TransaccionAdapter(private val transacciones: List<Transaccion>,
                         private val lifecycleOwner: LifecycleOwner,
                         private val viewmodel: FrTransaccionViewModel,
                         private val tvIngresoTot: TextView,
                         private val tvGastoTot: TextView,
                         private val tvSaldoNeto: TextView
)
    : RecyclerView.Adapter<TransaccionAdapter.ViewHolder>(){

    private lateinit var myAdapter: TransaccionChildAdapter
    var totIngreso =0.00
    var totGasto = 0.00
    val sdfDayNumber = SimpleDateFormat("dd",Locale("es","pe"))
    val sdfDayLetter = SimpleDateFormat("EEE",Locale("es","pe"))
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : ViewHolder {
        val v: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview_container_transaccion,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        val dayNumber = sdfDayNumber.format(transacciones[position].fecha)
        val dayText = sdfDayLetter.format(transacciones[position].fecha)

        holder.numberDate.text = dayNumber.toString()
        holder.textDate.text = dayText.toString()

        if (dayText.toString() == "sáb."){
            holder.cardFecha.setBackgroundColor(ContextCompat .getColor(holder.itemView.context,R.color.salmon))
        }
        if (holder.textDate.text == "dom."){
            holder.cardFecha.setBackgroundColor(ContextCompat.getColor(holder.itemView.context,R.color.celesteClaro))
        }

        holder.childRecyclerview.layoutManager = GridLayoutManager(holder.itemView.context,1, RecyclerView.VERTICAL,false)
        holder.childRecyclerview.setHasFixedSize(true)

        viewmodel.getTransaccionesWithDate(transacciones[position].fecha).observe(lifecycleOwner, androidx.lifecycle.Observer {


            if (it.isEmpty()){
                totIngreso = 0.00
                totGasto =  0.00

            }
            else {
                for (x in it) {

                    if (x.tipo == "ingreso") {
                        totIngreso += x.monto
                        tvIngresoTot.text = "S/ " + String.format("%.2f", totIngreso)
                    }
                    if (x.tipo == "gasto") {
                        totGasto += x.monto
                        tvGastoTot.text = "S/ " + String.format("%.2f", totGasto)
                    }
                    tvSaldoNeto.text = "S/ " + String.format("%.2f", totIngreso - totGasto)
                }
            }
            myAdapter= TransaccionChildAdapter(it)
            holder.childRecyclerview.adapter = myAdapter


        })






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
        var binding = CardviewContainerTransaccionBinding.bind(itemView)

        val childRecyclerview = binding.recyclerviewTransaccionChild
        val numberDate = binding.tvNumberDate
        val textDate = binding.tvTextDate
        val cardFecha = binding.cardFecha




    }


}


