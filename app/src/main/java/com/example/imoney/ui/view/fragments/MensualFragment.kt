package com.example.imoney.ui.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.imoney.databinding.FragmentMensualBinding
import com.example.imoney.ui.view.adapters.TransaccionAdapter
import com.example.imoney.ui.view.adapters.TransaccionMonthAdapter
import com.example.imoney.ui.viewmodel.FrTransaccionViewModel
import java.text.SimpleDateFormat
import java.util.*


class MensualFragment : Fragment() {
    val sdfYearNumber = SimpleDateFormat("yyyy")
    val sdfMonthLetter = SimpleDateFormat("MMMM", Locale("es","pe"))
    val sdfMonthNumber = SimpleDateFormat("MM", Locale("es","pe"))

    lateinit var txtIngresosTot: TextView
    lateinit var txtGastoTot: TextView
    lateinit var txtSaldoNeto: TextView

    private val viewModel: FrTransaccionViewModel by viewModels()

    private var _binding: FragmentMensualBinding? =null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMensualBinding.inflate(inflater,container,false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        txtIngresosTot = binding.tvIngresosTotal
        txtGastoTot = binding.tvGastosTotal
        txtSaldoNeto = binding.tvSaldoNeto

        var yearToday = sdfYearNumber.format(Date()).toString()
        binding.tvYear.text = yearToday


        // Specify layout for recycler view
        binding.recyclerviewTransacciones?.setHasFixedSize(true)
        val gridLayoutManager = GridLayoutManager(
            this.context,1)


        binding.recyclerviewTransacciones?.layoutManager = gridLayoutManager




        //EVENTO CLICK BACK YEAR
        binding.btnBackYear.setOnClickListener {

            binding.tvYear.text = (binding.tvYear.text.toString().toInt() -1).toString()
            viewModel.getTransaccionesGroupMonth(binding.tvYear.text.toString()).observe(viewLifecycleOwner,{

                binding.recyclerviewTransacciones.adapter = TransaccionMonthAdapter(it,txtIngresosTot,txtGastoTot,txtSaldoNeto)
            })

        }
        //EVENTO CLICK NEXT YEAR
        binding.btnNextYear.setOnClickListener {


            binding.tvYear.text = (binding.tvYear.text.toString().toInt() +1).toString()
            viewModel.getTransaccionesGroupMonth(binding.tvYear.text.toString()).observe(viewLifecycleOwner,{

                binding.recyclerviewTransacciones.adapter = TransaccionMonthAdapter(it,txtIngresosTot,txtGastoTot,txtSaldoNeto)
            })
        }

        viewModel.getTransaccionesGroupMonth(binding.tvYear.text.toString()).observe(viewLifecycleOwner,{

            binding.recyclerviewTransacciones.adapter = TransaccionMonthAdapter(it,txtIngresosTot,txtGastoTot,txtSaldoNeto)
        })

    }
}