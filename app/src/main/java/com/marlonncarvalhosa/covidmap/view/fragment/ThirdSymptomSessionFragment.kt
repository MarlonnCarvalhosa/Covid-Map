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
import com.marlonncarvalhosa.covidmap.databinding.FragmentThirdSymptomSessionBinding

class ThirdSymptomSessionFragment : Fragment(), View.OnClickListener {

    // inicializar como null para utilizar o null safety e garantir que a aplicação não vá quebrar caso alguma view seja nula
    private var binding: FragmentThirdSymptomSessionBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): ConstraintLayout? {
        binding = FragmentThirdSymptomSessionBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
        binding?.cvThirdFinish?.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.quiz_container, HighRiskFragment())
                .disallowAddToBackStack()
                .commit()
        }
        binding?.cvThirdBack?.setOnClickListener { parentFragmentManager.popBackStack("second", 1) }

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding?.buttonSymptomDorGarganta?.id -> {
                Log.d("teste", "click dor garganta")
                Toast.makeText(context, "click dor garganta", Toast.LENGTH_SHORT).show()
                //Alterando cor no click
                binding?.buttonSymptomDorGarganta?.backgroundTintList =
                    context?.let { ContextCompat.getColorStateList(it, R.color.primary_purple) }
                binding?.tvSymptomDorGarganta?.setTextColor(Color.WHITE)

            }
            binding?.buttonSymptomNarizEscorrendo?.id -> {
                Log.d("teste", "click nariz escorrendo")
                Toast.makeText(context, "click nariz escorrendo", Toast.LENGTH_SHORT).show()
            }
            binding?.buttonSymptomDiarreia?.id -> {
                Log.d("teste", "click diarreia")
                Toast.makeText(context, "click diarreia", Toast.LENGTH_SHORT).show()
            }
            binding?.buttonSymptomDorCorpo?.id -> {
                Log.d("teste", "click dor corpo")
                Toast.makeText(context, "click dor corpo", Toast.LENGTH_SHORT).show()
            }
            binding?.buttonSymptomFadiga?.id -> {
                Log.d("teste", "click fadiga incomum")
                Toast.makeText(context, "click fadiga incomum", Toast.LENGTH_SHORT).show()
            }
            binding?.buttonSymptomDorCabeca?.id -> {
                Log.d("teste", "click dor cabeça")
                Toast.makeText(context, "click dor cabeça", Toast.LENGTH_SHORT).show()
            }
            binding?.buttonSymptomVermelhidaoOlhos?.id -> {
                Log.d("teste", "click vermelhidao olhos")
                Toast.makeText(context, "click vermelhidao olhos", Toast.LENGTH_SHORT).show()
            }
            binding?.buttonSymptomNauseaVomito?.id -> {
                Log.d("teste", "click nauseas/vomito")
                Toast.makeText(context, "click nauseas/vomito", Toast.LENGTH_SHORT).show()
            }
            binding?.buttonNegativeSymptomThirdSession?.id -> {
                Log.d("teste", "click sem sintomas")
                Toast.makeText(context, "click sem sintomas", Toast.LENGTH_SHORT).show()
            }
            else -> {
            }
        }
    }

    private fun setClickListeners() {
        binding?.buttonSymptomDorGarganta?.setOnClickListener(this)
        binding?.buttonSymptomNarizEscorrendo?.setOnClickListener(this)
        binding?.buttonSymptomDiarreia?.setOnClickListener(this)
        binding?.buttonSymptomDorCorpo?.setOnClickListener(this)
        binding?.buttonSymptomFadiga?.setOnClickListener(this)
        binding?.buttonSymptomDorCabeca?.setOnClickListener(this)
        binding?.buttonSymptomVermelhidaoOlhos?.setOnClickListener(this)
        binding?.buttonSymptomNauseaVomito?.setOnClickListener(this)
        binding?.buttonNegativeSymptomThirdSession?.setOnClickListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null //retirar a referencia de view binding para evitar memory leak
    }
}