package com.marlonncarvalhosa.covidmap.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.marlonncarvalhosa.covidmap.R
import com.marlonncarvalhosa.covidmap.adapter.SecondSymptomAdapter
import com.marlonncarvalhosa.covidmap.databinding.FragmentSecondSymptomSessionBinding
import com.marlonncarvalhosa.covidmap.model.SecondSymptomModel
import kotlinx.android.synthetic.main.fragment_second_symptom_session.*


class SecondSymptomSessionFragment : Fragment(R.layout.fragment_second_symptom_session) {
    private var binding: FragmentSecondSymptomSessionBinding? = null
    private val symptomAdapter by lazy { SecondSymptomAdapter(::onSymtomSelectedListener, ::onSymptomDesselectedListener) }
    private val symptom: HashMap<String,Boolean> = HashMap()

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

        binding?.rvSecondSymptom?.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = symptomAdapter
        }

        binding?.cvSecondNext?.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.quiz_container, ThirdSymptomSessionFragment.newInstance(symptom))
                .addToBackStack("second")
                .commit()
        }
        binding?.cvSecondBack?.setOnClickListener { parentFragmentManager.popBackStack("first", 1) }
        symptomAdapter.updateSymptom(symptoms())
    }

    private fun onSymtomSelectedListener(secondSymptomModel: SecondSymptomModel) {
        symptom[secondSymptomModel.sintomas] = true
        Log.d("teste", Gson().toJson(symptom))
        //atualizar o firebase
    }

    private fun onSymptomDesselectedListener(secondSymptomModel: SecondSymptomModel) {
        symptom.remove(secondSymptomModel.sintomas)
        Log.d("teste", Gson().toJson(symptom))
        //atualizar o firebase
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null //retirar a referencia de view binding para evitar memory leak
    }

    private fun symptoms() : List<SecondSymptomModel> {
        return listOf(SecondSymptomModel("Febre"), SecondSymptomModel("Dor de garganta"))
        //colocar os sintomas
    }
}