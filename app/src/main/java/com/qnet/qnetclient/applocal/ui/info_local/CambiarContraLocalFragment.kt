package com.qnet.qnetclient.applocal.ui.info_local

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.qnet.qnetclient.R
import com.qnet.qnetclient.loginregister_usuario.CommonUtils
import kotlinx.android.synthetic.main.fragment_cambiar_contra_local.*
import kotlinx.android.synthetic.main.fragment_cambiar_contra_local.back_icon
import kotlinx.android.synthetic.main.fragment_cambiar_contra_local.buttonNext
import kotlinx.android.synthetic.main.fragment_cambiar_mail_local.*


class CambiarContraLocalFragment : Fragment() {

    private lateinit var mAuth: FirebaseAuth
    private var loadingDialog: Dialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cambiar_contra_local, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        back_icon.setOnClickListener {

            findNavController().navigate(R.id.action_cambiarNombreLocalFragment_to_infoLocal_Fragment)

        }
        buttonNext.setOnClickListener {
            changePassword()
        }

    }

    private fun hideLoading(){
        loadingDialog?.let { if (it.isShowing)it.cancel() }
    }

    private fun showLoading(){
        hideLoading()
        loadingDialog = CommonUtils.showLoadingDialog(requireContext())
    }

    fun changePassword(){
        val password = edtxt_ContraseñaLocal.text.toString()
        showLoading()
        if(password.isNotEmpty()&&password.length>6){
            mAuth.currentUser?.updatePassword(password)?.addOnSuccessListener {
                hideLoading()
                findNavController().navigate(R.id.action_cambiarContraLocalFragment_to_verificarNuevaContraLocalFragment)
            }
        }else{
            hideLoading()
            Toast.makeText(activity, "La contraseña debe tener más de 6 caracteres ", Toast.LENGTH_SHORT).show()
            //la contra debe de ser de más de 6
        }
    }

}