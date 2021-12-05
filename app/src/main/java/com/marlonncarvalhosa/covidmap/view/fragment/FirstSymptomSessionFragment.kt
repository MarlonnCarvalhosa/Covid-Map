package com.marlonncarvalhosa.covidmap.view.fragment

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.marlonncarvalhosa.covidmap.R
import com.marlonncarvalhosa.covidmap.databinding.FragmentFirstSymptomSessionBinding
import com.marlonncarvalhosa.covidmap.model.QuizModel

class FirstSymptomSessionFragment: androidx.fragment.app.Fragment(), View.OnClickListener {

    // inicializar como null para utilizar o null safety e garantir que a aplicação não vá quebrar caso alguma view seja nula
    private var binding: FragmentFirstSymptomSessionBinding? = null
    private var contatoComInfectado  = false
    private var positivoCovid = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): ConstraintLayout? {
        binding = FragmentFirstSymptomSessionBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
        binding?.buttonFirstBack?.setOnClickListener { parentFragmentManager.popBackStack("start", 1) }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding?.buttonPositiveCovid?.id -> {
                positivoCovid = true
                covidPositiveClick()
            }
            binding?.buttonNegativeCovid?.id -> {
                positivoCovid = false
                covidNegativeClick()
            }
            binding?.buttonPositiveContactCovid?.id -> {
                contatoComInfectado = true
                contactPositiveClick()
            }
            binding?.buttonNegativeContactCovid?.id -> {
                contatoComInfectado = false
                contactNegativeClick()
            }
            else -> {
            }
        }
        binding?.buttonFirstNext?.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, SecondSymptomSessionFragment.newInstance(QuizModel(positivoCovid, contatoComInfectado, null, null)))
                .addToBackStack("first")
                .commit()

            onDestroyView()
        }
    }

    private fun covidPositiveClick(){
        binding?.buttonPositiveCovid?.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.dark_red))
        binding?.tvPositiveCovid?.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        binding?.ivPositiveCovid?.setImageResource(R.drawable.ic_check_white)

        binding?.buttonNegativeCovid?.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.gray))
        binding?.tvNegativeCovid?.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
        binding?.ivNegativeCovid?.setImageResource(R.drawable.ic_cancel_red_24dp)
    }

    private fun covidNegativeClick(){
        binding?.buttonNegativeCovid?.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.dark_red))
        binding?.tvNegativeCovid?.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        binding?.ivNegativeCovid?.setImageResource(R.drawable.ic_cancel_white_24dp)

        binding?.buttonPositiveCovid?.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.gray))
        binding?.tvPositiveCovid?.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        binding?.ivPositiveCovid?.setImageResource(R.drawable.ic_baseline_check_circle_outline_24)
    }

    private fun contactPositiveClick(){
        binding?.buttonPositiveContactCovid?.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.dark_red))
        binding?.tvPositiveContactCovid?.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        binding?.ivPositiveContactCovid?.setImageResource(R.drawable.ic_check_white)

        binding?.buttonNegativeContactCovid?.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.gray))
        binding?.tvNegativeContactCovid?.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
        binding?.ivNegativeContactCovid?.setImageResource(R.drawable.ic_cancel_red_24dp)
    }

    private fun contactNegativeClick(){
        binding?.buttonNegativeContactCovid?.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.dark_red))
        binding?.tvNegativeContactCovid?.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        binding?.ivNegativeContactCovid?.setImageResource(R.drawable.ic_cancel_white_24dp)

        binding?.buttonPositiveContactCovid?.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.gray))
        binding?.tvPositiveContactCovid?.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        binding?.ivPositiveContactCovid?.setImageResource(R.drawable.ic_baseline_check_circle_outline_24)
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

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}