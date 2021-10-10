package com.example.imoney.ui.view.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.imoney.R
import com.example.imoney.data.clases.DatePicker
import com.example.imoney.data.entity.Prestamo
import com.example.imoney.data.entity.Transaccion
import com.example.imoney.databinding.DialogRegistroGastoBinding
import com.example.imoney.ui.viewmodel.FrTransaccionViewModel
import java.text.SimpleDateFormat
import java.util.*

class DialogRegistroGasto: DialogFragment() {

    // Get the view model
    private val model: FrTransaccionViewModel by viewModels()
    private val viewModel: FrTransaccionViewModel by viewModels()
    val TAG = "FullScreenDialog"
    var numeroDigitado = ""
    val sdfDate = SimpleDateFormat("yyyy/MM/dd")

    private var _binding: DialogRegistroGastoBinding? =null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = DialogRegistroGastoBinding.inflate (inflater,container,false)
        return binding.root
    }
    override fun getTheme(): Int {
        return R.style.DialogTheme
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {


        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog


    }
    override fun onStart() {
        super.onStart()
        if (binding.tvSaldo.text == "0.00"){
            showBottomSheetNumeros()
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var idTransaction = arguments?.getString("id")
        var saldo = arguments?.getString("saldo")
        var switch = arguments?.getBoolean("switch")
        var fecha = arguments?.getString("fecha")
        var descripcion = arguments?.getString("descripcion")
        var categoria = arguments?.getString("categoria")
        var persona = arguments?.getString("persona")

        // CONDITION  PARA INICIALIZAR CAMPOS UPDATE O INSERT

        if (idTransaction == null){
            binding.tvSaldo.text = "0.00"
            binding.buttonCategoria.text = "otro"
            binding.buttonCategoria.isEnabled = true
            binding.containerBtnCategoria.isEnabled = true
            binding.containerPersona.visibility = View.GONE
        }
        else{

            binding.tvSaldo.text = saldo
            binding.tvFecha.text = fecha
            binding.etDescripcion.setText(descripcion)
            binding.buttonCategoria.text = categoria
            binding.etPersona.setText(persona)
            if (switch == true) {
                binding.switchPrestamo.isChecked = switch
                binding.buttonCategoria.text = "Prestamo"
                binding.containerBtnCategoria.isEnabled = false
                binding.containerPersona.visibility = View.VISIBLE
            }
        }


        binding.tvSaldo.setOnClickListener {

            showBottomSheetNumeros()
        }

        binding.containerBtnCategoria.setOnClickListener {

            showBottomSheetCategoria()

        }


        //EVENTO SHOW DATEPICKER
        binding.tvFecha.setOnClickListener {

            val fecha = DatePicker{ year, month, day -> monstrarDate(year,month,day) }
            fecha.show(this.childFragmentManager,"")
        }


        //  EVENTO SWITCH PRESTAMO TRUE O FALSE
        binding.switchPrestamo.setOnCheckedChangeListener { buttonView, isChecked ->

            if (buttonView.isChecked){
                binding.buttonCategoria.text = "Prestamo"
                binding.buttonCategoria.isEnabled = true
                binding.containerBtnCategoria.isEnabled = false
                binding.containerPersona.visibility = View.VISIBLE


            }
            else{
                binding.buttonCategoria.text = "otro"
                binding.buttonCategoria.isEnabled = true
                binding.containerBtnCategoria.isEnabled = true
                binding.containerPersona.visibility = View.GONE
            }
        }


        binding.btnGuardar.setOnClickListener {
            var uniqueID = UUID.randomUUID().toString()
            if (idTransaction == null){
                saveTransaction(uniqueID)
                savePrestamo(uniqueID)
            }

            if (idTransaction != null) {
                updateTransaction(idTransaction)
                updatePrestamo(idTransaction)

            }

            dialog?.dismiss()
        }
        binding.btnBack.setOnClickListener{
            this.dismiss()
        }

    }



    fun saveTransaction( id: String){


        model.insert(
            Transaccion(id,binding.etDescripcion.text.toString(),
            binding.etPersona.text.toString(),
            binding.buttonCategoria.text.toString(),
            "0","gasto",
            String.format("%.2f",binding.tvSaldo.text.toString().toFloat()).toFloat(),
            sdfDate.parse(binding.tvFecha.text.toString()))
        )


    }
    fun updateTransaction(id: String){

        viewModel.updateTransaction(
            Transaccion(id,binding.etDescripcion.text.toString(),
            binding.etPersona.text.toString(),
            binding.buttonCategoria.text.toString(),
            "0","gasto",
            String.format("%.2f",binding.tvSaldo.text.toString().toFloat()).toFloat(),
            sdfDate.parse(binding.tvFecha.text.toString()))
        )
    }

    fun savePrestamo(id: String){

        if (binding.switchPrestamo.isChecked){
            viewModel.insertPrestamo(
                Prestamo(id,binding.etDescripcion.text.toString(),
                binding.etPersona.text.toString(),
                binding.buttonCategoria.text.toString(),
                "0",0f,binding.tvSaldo.text.toString().toFloat())
            )

        }
    }
    fun updatePrestamo(id: String){
        if (binding.switchPrestamo.isChecked){
            viewModel.updatePrestamo(
                Prestamo(id,binding.etDescripcion.text.toString(),
                binding.etPersona.text.toString(),
                binding.buttonCategoria.text.toString(),
                "0",0f,binding.tvSaldo.text.toString().toFloat())
            )

        }
    }

    fun monstrarDate(year: Int, month: Int, day: Int){
        var daycadena=""
        var monthCadena = ""
        if(day<=9){
            daycadena="0"+day;

        }
        else{
            daycadena=day.toString()
        }

        if(month<=9){
            monthCadena="0"+month
        }
        else{

            monthCadena=month.toString()
        }

        binding.tvFecha.setText("$year/$monthCadena/$daycadena")
    }

    fun showBottomSheetCategoria(){
        var dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.setContentView(R.layout.bottom_sheet_dialog_categorias_gasto)

        dialog?.show()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog?.window?.setGravity(Gravity.BOTTOM)

        var opEducacion = dialog.findViewById<TextView>(R.id.tvEducacion)
        var opLuz = dialog.findViewById<TextView>(R.id.tvLuz)
        var opAgua  = dialog.findViewById<TextView>(R.id.tvAgua)
        var opInternet  = dialog.findViewById<TextView>(R.id.tvInternet)
        var opSupermercado = dialog.findViewById<TextView>(R.id.tvSupermercado)

        opEducacion.setOnClickListener {
            binding.buttonCategoria.text =  opEducacion.text.toString()
            dialog.dismiss()
        }
        opLuz.setOnClickListener {
            binding.buttonCategoria.text =  opLuz.text.toString()
            dialog.dismiss()
        }
        opAgua.setOnClickListener {
            binding.buttonCategoria.text =  opAgua.text.toString()
            dialog.dismiss()
        }
        opInternet.setOnClickListener {
            binding.buttonCategoria.text =  opInternet.text.toString()
            dialog.dismiss()
        }
        opSupermercado.setOnClickListener {
            binding.buttonCategoria.text =  opSupermercado.text.toString()
            dialog.dismiss()
        }
    }

    fun showBottomSheetNumeros(){
        var dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.setContentView(R.layout.bottom_sheet_dialog_numeros)

        dialog?.show()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog?.window?.setGravity(Gravity.BOTTOM)
        dialog.setOnDismissListener {
            numeroDigitado = ""
        }


        val btn0 = dialog.findViewById<Button>(R.id.btn0)
        val btn1 = dialog.findViewById<Button>(R.id.btn1)
        val btn2 = dialog.findViewById<Button>(R.id.btn2)
        val btn3 = dialog.findViewById<Button>(R.id.btn3)
        val btn4 = dialog.findViewById<Button>(R.id.btn4)
        val btn5 = dialog.findViewById<Button>(R.id.btn5)
        val btn6 = dialog.findViewById<Button>(R.id.btn6)
        val btn7 = dialog.findViewById<Button>(R.id.btn7)
        val btn8 = dialog.findViewById<Button>(R.id.btn8)
        val btn9 = dialog.findViewById<Button>(R.id.btn9)

        val btnBorrar = dialog.findViewById<ImageView>(R.id.btnBorrar)
        val btnListo = dialog.findViewById<Button>(R.id.btnListo)
        val txtNumerosDigitados = dialog.findViewById<TextView>(R.id.tvNumerosDigitados)
        val btnCancelar  = dialog.findViewById<Button>(R.id.btCancelar)


        btn0.setOnClickListener {
            numeroDigitado +=  "0"
            txtNumerosDigitados.text = numeroDigitado
        }
        btn1.setOnClickListener {
            numeroDigitado +=  "1"
            txtNumerosDigitados.text = numeroDigitado
        }
        btn2.setOnClickListener {
            numeroDigitado +=  "2"
            txtNumerosDigitados.text = numeroDigitado
        }
        btn3.setOnClickListener {
            numeroDigitado +=  "3"
            txtNumerosDigitados.text = numeroDigitado
        }
        btn4.setOnClickListener {
            numeroDigitado +=  "4"
            txtNumerosDigitados.text = numeroDigitado
        }
        btn5.setOnClickListener {
            numeroDigitado +=  "5"
            txtNumerosDigitados.text = numeroDigitado
        }
        btn6.setOnClickListener {
            numeroDigitado +=  "6"
            txtNumerosDigitados.text = numeroDigitado
        }
        btn7.setOnClickListener {
            numeroDigitado +=  "7"
            txtNumerosDigitados.text = numeroDigitado
        }
        btn8.setOnClickListener {
            numeroDigitado +=  "8"
            txtNumerosDigitados.text = numeroDigitado
        }
        btn9.setOnClickListener {
            numeroDigitado +=  "9"
            txtNumerosDigitados.text = numeroDigitado
        }

        btnBorrar.setOnClickListener {
            numeroDigitado = numeroDigitado.dropLast(1)
            txtNumerosDigitados.text = numeroDigitado
        }
        btnListo.setOnClickListener {
            binding.tvSaldo.text = String.format("%.2f",numeroDigitado.toFloat())
            dialog.dismiss()
        }
        btnCancelar.setOnClickListener{
            dialog.dismiss()
        }
    }
}