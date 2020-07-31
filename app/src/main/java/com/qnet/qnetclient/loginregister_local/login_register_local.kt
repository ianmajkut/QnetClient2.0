package com.qnet.qnetclient.loginregister_local

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController

import com.qnet.qnetclient.R
import com.qnet.qnetclient.loginregister_usuario.CommonUtils
import com.qnet.qnetclient.viewModel.FirestoreViewModel
import kotlinx.android.synthetic.main.fragment_login_register.*
import kotlinx.android.synthetic.main.fragment_login_register_local.*
import kotlinx.android.synthetic.main.fragment_login_register_local.buttonForget
import kotlinx.android.synthetic.main.fragment_login_register_local.buttonNew
import kotlinx.android.synthetic.main.fragment_login_register_local.buttonNext


class login_register_local : Fragment() {

    private lateinit var viewModel: FirestoreViewModel
    private var loadingDialog: Dialog? = null
    private var rememberMe: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_register_local, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = FirestoreViewModel()
            buttonNew.setOnClickListener{
                findNavController().navigate(R.id.next_action_local)
            }
            buttonForget.setOnClickListener{
                findNavController().navigate(R.id.forget_action_local)
            }
            buttonNext.setOnClickListener{
                login()
            }
    }

    private fun login() {
        val name = edtxt_UserLocal.text.toString().trim()
        val password = edtxt_PasswordLocal.text.toString().trim()

        if (name.isNotEmpty() && password.isNotEmpty()) {
            //@Ian falta poner un progress bar para ver el progreso
            showLoading()
            obsever(name, password)
        } else {
            Toast.makeText(activity, "Error Campos Incompletos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun hideLoading(){
        loadingDialog?.let { if (it.isShowing)it.cancel() }
    }

    private fun showLoading(){
        hideLoading()
        loadingDialog = CommonUtils.showLoadingDialog(requireContext())
    }

    private fun obsever(name:String,password:String) {
        viewModel.singInUser(name,password).observeForever{

            when(it){
                0 ->{ Toast.makeText(activity, "Usuario no Registrado", Toast.LENGTH_SHORT).show()
                    hideLoading()}
                1 ->{ Toast.makeText(activity, "Esta intentando entrar con un Usuario", Toast.LENGTH_SHORT).show()
                    hideLoading()}
                2 -> findNavController().navigate(R.id.menu_principal_action_local)
            }

        }
    }

}
