package com.marlonncarvalhosa.covidmap.view.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.marlonncarvalhosa.covidmap.databinding.FragmentPermissionBinding
import com.marlonncarvalhosa.covidmap.dialog.PoliticDialogFragment
import com.marlonncarvalhosa.covidmap.dialog.TermsDialogFragment

class PermissionFragment : Fragment() {

    private var binding: FragmentPermissionBinding? = null

    private val dialog = TermsDialogFragment()
    private val dialogPolitic = PoliticDialogFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPermissionBinding.inflate(inflater, container, false)
        onClick()
        return binding?.root
    }

    private fun onClick() {
        binding?.txtGeneralTerms?.setOnClickListener {
            dialog.show(parentFragmentManager, dialog.tag)
        }
        binding?.txtPolitic?.setOnClickListener {
            dialogPolitic.show(parentFragmentManager, dialog.tag)
        }
        binding?.btnFinishRegister?.setOnClickListener {
            if (binding?.checkboxLgdp?.isChecked!! && binding?.checkboxGeneralTerms?.isChecked!!){
                val sharedPref = activity?.getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
                val editor = sharedPref?.edit()
                editor?.apply {
                    putBoolean("permission", true)
                }?.apply()
                findNavController().navigate(PermissionFragmentDirections.actionPermissionFragmentToOnBoardingFragment())
            } else {
                Toast.makeText(requireContext(), "Para acessar o aplicativo é necessário aceitar os termos e concordar com a LGPD.", Toast.LENGTH_LONG).show()
            }
        }
    }
}