package com.qnet.qnetclient.loginregister_local

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController

import com.qnet.qnetclient.R
import com.qnet.qnetclient.loginregister_usuario.CommonUtils
import com.qnet.qnetclient.loginregister_usuario.registerDirections
import com.qnet.qnetclient.viewModel.FirestoreViewModel
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register_local.*
import kotlinx.android.synthetic.main.fragment_register_local.back_icon
import kotlinx.android.synthetic.main.fragment_register_local.buttonNext


/**
 * A simple [Fragment] subclass.
 */
class register_local : Fragment() {

    private lateinit var viewModel: FirestoreViewModel
    private var loadingDialog: Dialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register_local, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = FirestoreViewModel()
        buttonNext.setOnClickListener{
            loadUser()
        }
        back_icon.setOnClickListener{
            findNavController().navigate(R.id.back_action_local)
        }

    }

    private fun hideLoading(){
        loadingDialog?.let { if (it.isShowing)it.cancel() }
    }

    private fun showLoading(){
        hideLoading()
        loadingDialog = CommonUtils.showLoadingDialog(requireContext())
    }

    fun loadUser() {
        Log.i("Verif", "loadUser() register.kt")
        val eMail = edtxt_EmailLocal.text.toString().trim()
        val password = edtxt_PasswordLocal.text.toString().trim()

        if (eMail.isNotEmpty() && password.isNotEmpty()) {
            showLoading()
            crearUsuario(eMail, password)
        } else {
            Toast.makeText(activity, "Error falta algun campo", Toast.LENGTH_SHORT).show()
        }
    }
    fun crearUsuario(eMail:String,password:String){
        viewModel.createUser(eMail, password)
            .observeForever(){
            if(it){
                hideLoading()
                findNavController().navigate(R.id.next_action_local)
                Toast.makeText(activity, "Ok", Toast.LENGTH_SHORT).show()
            }else{
                hideLoading()
                Toast.makeText(activity, "Error al crear usuario", Toast.LENGTH_SHORT).show()

            }
        }

    }

}
