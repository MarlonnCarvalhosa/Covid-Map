package com.marlonncarvalhosa.covidmap.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.marlonncarvalhosa.covidmap.R
import com.marlonncarvalhosa.covidmap.adapter.SecondSymptomAdapter
import com.marlonncarvalhosa.covidmap.databinding.FragmentSecondSymptomSessionBinding
import com.marlonncarvalhosa.covidmap.model.QuizModel
import com.marlonncarvalhosa.covidmap.model.SecondSymptomModel

class SecondSymptomSessionFragment : Fragment(R.layout.fragment_second_symptom_session) {
    private var binding: FragmentSecondSymptomSessionBinding? = null
    private val symptomAdapter by lazy {
        SecondSymptomAdapter(
            ::onSymtomSelectedListener,
            ::onSymptomDesselectedListener
        )
    }
    private val symptom: HashMap<String, Boolean> = HashMap()
    private var quiz: QuizModel? = null

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
        quiz = arguments?.getSerializable("quiz") as? QuizModel
        Log.d("teste", Gson().toJson(quiz))

        binding?.rvSecondSymptom?.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            val gridLayout = GridLayoutManager(context, 2)
            binding?.rvSecondSymptom!!.layoutManager = gridLayout
            adapter = symptomAdapter
        }

        binding?.buttonThirdFinish?.setOnClickListener {
            quiz?.let {
                it.secondSynthoms = symptom
                parentFragmentManager.beginTransaction()
                    .replace(R.id.quiz_container, ThirdSymptomSessionFragment.newInstance(it))
                    .addToBackStack("second")
                    .commit()
            }
        }
        binding?.buttonSecondBack?.setOnClickListener {
            parentFragmentManager.popBackStack(
                "first",
                1
            )
        }
        symptomAdapter.updateSymptom(symptoms())
    }

    private fun onSymtomSelectedListener(secondSymptomModel: SecondSymptomModel) {
        symptom[secondSymptomModel.secondSymptomName] = true
        Log.d("teste", Gson().toJson(symptom))
        //atualizar o firebase
    }

    private fun onSymptomDesselectedListener(secondSymptomModel: SecondSymptomModel) {
        symptom.remove(secondSymptomModel.secondSymptomName)
        Log.d("teste", Gson().toJson(symptom))
        //atualizar o firebase
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null //retirar a referencia de view binding para evitar memory leak
    }

    private fun symptoms(): List<SecondSymptomModel> {
        return listOf(
            SecondSymptomModel("Febre"),
            SecondSymptomModel("Arrepios ou tremores"),
            SecondSymptomModel("Tosse"),
            SecondSymptomModel("Falta de ar"),
            SecondSymptomModel("Perda de paladar/ofato"),
            SecondSymptomModel("Nenhum desses sintomas")
        )
    }

    companion object {
        fun newInstance(quiz: QuizModel): SecondSymptomSessionFragment {
            val args = Bundle()
            args.putSerializable("quiz", quiz)
            val fragment = SecondSymptomSessionFragment()
            fragment.arguments = args
            return fragment
        }
    }
}