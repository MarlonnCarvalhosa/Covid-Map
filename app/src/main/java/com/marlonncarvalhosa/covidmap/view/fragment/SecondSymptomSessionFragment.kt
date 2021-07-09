package com.marlonncarvalhosa.covidmap.view.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
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
        when (v?.id) {
            binding?.buttonSymptomFebre?.id -> {
                Log.d("teste", "click febre")
                Toast.makeText(context, "click febre", Toast.LENGTH_SHORT).show()
                //Alterando cor no click
                binding?.buttonSymptomFebre?.backgroundTintList =
                    context?.let { ContextCompat.getColorStateList(it, R.color.primary_purple) }
                binding?.tvSymptomFebre?.setTextColor(Color.WHITE)

            }
            binding?.buttonSymptomTosse?.id -> {
                Log.d("teste", "click tosse")
                Toast.makeText(context, "click tosse", Toast.LENGTH_SHORT).show()
            }
            binding?.buttonSymptomArrepiosTremores?.id -> {
                Log.d("teste", "click arrepios/tremores")
                Toast.makeText(context, "click arrepios/tremores", Toast.LENGTH_SHORT).show()
            }
            binding?.buttonSymptomFaltaAr?.id -> {
                Log.d("teste", "click falda de ar")
                Toast.makeText(context, "click falda de ar", Toast.LENGTH_SHORT).show()
            }
            binding?.buttonSymptomPaladarOfato?.id -> {
                Log.d("teste", "click sem paladar/ofato")
                Toast.makeText(context, "click sem paladar/ofato", Toast.LENGTH_SHORT).show()
            }
            binding?.buttonNegativeSymptomSecondSession?.id -> {
                Log.d("teste", "click sem sintomas")
                Toast.makeText(context, "click sem sintomas", Toast.LENGTH_SHORT).show()
            }
            else -> {
            }
        }
    }

    private fun setClickListeners() {
        binding?.buttonSymptomFebre?.setOnClickListener(this)
        binding?.buttonSymptomTosse?.setOnClickListener(this)
        binding?.buttonSymptomArrepiosTremores?.setOnClickListener(this)
        binding?.buttonSymptomFaltaAr?.setOnClickListener(this)
        binding?.buttonSymptomPaladarOfato?.setOnClickListener(this)
        binding?.buttonNegativeSymptomSecondSession?.setOnClickListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null //retirar a referencia de view binding para evitar memory leak
    }
}