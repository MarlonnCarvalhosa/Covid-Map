package com.marlonncarvalhosa.covidmap.view.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.marlonncarvalhosa.covidmap.databinding.FragmentOnBoardingBinding
import com.marlonncarvalhosa.covidmap.dialog.DialogLogin
import com.marlonncarvalhosa.covidmap.view.MapsActivity

class OnBoardingFragment : Fragment() {

    private var binding: FragmentOnBoardingBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOnBoardingBinding.inflate(inflater, container, false)
        onClick()
        return binding?.root
    }

    private fun onClick() {
        binding?.btnLoginGoogle?.setOnClickListener {
            val dialog = DialogLogin()
            if (dialog.isCancelable){
                dialog.onDestroy()
            }
            dialog.show(parentFragmentManager, "DialogLogin")
        }

        binding?.btnEnterNotLogin?.setOnClickListener {
            startActivity(Intent(requireContext(), MapsActivity::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}