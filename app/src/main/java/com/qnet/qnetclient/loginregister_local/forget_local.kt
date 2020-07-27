package com.qnet.qnetclient.loginregister_local

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth

import com.qnet.qnetclient.R
import kotlinx.android.synthetic.main.fragment_forget.*
import kotlinx.android.synthetic.main.fragment_login_register.buttonNext


/**
 * A simple [Fragment] subclass.
 */
class forget_local : Fragment() {

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

        if(eMail.isNotEmpty()) {
            FirebaseAuth.getInstance().sendPasswordResetEmail(eMail)
                .addOnCompleteListener{ task ->
                    if (task.isSuccessful) {
                        findNavController().navigate(R.id.forget_action_local)
                    } else {
                        Toast.makeText(activity, "Error Email No Existe", Toast.LENGTH_SHORT).show()
                    }
                }
        }


    }

}
