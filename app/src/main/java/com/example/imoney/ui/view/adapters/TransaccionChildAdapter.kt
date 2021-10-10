package com.example.imoney.ui.view.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.imoney.R
import com.example.imoney.data.entity.Transaccion
import com.example.imoney.databinding.CardviewTransaccionBinding
import java.text.SimpleDateFormat

var totIngreso =0.00
var totGasto = 0.00
class TransaccionChildAdapter(private val transacciones: List<Transaccion>
)
    : RecyclerView.Adapter<TransaccionChildAdapter.ViewHolder>(){

    private val formato = SimpleDateFormat("yyyy/MM/dd")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : ViewHolder {
        val v: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview_transaccion,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.descripcion.text = transacciones[position].descripcion
        holder.categoria.text = transacciones[position].categoria
        holder.monto.text = "S/ "+String.format("%.2f",transacciones[position].monto)
        if (transacciones[position].tipo == "ingreso"){
            holder.monto.setTextColor(ContextCompat .getColor(holder.itemView.context,R.color.green_normal))
            totIngreso += transacciones[position].monto

        }
        if (transacciones[position].tipo == "gasto"){
            holder.monto.setTextColor(ContextCompat .getColor(holder.itemView.context,R.color.red_normal))
            totGasto += transacciones[position].monto

        }




      holder.itemView.setOnClickListener{

          val bundle = Bundle()
          bundle.putString("saldo", transacciones[position].monto.toString())
          if (transacciones[position].categoria == "Prestamo"){
              bundle.putBoolean("switch", true)
          }
          else {
              bundle.putBoolean("switch", false)
          }

          bundle.putString("id",transacciones[position].id)
          bundle.putString("fecha",formato.format(transacciones[position].fecha))
          bundle.putString("descripcion", transacciones[position].descripcion)
          bundle.putString("categoria", transacciones[position].categoria)
          bundle.putString("monto", transacciones[position].monto.toString())
          bundle.putString("persona", transacciones[position].persona)

          if (transacciones[position].tipo == "ingreso"){
              it.findNavController().navigate(R.id.dialogRegistroIngreso,bundle)

          }
          else{
              it.findNavController().navigate(R.id.dialogRegistroGasto,bundle)
          }







      }




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
        var binding = CardviewTransaccionBinding.bind(itemView)


        val descripcion = binding.tvDescripcion
        val categoria = binding.tvCategoria
        val monto = binding.tvMonto


    }


}