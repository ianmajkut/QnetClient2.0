package com.qnet.qnetclient.loginregister_usuario

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController

import com.qnet.qnetclient.R
import com.qnet.qnetclient.viewModel.FirestoreViewModel
import kotlinx.android.synthetic.main.fragment_login_register.*

class login_register : Fragment() {
    private lateinit var viewModel: FirestoreViewModel
    private var loadingDialog: Dialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_register, container, false)
    }

    override fun onViewCreated (view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = FirestoreViewModel()
            buttonNew.setOnClickListener {
                findNavController().navigate(R.id.next_action)
            }
            buttonForget.setOnClickListener {
                findNavController().navigate(R.id.forget_action)
            }
            buttonNext.setOnClickListener {
                login()
            }
    }

    private fun login() {
        val name = edtxt_eMail.text.toString().trim()
        val password = edtxt_Password.text.toString().trim()

        if (name.isNotEmpty() && password.isNotEmpty()) {

            //Aca progress bar
            showLoading()
            Handler().postDelayed({
                hideLoading()

            }, 5000)
           obsever(name, password)

        } else {
            Toast.makeText(activity, "Error Campos Incompletos", Toast.LENGTH_SHORT).show()
        }
    }
    private fun obsever(name:String,password:String)
    {
        viewModel.singInUser(name,password).observeForever{
            if(it) {
                Toast.makeText(activity, "Ok", Toast.LENGTH_SHORT).show()
                viewModel.localesCercanos()

                //aca termina
                findNavController().navigate(R.id.menu_principal_action)
            }else{
                Toast.makeText(activity, "Usuario no Registrado", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun hideLoading(){
        loadingDialog?.let { if (it.isShowing)it.cancel() }
    }

    private fun showLoading(){
        hideLoading()
        loadingDialog=CommonUtils.showLoadingDialog(requireContext())
    }

}
