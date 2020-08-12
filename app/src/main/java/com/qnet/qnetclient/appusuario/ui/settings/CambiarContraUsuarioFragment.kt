package com.qnet.qnetclient.appusuario.ui.settings

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.qnet.qnetclient.R
import com.qnet.qnetclient.loginregister_usuario.CommonUtils
import kotlinx.android.synthetic.main.fragment_cambiar_mail_usuario.*
import kotlinx.android.synthetic.main.fragment_settings.*


class CambiarContraUsuarioFragment : Fragment() {

    private var loadingDialog: Dialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val layout= inflater.inflate(R.layout.fragment_cambiar_contra_usuario, container, false)

        return layout

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonNext.setOnClickListener {
            showLoading()

            hideLoading()

            findNavController().navigate(R.id.contra_to_verification)

        }



    }


    private fun hideLoading(){
        loadingDialog?.let { if (it.isShowing)it.cancel() }
    }

    private fun showLoading(){
        hideLoading()
        loadingDialog = CommonUtils.showLoadingDialog(requireContext())
    }


}