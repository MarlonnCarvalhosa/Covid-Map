package com.marlonncarvalhosa.covidmap.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.marlonncarvalhosa.covidmap.R
import com.marlonncarvalhosa.covidmap.adapter.ThirdSymptomAdapter
import com.marlonncarvalhosa.covidmap.databinding.FragmentThirdSymptomSessionBinding
import kotlinx.android.synthetic.main.fragment_third_symptom_session.*

class ThirdSymptomSessionFragment : Fragment(){

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

        val symptomName: Array<String> = resources.getStringArray(R.array.thirdSessionSymptom)

        val thirdSymptomAdapter = ThirdSymptomAdapter(symptomName)
        val gridLayout = GridLayoutManager(context, 2)
        rv_third_symptom.layoutManager = gridLayout
        rv_third_symptom.adapter = thirdSymptomAdapter

        binding?.cvThirdFinish?.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.quiz_container, HighRiskContaminatedFragment())
                .disallowAddToBackStack()
                .commit()
        }
        binding?.cvThirdBack?.setOnClickListener { parentFragmentManager.popBackStack("second", 1) }

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null //retirar a referencia de view binding para evitar memory leak
    }
}