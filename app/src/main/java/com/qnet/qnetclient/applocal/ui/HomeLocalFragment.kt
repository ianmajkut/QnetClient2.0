package com.qnet.qnetclient.applocal.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import com.qnet.qnetclient.R
import com.qnet.qnetclient.applocal.AppLocal
import com.qnet.qnetclient.appusuario.AppUser
import kotlinx.android.synthetic.main.fragment_home_local.*

class HomeLocalFragment : Fragment() {

    private var backPressedTime: Long = 0
    private lateinit var backToast: Toast

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_local, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        filalocal.setOnClickListener {
            findNavController().navigate(R.id.action_homeLocalFragment_to_filaLocalFragment)
        }
        lectorcodigoQR.setOnClickListener {
            findNavController().navigate(R.id.action_homeLocalFragment_to_lectorQr_Fragment)
        }
        informacionlocal.setOnClickListener {
            findNavController().navigate(R.id.action_homeLocalFragment_to_infoLocal_Fragment)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            if (backPressedTime + 2000 > System.currentTimeMillis()) {
                val intent = Intent(requireContext(), AppLocal::class.java)
                startActivity(intent)
                backToast.cancel()
                requireActivity().moveTaskToBack(true)
                requireActivity().finish()
            } else {
                backToast = Toast.makeText(
                    requireContext(),
                    "Presione nuevamente \"Atrás\" para salir",
                    Toast.LENGTH_SHORT
                )
                backToast.show()
            }
            backPressedTime = System.currentTimeMillis()
        }
    }
}