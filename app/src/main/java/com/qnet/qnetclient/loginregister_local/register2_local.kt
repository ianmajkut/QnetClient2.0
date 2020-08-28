package com.qnet.qnetclient.loginregister_local


import android.app.AlertDialog
import android.app.TimePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.qnet.qnetclient.R
import com.qnet.qnetclient.viewModel.FirestoreViewModel
import kotlinx.android.synthetic.main.fragment_login_register.buttonNext
import kotlinx.android.synthetic.main.fragment_register2.back_icon
import kotlinx.android.synthetic.main.fragment_register2_local.*
import java.text.SimpleDateFormat
import java.util.*


class register2_local : Fragment(){

    private lateinit var viewModel: FirestoreViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val layout= inflater.inflate(R.layout.fragment_register2_local, container, false)

        val mShowAlertDialogBtn:Button=layout.findViewById(R.id.btnHorarioDias)

        val spinner1=layout.findViewById<Spinner>(R.id.spinner1)
        val spinner2=layout.findViewById<Spinner>(R.id.spinner2)
        val spinner3=layout.findViewById<Spinner>(R.id.spinner3)
        val spinner4=layout.findViewById<Spinner>(R.id.spinner4)

        val horario= listOf("00:00","00:30","1:00","1:30","2:00","2:30","3:00","3:30",
                                            "4:00","4:30","5:00","5:30","6:00","6:30","7:00","7:30",
                                            "8:00","8:30","9:00","9:30","10:00","10:30","11:00","11:30",
                                            "12:00","12:30","13:00","13:30","14:00","14:30","15:00","15:30",
                                            "16:00","16:30","17:00","17:30","18:00","18:30","19:00","19:30",
                                            "20:00","20:30","21:00","21:30","22:00","22:30","23:00","23:30")
        val adapter=ArrayAdapter<String>(requireContext(),R.layout.support_simple_spinner_dropdown_item,horario)
        spinner1.adapter=adapter
        spinner2.adapter=adapter
        spinner3.adapter=adapter
        spinner4.adapter=adapter

        spinner1.onItemSelectedListener= object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {

            }

        }
        spinner2.onItemSelectedListener= object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {

            }

        }
        spinner3.onItemSelectedListener= object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {

            }

        }
        spinner4.onItemSelectedListener= object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {

            }

        }

        mShowAlertDialogBtn.setOnClickListener {
            alertaDia()
        }

        return layout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = FirestoreViewModel()
        buttonNext.setOnClickListener{
            loadData()
        }
        back_icon.setOnClickListener{
            findNavController().navigate(R.id.back_action_local)
        }

    }

    fun alertaDia(){

        val builder= AlertDialog.Builder(requireContext(),R.style.MyCheckBox)
        val diasArray= arrayOf("Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo")
        val checkedDiasArray= booleanArrayOf(false, false,false, false, false,false,false)
        val diasList= Arrays.asList(*diasArray)
        builder.setTitle("Seleccionar días")


        builder.setMultiChoiceItems(diasArray,checkedDiasArray){ _, which, isChecked ->
            checkedDiasArray[which]=isChecked
            val currentItem=diasList[which]
            
        }

        builder.setPositiveButton("OK") { _, _ ->
            diasDisp.text="Días disponibles: "
            for(i in checkedDiasArray.indices){
                val checked=checkedDiasArray[i]
                if(checked){
                    diasDisp.text=diasDisp.text.toString() + diasList[i] + " "
                }
            }
        }
        builder.setNeutralButton("Cancelar") { _, _ ->

        }

        val dialog=builder.create()
        dialog.show()
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK)
        dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(Color.BLACK)
    }



    private fun loadData(){
        val nombre = edtxt_nombreLocal.text.toString()
        val ubicacion= edtxt_ubicacion.text.toString()
        val horario = edtxt_horario.text.toString()
        val tipo = edtxt_tipo.text.toString()
        val informacion = edtxt_informacion.text.toString()
        val telefono = telefono.text.toString()

        if(nombre.isNotEmpty()&&ubicacion.isNotEmpty()&&horario.isNotEmpty()&&tipo.isNotEmpty()&&informacion.isNotEmpty()){
            val data = InfoRegister(nombre, ubicacion, horario, tipo, informacion, telefono)
            val action = register2_localDirections.nextActionLocal(data)
            findNavController().navigate(action)
            Toast.makeText(activity, "Ok", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(activity, "Error falta algun campo", Toast.LENGTH_SHORT).show()
        }

    }



}






