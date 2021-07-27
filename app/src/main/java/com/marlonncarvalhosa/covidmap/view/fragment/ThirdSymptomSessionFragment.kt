 package com.marlonncarvalhosa.covidmap.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.marlonncarvalhosa.covidmap.R
import com.marlonncarvalhosa.covidmap.adapter.ThirdSymptomAdapter
import com.marlonncarvalhosa.covidmap.databinding.FragmentThirdSymptomSessionBinding
import com.marlonncarvalhosa.covidmap.model.QuizModel
import com.marlonncarvalhosa.covidmap.model.ThirdSymptomModel

class ThirdSymptomSessionFragment : Fragment(R.layout.fragment_third_symptom_session){
    private var binding: FragmentThirdSymptomSessionBinding? = null
    private val symptomAdapter by lazy { ThirdSymptomAdapter(::onThirdSymtomSelectedListener, ::onThirdSymptomDesselectedListener) }
    private val symptom: HashMap<String, Boolean> = HashMap()
    private val thirdSymptom: HashMap<String, Boolean> = HashMap()
    private var quiz: QuizModel? = null

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
        quiz = arguments?.getSerializable("quiz") as? QuizModel

        Log.d("teste", Gson().toJson(quiz))
        binding?.rvThirdSymptom?.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = symptomAdapter
        }

        binding?.cvThirdFinish?.setOnClickListener {
            quiz?.let {
                it.thirdSynthoms = thirdSymptom
                Log.d("QUIZ", Gson().toJson(quiz))
                if ((quiz?.contatoComInfectado == true) or (quiz?.positivoCovid == true)) {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.quiz_container, HighRiskContaminatedFragment())
                        .disallowAddToBackStack()
                        .commit()
                } else if (symptom.isNotEmpty()) {
                    Toast.makeText(context, "Primeiro else if", Toast.LENGTH_SHORT).show()
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.quiz_container, HighRiskContaminatedFragment())
                        .disallowAddToBackStack()
                        .commit()
                } else if (symptom.size > 2) {
                    Toast.makeText(context, "Segundo else if", Toast.LENGTH_SHORT).show()
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.quiz_container, HighRiskContaminatedFragment())
                        .disallowAddToBackStack()
                        .commit()
                }
            }

        }
        binding?.cvThirdBack?.setOnClickListener { parentFragmentManager.popBackStack("second", 1) }
        symptomAdapter.updateThirdSymptom(symptoms())

    }

    private fun onThirdSymtomSelectedListener(thirdSymptomModel: ThirdSymptomModel) {
        thirdSymptom[thirdSymptomModel.thirdSymptomName] = true
        Log.d("teste", Gson().toJson(thirdSymptom))
        //atualizar o firebase
    }

    private fun onThirdSymptomDesselectedListener(thirdSymptomModel: ThirdSymptomModel) {
        thirdSymptom.remove(thirdSymptomModel.thirdSymptomName)
        Log.d("teste", Gson().toJson(thirdSymptom))
        //atualizar o firebase
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null //retirar a referencia de view binding para evitar memory leak
    }

    private fun symptoms() : List<ThirdSymptomModel> {
        return listOf(ThirdSymptomModel("Dor de garganta"),
            ThirdSymptomModel("Congestão nasal"),
            ThirdSymptomModel("Diarréia"),
            ThirdSymptomModel("Dores no corpo"),
            ThirdSymptomModel("Dor de cabeça"),
            ThirdSymptomModel("Fadiga incomum"),
            ThirdSymptomModel("Vermelhidão nos olhos"),
            ThirdSymptomModel("Náusea ou vomito"),
            ThirdSymptomModel("Nenhum desses sintomas"))
    }

    companion object {
        fun newInstance(quiz: QuizModel): ThirdSymptomSessionFragment {
            val args = Bundle()
            args.putSerializable("quiz", quiz)
            val fragment = ThirdSymptomSessionFragment()
            fragment.arguments = args
            return fragment
        }
    }
}