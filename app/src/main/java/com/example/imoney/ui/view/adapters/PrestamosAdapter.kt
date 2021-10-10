package com.example.imoney.ui.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.imoney.R
import com.example.imoney.data.entity.Prestamo
import com.example.imoney.databinding.CardviewTransaccionBinding

class PrestamosAdapter(private val prestamo: List<Prestamo>,
                       private var  itemClick: RecyclerItemClick)
    : RecyclerView.Adapter<PrestamosAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : ViewHolder {
        val v: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview_transaccion,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var item = prestamo.get(position)
        holder.tvCategoria.text = prestamo[position].categoria
        holder.tvPersona.text = prestamo[position].persona

        holder.tvMonto.text =  String.format("%.2f",(prestamo[position].total-prestamo[position].monto))


        holder.itemView.setOnClickListener { itemClick.itemClick(item) }

        }




    override fun getItemCount(): Int {
        return prestamo.size
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    class ViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){
        var binding = CardviewTransaccionBinding.bind(itemView)

        val tvCategoria = binding.tvCategoria
        val tvMonto = binding.tvMonto
        val tvPersona = binding.tvDescripcion





    }
    interface RecyclerItemClick{

        fun itemClick(item: Prestamo)

    }


}
