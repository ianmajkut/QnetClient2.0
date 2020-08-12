package com.qnet.qnetclient.loginregister_local

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
import kotlinx.android.synthetic.main.fragment_forget.*
import kotlinx.android.synthetic.main.fragment_login_register.buttonNext


/**
 * A simple [Fragment] subclass.
 */
class forget_local : Fragment() {

    private var loadingDialog: Dialog? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forget_local, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonNext.setOnClickListener{
            reestablecerPassword()
        }
        back_icon.setOnClickListener{
            findNavController().navigate(R.id.forget_back_action_local)
        }

    }

    private fun reestablecerPassword() {

        val eMail = edtxt_eMailReestablecer.text.toString().trim()
        showLoading()
        if(eMail.isNotEmpty()) {
            FirebaseAuth.getInstance().sendPasswordResetEmail(eMail)
                .addOnCompleteListener{ task ->
                    if (task.isSuccessful) {
                        hideLoading()
                        findNavController().navigate(R.id.forget_action_local)
                    } else {
                        hideLoading()
                        Toast.makeText(activity, "Error Email No Existe", Toast.LENGTH_SHORT).show()
                    }
                }
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
