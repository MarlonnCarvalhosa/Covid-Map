package com.marlonncarvalhosa.covidmap.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.marlonncarvalhosa.covidmap.R
import com.marlonncarvalhosa.covidmap.adapter.SecondSymptomAdapter
import com.marlonncarvalhosa.covidmap.databinding.FragmentSecondSymptomSessionBinding
import kotlinx.android.synthetic.main.fragment_second_symptom_session.*


class SecondSymptomSessionFragment : Fragment() {

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

        val symptomName: Array<String> = resources.getStringArray(R.array.secondSessionSymptom)

        val secondSymptomAdapter = SecondSymptomAdapter(symptomName, requireContext())
        val gridLayout = GridLayoutManager(context, 2)
        rv_second_symptom.layoutManager = gridLayout
        rv_second_symptom.adapter = secondSymptomAdapter

        binding?.cvSecondNext?.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.quiz_container, ThirdSymptomSessionFragment())
                .addToBackStack("second")
                .commit()
        }
        binding?.cvSecondBack?.setOnClickListener { parentFragmentManager.popBackStack("first", 1) }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null //retirar a referencia de view binding para evitar memory leak
    }
}