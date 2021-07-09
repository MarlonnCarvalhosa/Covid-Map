package com.marlonncarvalhosa.covidmap.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.marlonncarvalhosa.covidmap.R
import com.marlonncarvalhosa.covidmap.databinding.FragmentSecondSymptomSessionBinding

class SecondSymptomSessionFragment : Fragment(), View.OnClickListener {

    // inicializar como null para utilizar o null safety e garantir que a aplicação não vá quebrar caso alguma view seja nula
    private var binding: FragmentSecondSymptomSessionBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): ConstraintLayout? {
        binding = FragmentSecondSymptomSessionBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
        binding?.cvSecondNext?.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.quiz_container, ThirdSymptomSessionFragment())
                .addToBackStack("second")
                .commit()
        }
        binding?.cvSecondBack?.setOnClickListener { parentFragmentManager.popBackStack("first", 1) }
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }

    private fun setClickListeners() {
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null //retirar a referencia de view binding para evitar memory leak
    }
}