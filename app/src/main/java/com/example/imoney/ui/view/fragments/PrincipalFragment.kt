package com.example.imoney.ui.view.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.imoney.databinding.FragmentPrincipalBinding
import com.example.imoney.ui.view.adapters.TransaccionAdapter
import com.example.imoney.ui.viewmodel.FrTransaccionViewModel
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class PrincipalFragment : Fragment() {

    private val viewModel: FrTransaccionViewModel by viewModels()
    private val sdfMonthLetter = SimpleDateFormat("MMM")
    private val sdfMonthNumber = SimpleDateFormat("MM")
    private val sdfYearNumber = SimpleDateFormat("yyyy")

    private var _binding: FragmentPrincipalBinding? =null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        _binding = FragmentPrincipalBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPieChart()

        binding.collapsingToolbar.setExpandedTitleColor(Color.TRANSPARENT)


        binding.tvFecha.text = sdfMonthLetter.format(Date()).toString()

        viewModel.getAllTransacciones().observe(viewLifecycleOwner, Observer {
            var totIngreso =0.00
            var totGasto = 0.00
            for (x in it) {

                if (x.tipo == "ingreso") {
                    totIngreso += x.monto

                }
                if (x.tipo == "gasto") {
                    totGasto += x.monto
                }
                binding.tvSaldo.text = "S/ " + String.format("%.2f", totIngreso - totGasto)
                binding.collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE)
                binding.collapsingToolbar.title = "Su saldo actual es :"+"S/ " + String.format("%.2f", totIngreso - totGasto)
            }
        })
      viewModel.getTransaccionesWithMonthAndYear(sdfMonthNumber.format(Date()),sdfYearNumber.format(Date())).observe(viewLifecycleOwner, Observer
      {
          var totIngreso =0.00
          var totGasto = 0.00

          for (x in it.indices) {


              if (it[x].tipo == "ingreso") {
                  totIngreso += it[x].monto
              }
              if (it[x].tipo == "gasto") {
                  totGasto += it[x].monto
              }
              binding.tvIngresos.text = "S/" + String.format("%.2f", totIngreso).toString()
              binding.tvGastos.text = "S/ " + String.format("%.2f", totGasto).toString()
          }

        })


        viewModel.getTransaccionesWithCategory(sdfMonthNumber.format(Date()),sdfYearNumber.format(Date())).observe(viewLifecycleOwner, Observer{

            var arrayPieEntriesGastos = arrayListOf<PieEntry>()
            var arrayPieEntriesIngresos = arrayListOf<PieEntry>()

            for (x in it) {


                if (x.tipo == "ingreso") {
                    arrayPieEntriesIngresos.add(PieEntry(x.monto,x.categoria))
                }
                if (x.tipo == "gasto") {
                    arrayPieEntriesGastos.add(PieEntry(x.monto,x.categoria))
                }

            }

            loaPieChartData(arrayPieEntriesIngresos,arrayPieEntriesGastos)

        })
    }

    //GRAFICA GASTOS POR CATEGORIA
    fun setupPieChart(){
        //Gastos
        binding.chartPieGastos.isDrawHoleEnabled = true
        binding.chartPieGastos.setUsePercentValues(false)
        binding.chartPieGastos.setEntryLabelTextSize(15f)
        binding.chartPieGastos.setDrawEntryLabels(false)
        binding.chartPieGastos.extraLeftOffset = 0f //posicion pieChart

        //binding.chartPieGastos.centerText = "Spending by Caterogry"
        //binding.chartPieGastos.setCenterTextSize(24f)
        binding.chartPieGastos.description.isEnabled = false

        var legendGastos = binding.chartPieGastos.legend
        legendGastos.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        legendGastos.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        legendGastos.xOffset = 8f //posicion barra de letras
        legendGastos.orientation = Legend.LegendOrientation.HORIZONTAL
        legendGastos.setDrawInside(false)
        legendGastos.isEnabled = true
        legendGastos.xEntrySpace = 20f
        legendGastos.textSize = 14f

        //Ingresos
        binding.chartPieIngresos.isDrawHoleEnabled = true // activar grafica con agujero al centro
        binding.chartPieIngresos.setHoleColor(Color.WHITE)
        binding.chartPieIngresos.setTransparentCircleColor(Color.WHITE)
        binding.chartPieIngresos.setTransparentCircleAlpha(110)
        binding.chartPieIngresos.setUsePercentValues(false)
        binding.chartPieIngresos.setEntryLabelTextSize(15f)
        binding.chartPieIngresos.setDrawEntryLabels(false)
        binding.chartPieIngresos.extraLeftOffset = 0f //posicion pieChart

        //binding.chartPieGastos.centerText = "Spending by Caterogry"
        //binding.chartPieGastos.setCenterTextSize(24f)
        binding.chartPieIngresos.description.isEnabled = false

        var legendIngresos = binding.chartPieIngresos.legend
        legendIngresos.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        legendIngresos.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        legendIngresos.orientation = Legend.LegendOrientation.HORIZONTAL
        legendIngresos.xOffset = 10f
        legendIngresos.setDrawInside(false)
        legendIngresos.isEnabled = true
        legendIngresos.xEntrySpace = 10f
        legendIngresos.textSize = 14f
    }

    fun loaPieChartData(pieEntriesIngresos: List<PieEntry>,pieEntriesGastos: List<PieEntry>){


        if(pieEntriesGastos.isEmpty()) {
            binding.chartPieGastos.visibility = View.GONE
        }

        if(pieEntriesIngresos.isEmpty()) {
            binding.chartPieIngresos.visibility = View.GONE
        }

        var colors = arrayListOf<Int>()
        for (color:Int in ColorTemplate.MATERIAL_COLORS){
            colors.add(color)
        }
        for (color:Int in ColorTemplate.MATERIAL_COLORS){
            colors.add(color)
        }
        var dataSetIngresos = PieDataSet(pieEntriesIngresos,"")
        dataSetIngresos.setColors(colors)
        dataSetIngresos.sliceSpace = 2f

        var dataIngresos = PieData(dataSetIngresos)
        dataIngresos.setDrawValues(true)
        dataIngresos.setValueFormatter(PercentFormatter(binding.chartPieIngresos))
        dataIngresos.setValueTextSize(14f)
        dataIngresos.setValueTextColor(Color.WHITE)

        var dataSetGastos = PieDataSet(pieEntriesGastos,"")
        dataSetGastos.setColors(colors)
        dataSetGastos.sliceSpace = 2f


        var dataGastos = PieData(dataSetGastos)
        dataGastos.setDrawValues(true)
        dataGastos.setValueFormatter(PercentFormatter(binding.chartPieGastos))
        dataGastos.setValueTextSize(14f)
        dataGastos.setValueTextColor(Color.WHITE)

        //CHART INGRESOS

        binding.chartPieIngresos.data =dataIngresos
        binding.chartPieIngresos.invalidate()
        binding.chartPieIngresos.animateY(1400,Easing.EaseOutQuad)

        binding.chartPieGastos.data =dataGastos
        binding.chartPieGastos.invalidate()
        binding.chartPieGastos.animateY(1400,Easing.EaseOutQuad)


    }

}