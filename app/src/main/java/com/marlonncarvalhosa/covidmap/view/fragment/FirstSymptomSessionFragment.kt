package com.marlonncarvalhosa.covidmap.view.fragment

import android.app.Fragment
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import com.marlonncarvalhosa.covidmap.R
import com.marlonncarvalhosa.covidmap.databinding.FragmentFirstSymptomSessionBinding
import com.marlonncarvalhosa.covidmap.model.QuizModel

class FirstSymptomSessionFragment: androidx.fragment.app.Fragment(), View.OnClickListener {

    // inicializar como null para utilizar o null safety e garantir que a aplicação não vá quebrar caso alguma view seja nula
    private var binding: FragmentFirstSymptomSessionBinding? = null
    private var contatoComInfectado  = false
    private var positivoCovid = false
    @RequiresApi(Build.VERSION_CODES.O)
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
        binding?.buttonFirstNext?.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.quiz_container, SecondSymptomSessionFragment.newInstance(QuizModel(positivoCovid, contatoComInfectado, null, null)))
                .addToBackStack("first")
                .commit()
        }
        binding?.buttonFirstBack?.setOnClickListener { parentFragmentManager.popBackStack("start", 1) }
    }
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onClick(v: View?) {
        when (v?.id) {
            binding?.buttonPositiveCovid?.id -> {
                positivoCovid = true
                Log.d("teste", "positive click")
                Toast.makeText(context, "Positive click", Toast.LENGTH_LONG).show()
            }
            binding?.buttonNegativeCovid?.id -> {
                positivoCovid = false
                Log.d("teste", "negative click")
                Toast.makeText(context, "negative click", Toast.LENGTH_LONG).show()
            }
            binding?.buttonPositiveContactCovid?.id -> {
                contatoComInfectado = true
                Log.d("teste", "positive contact click")
                Toast.makeText(context, "positive contact click", Toast.LENGTH_LONG).show()
            }
            binding?.buttonNegativeContactCovid?.id -> {
                contatoComInfectado = false
                Log.d("teste", "negative contact click")
                Toast.makeText(context, "negative contact click", Toast.LENGTH_LONG).show()
            }
            else -> {
            }
        }
    }

    private fun setClickListeners() {
        binding?.buttonPositiveCovid?.setOnClickListener(this)
        binding?.buttonNegativeCovid?.setOnClickListener(this)
        binding?.buttonPositiveContactCovid?.setOnClickListener(this)
        binding?.buttonNegativeContactCovid?.setOnClickListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null //retirar a referencia de view binding para evitar memory leak
    }
}