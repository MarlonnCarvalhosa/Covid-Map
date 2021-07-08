package com.marlonncarvalhosa.covidmap.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.marlonncarvalhosa.covidmap.R
import com.marlonncarvalhosa.covidmap.databinding.FragmentFirstSymptomSessionBinding

class FirstSymptomSessionFragment: Fragment(), View.OnClickListener {

    private var binding : FragmentFirstSymptomSessionBinding? = null // inicializar como null para utilizar o null safety e garantir que a aplicação não vá quebrar caso alguma view seja nula

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): ConstraintLayout? {
        binding = FragmentFirstSymptomSessionBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
        binding?.cvFirstNext?.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.quiz_container, SecondSymptomSessionFragment())
                .addToBackStack("first")
                .commit()
        }
        binding?.cvFirstBack?.setOnClickListener { parentFragmentManager.popBackStack("start", 1) }
    }
    override fun onClick(v: View?) {
        when (v?.id) {
            binding?.buttonPositiveCovidTest?.id -> {
                Log.d("teste", "positive click")
                Toast.makeText(context, "Positive click", Toast.LENGTH_LONG).show()
            }
            binding?.buttonNegativeCovidTest?.id -> {
                Log.d("teste", "negative click")
                Toast.makeText(context, "negative click", Toast.LENGTH_LONG).show()
            }
            else -> { }
        }
    }
    private fun setClickListeners() {
        binding?.buttonPositiveCovidTest?.setOnClickListener(this)
        binding?.buttonNegativeCovidTest?.setOnClickListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null //retirar a referencia de view binding para evitar memory leak
    }
}