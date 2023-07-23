package com.example.imoney.ui.view.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable

import android.os.Bundle
import android.view.*
import android.widget.Button

import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imoney.R
import com.example.imoney.data.clases.DatePicker
import com.example.imoney.data.entity.Prestamo
import com.example.imoney.data.entity.Transaccion
import com.example.imoney.databinding.DialogRegistroIngresosBinding
import com.example.imoney.formatPriceToFloat
import com.example.imoney.formatPriceToString
import com.example.imoney.ui.view.adapters.PrestamosAdapter
import com.example.imoney.ui.viewmodel.FrIngresoViewModel
import com.example.imoney.ui.viewmodel.FrTransaccionViewModel
import java.text.SimpleDateFormat
import java.util.*

class DialogRegistroIngreso: DialogFragment() , PrestamosAdapter.RecyclerItemClick {

    // Get the view model
    private val model: FrIngresoViewModel by viewModels()
    private val viewModel: FrTransaccionViewModel by viewModels()
    private lateinit var bottomSheetDialog:Dialog
    private var idPrestamo = ""
    private var totalPrestamo = 0f
    private val sdfDate = SimpleDateFormat("yyyy/MM/dd")

    val TAG = "FullScreenDialog"
    var numeroDigitado: String = ""


    private var _binding: DialogRegistroIngresosBinding? =null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogRegistroIngresosBinding.inflate (inflater,container,false)
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
        //RELLANA CAMPOS SI SE VA ACTUALIZAR
        var idTransaction = arguments?.getString("id")
        var saldo = arguments?.getString("saldo")
        var switch = arguments?.getBoolean("switch")
        var fecha = if(arguments?.getString("fecha").isNullOrEmpty()) sdfDate.format(Date()).toString() else arguments?.getString("fecha").toString()
        var descripcion = arguments?.getString("descripcion")
        var categoria = arguments?.getString("categoria")
        var monto = arguments?.getString("monto")
        var persona = arguments?.getString("persona")

       /* val navController = findNavController();
        // We use a String here, but any type that can be put in a Bundle is supported
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<String>("persona")?.observe(
            viewLifecycleOwner) { result ->
            binding.tvPersona.text = result.toString()
        }*/

        // CONDITION  PARA INICIALIZAR CAMPOS UPDATE O INSERT

        if (idTransaction == null){
            binding.tvSaldo.text = "0.00"
            binding.tvFecha.text = fecha
            binding.buttonCategoria.text = "otro"
            binding.buttonCategoria.isEnabled = true
            binding.containerBtnCategoria.isEnabled = true
            binding.containerPrestamos.visibility = View.GONE
        }
        else{
            binding.tvSaldo.text = saldo
            binding.tvFecha.text = fecha
            binding.tvDescripcion.setText(descripcion)
            binding.buttonCategoria.text = categoria
            binding.tvMonto.text = monto
            binding.tvPersona.text = persona
            if (switch == true) {
                binding.switchPrestamo.isChecked = switch
                binding.buttonCategoria.text = "Prestamo"
                binding.containerBtnCategoria.isEnabled = false
                binding.containerPrestamos.visibility = View.VISIBLE
            }
        }


        binding.tvSaldo.setOnClickListener {
            showBottomSheetNumeros()
        }

        binding.containerBtnCategoria.setOnClickListener {
            showBottomSheetCategoria()
        }

        // EVENTO SWITCH PRESTAMO TRUE O FALSE
        binding.switchPrestamo.setOnCheckedChangeListener { buttonView, isChecked ->
            if (buttonView.isChecked){
                binding.buttonCategoria.text = "Prestamo"
                binding.buttonCategoria.isEnabled = false
                binding.containerBtnCategoria.isEnabled = false
                binding.containerPrestamos.visibility = View.VISIBLE
            }
            else{
                binding.buttonCategoria.text = "otro"
                binding.buttonCategoria.isEnabled = true
                binding.containerBtnCategoria.isEnabled = true
                binding.containerPrestamos.visibility = View.GONE
            }
        }

        //EVENTO SHOW DIALOG PRESTAMOS

        binding.containerPrestamos.setOnClickListener {
            showBottomSheetPrestamos()
        }

        //EVENTO SHOW DATEPICKER
        binding.tvFecha.setOnClickListener {
            val fecha = DatePicker{ year, month, day -> monstrarDate(year,month,day) }
            fecha.show(this.childFragmentManager,"")
        }


        // EVENTO  SAVE  OR TRANSACTION AND UPDATE PRESTAMO

        binding.btnGuardar.setOnClickListener {
            var uniqueID = UUID.randomUUID().toString()
            if (idTransaction == null){
                saveTransaction(uniqueID)
                updatePrestamo(idPrestamo)
            }

            if (idTransaction != null) {
                updateTransaction(idTransaction)
                updatePrestamo(idPrestamo)

            }

            dialog?.dismiss()

        }
        binding.btnBack.setOnClickListener{
            this.dismiss()
        }

    }

    fun saveTransaction( id: String){
            model.insert(
                Transaccion(id,binding.tvDescripcion.text.toString(),
                binding.tvPersona.text.toString(),
                binding.buttonCategoria.text.toString(),
                "0","ingreso",
                formatPriceToFloat(binding.tvSaldo.text.toString()),
                sdfDate.parse(binding.tvFecha.text.toString()))
            )
    }

    fun updateTransaction(id: String){


        viewModel.updateTransaction(
            Transaccion(id,binding.tvDescripcion.text.toString(),
            binding.tvPersona.text.toString(),
            binding.buttonCategoria.text.toString(),
            "0","ingreso",
                formatPriceToFloat(binding.tvSaldo.text.toString()),
            sdfDate.parse(binding.tvFecha.text.toString()))
        )
    }

    fun updatePrestamo(id: String){

        if (binding.switchPrestamo.isChecked){
            viewModel.updatePrestamo(
                Prestamo(id,binding.tvDescripcion.text.toString(),
                binding.tvPersona.text.toString(),
                binding.buttonCategoria.text.toString(),
                "0",binding.tvSaldo.text.toString().toFloat(),totalPrestamo)
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
        dialog?.setContentView(R.layout.bottom_sheet_dialog_categorias_ingresos)

        dialog?.show()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog?.window?.setGravity(Gravity.BOTTOM)

        var opSueldo = dialog.findViewById<TextView>(R.id.tvSueldo)
        var opPremio  = dialog.findViewById<TextView>(R.id.tvPremio)
        var opInversiones  = dialog.findViewById<TextView>(R.id.tvInversiones)
        var opRegalo  = dialog.findViewById<TextView>(R.id.tvRegalo)
        var opPrestamo  = dialog.findViewById<TextView>(R.id.tvPrestamo)
        var opOtro  = dialog.findViewById<TextView>(R.id.tvOtro)

        opSueldo.setOnClickListener {
            binding.buttonCategoria.text =  opSueldo.text.toString()
            dialog.dismiss()
        }
        opPremio.setOnClickListener {
            binding.buttonCategoria.text =  opPremio.text.toString()
            dialog.dismiss()
        }
        opInversiones.setOnClickListener {
            binding.buttonCategoria.text =  opInversiones.text.toString()
            dialog.dismiss()
        }
        opRegalo.setOnClickListener {
            binding.buttonCategoria.text =  opRegalo.text.toString()
            dialog.dismiss()
        }

        opPrestamo.setOnClickListener {
            binding.buttonCategoria.text = opPrestamo.text.toString()
            dialog.dismiss()
        }
        opOtro.setOnClickListener {
            binding.buttonCategoria.text = opOtro.text.toString()
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
            if (numeroDigitado.isNullOrEmpty()) dialog.dismiss() else binding.tvSaldo.text = formatPriceToString(numeroDigitado.toFloat())
            dialog.dismiss()
        }
        btnCancelar.setOnClickListener{
            dialog.dismiss()
        }
    }

    fun showBottomSheetPrestamos(){
        bottomSheetDialog = Dialog(requireContext())
        bottomSheetDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        bottomSheetDialog?.setContentView(R.layout.bottom_sheet_dialog_prestamos )

        bottomSheetDialog?.show()
        bottomSheetDialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        bottomSheetDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        bottomSheetDialog?.window?.attributes?.windowAnimations = R.style.DialogAnimation
        bottomSheetDialog?.window?.setGravity(Gravity.BOTTOM)

        var recyclerView = bottomSheetDialog.findViewById<RecyclerView>(R.id.recyclerviewTransacciones)

// Specify layout for recycler view
        recyclerView?.setHasFixedSize(true)
        val gridLayoutManager = GridLayoutManager(
            this.context,1)
        recyclerView?.layoutManager = gridLayoutManager

        viewModel.getAllPrestamos().observe(viewLifecycleOwner, androidx.lifecycle.Observer {

            recyclerView.adapter = PrestamosAdapter(it,this)

        })


    }

    override fun itemClick(item: Prestamo) {
        binding.tvPersona.text = item.persona
        totalPrestamo = item.total
        binding.tvMonto.text = String.format("%.2f",(item.total - item.monto))
        binding.tvCategoria.text = item.categoria

        idPrestamo = item.id

        bottomSheetDialog?.dismiss()
    }


}