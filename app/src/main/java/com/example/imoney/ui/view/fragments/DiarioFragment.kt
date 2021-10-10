package com.example.imoney.ui.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.imoney.data.clases.Meses
import com.example.imoney.databinding.FragmentDiarioBinding
import com.example.imoney.ui.view.adapters.TransaccionAdapter
import com.example.imoney.ui.viewmodel.FrTransaccionViewModel
import java.text.SimpleDateFormat
import java.util.*


class DiarioFragment : Fragment() {
    val sdfYearNumber = SimpleDateFormat("yyyy")
    val sdfMonthLetter = SimpleDateFormat("MMMM",Locale("es","pe"))
    val sdfMonthNumber = SimpleDateFormat("MM",Locale("es","pe"))

    lateinit var txtIngresosTot:TextView
    lateinit var txtGastoTot:TextView
    lateinit var txtSaldoNeto:TextView
    var arrayMonth = arrayListOf(

        "Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto",
        "Setiembre","Octubre","Noviembre","Diciembre"
    )
    var meses = arrayListOf(
        Meses("Enero","01"),
        Meses("Febrero","02"),
        Meses("Marzo","03"),
        Meses("Abril","04"),
        Meses("Mayo","05"),
        Meses("Junio","06"),
        Meses("Julio","07"),
        Meses("Agosto","08"),
        Meses("Setiembre","09"),
        Meses("Octubre","10"),
        Meses("Noviembre","11"),
        Meses("Diciembre","12")

    )


    private val viewModel: FrTransaccionViewModel by viewModels()
    private var _binding: FragmentDiarioBinding? =null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDiarioBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var mesActual = sdfMonthLetter.format(Date()).toString()
        var monthNumber = sdfMonthNumber.format(Date()).toString()
        var yearToday = sdfYearNumber.format(Date()).toString()


            txtIngresosTot = binding.tvIngresosTotal
            txtGastoTot = binding.tvGastosTotal
            txtSaldoNeto = binding.tvSaldoNeto
        binding.tvMonth.text = mesActual.capitalize()
        binding.tvYear.text = yearToday


        // Specify layout for recycler view
        binding.recyclerviewTransacciones?.setHasFixedSize(true)
        val gridLayoutManager = GridLayoutManager(
            this.context,1)
        binding.recyclerviewTransacciones?.layoutManager = gridLayoutManager

        viewModel.getTransaccionesGroup(monthNumber,yearToday).observe(viewLifecycleOwner, androidx.lifecycle.Observer {

            binding.recyclerviewTransacciones.adapter = TransaccionAdapter(it, viewLifecycleOwner,viewModel,txtIngresosTot,txtGastoTot,txtSaldoNeto)

        })



        //EVENTO CLICK BACK MONTH
        binding.btnBackMonth.setOnClickListener {
            txtIngresosTot.text ="S/ 0.00"
            txtGastoTot.text = "S/ 0.00"
            txtSaldoNeto.text = "S/ 0.00"

            binding.tvMonth.text = if (arrayMonth.indexOf(binding.tvMonth.text) - 1 == -1) "Enero"
            else arrayMonth[arrayMonth.indexOf(binding.tvMonth.text) - 1 ]

            for (x in meses){
                if (binding.tvMonth.text == x.nombre){
                    mesActual=  x.numero

                }
            }

            viewModel.getTransaccionesGroup(mesActual,binding.tvYear.text.toString()).observe(viewLifecycleOwner, androidx.lifecycle.Observer {


                binding.recyclerviewTransacciones.adapter = TransaccionAdapter(it, viewLifecycleOwner,viewModel,txtIngresosTot,txtGastoTot,txtSaldoNeto)

            })




        }
        //EVENTO CLICK NEXT MONTH
        binding.btnNextMonth.setOnClickListener {


            txtIngresosTot.text ="S/ 0.00"
            txtGastoTot.text = "S/ 0.00"
            txtSaldoNeto.text = "S/ 0.00"
            binding.tvMonth.text = if (arrayMonth.indexOf(binding.tvMonth.text) + 1 == 12) "Diciembre"
            else arrayMonth[arrayMonth.indexOf(binding.tvMonth.text) + 1 ]
            for (x in meses){
                if (binding.tvMonth.text == x.nombre){
                    mesActual=  x.numero
                }
            }
            viewModel.getTransaccionesGroup(mesActual,binding.tvYear.text.toString()).observe(viewLifecycleOwner, androidx.lifecycle.Observer {

                binding.recyclerviewTransacciones.adapter = TransaccionAdapter(it, viewLifecycleOwner,viewModel,txtIngresosTot,txtGastoTot,txtSaldoNeto)

            })


        }






    }


}