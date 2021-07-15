package com.marlonncarvalhosa.covidmap.view.fragment

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.text.bold
import androidx.fragment.app.Fragment
import com.marlonncarvalhosa.covidmap.databinding.FragmentLowRiskContaminatedBinding

class LowRiskContaminatedFragment : Fragment() {

    private var binding: FragmentLowRiskContaminatedBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): ConstraintLayout? {
        binding = FragmentLowRiskContaminatedBinding.inflate(layoutInflater)
        val lowRiskOrientation = SpannableStringBuilder()
            .append("Baseado em suas respostas, é possível que essa situação ")
            .bold { append("não") }
            .append(" se enquadre como caso suspeito de sintomático para Coronavírus 2019 (Covid-19)." +
                    "\n\nMantenha as condutas de precaução e prevenção, praticando a etiqueta respiratória.")
        binding?.txtLowRiskOrientation?.text = lowRiskOrientation

        return binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null //retirar a referencia de view binding para evitar memory leak
    }
}