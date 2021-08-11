 package com.marlonncarvalhosa.covidmap.view.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.marlonncarvalhosa.covidmap.R
import com.marlonncarvalhosa.covidmap.adapter.ThirdSymptomAdapter
import com.marlonncarvalhosa.covidmap.databinding.FragmentThirdSymptomSessionBinding
import com.marlonncarvalhosa.covidmap.model.QuizModel
import com.marlonncarvalhosa.covidmap.model.ThirdSymptomModel
import com.marlonncarvalhosa.covidmap.utils.FirebaseRepo

 class ThirdSymptomSessionFragment : Fragment(R.layout.fragment_third_symptom_session){

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "location")
    private var binding: FragmentThirdSymptomSessionBinding? = null
    private val symptomAdapter by lazy { ThirdSymptomAdapter(::onThirdSymtomSelectedListener, ::onThirdSymptomDesselectedListener) }
    private val symptom: HashMap<String, Boolean> = HashMap()
    private val thirdSymptom: HashMap<String, Boolean> = HashMap()
    private var quiz: QuizModel? = null
    private val fireRepo = FirebaseRepo()
    var teste = ""
    val LAT_KEY = stringPreferencesKey("LATITUDE")

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
            val gridLayout = GridLayoutManager(context, 2)
            binding?.rvThirdSymptom!!.layoutManager = gridLayout
            adapter = symptomAdapter
        }

        binding?.buttonThirdFinish?.setOnClickListener {
            quiz?.let {
                it.thirdSynthoms = thirdSymptom
                Log.d("QUIZ", Gson().toJson(quiz))
                if ((quiz?.contatoComInfectado == true) or (quiz?.positivoCovid == true) ||
                    (quiz?.secondSynthoms?.size!! >= 1) || (thirdSymptom.size >= 2)) {
                        quiz?.possibleInfected = true
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.quiz_container, HighRiskContaminatedFragment())
                        .disallowAddToBackStack()
                        .commit()

                    fireRepo.postLocation(50.0, "-21.191971", "-41.9087798", hashSetOf<String>(quiz.toString()))
                    Log.d("LATITUDE", teste)
                } else {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.quiz_container, LowRiskContaminatedFragment())
                        .disallowAddToBackStack()
                        .commit()
                }
            }
        }
        binding?.buttonThirdBack?.setOnClickListener { parentFragmentManager.popBackStack("second", 1) }
        symptomAdapter.updateThirdSymptom(symptoms())

    }

//     private suspend fun readData(key: String): String? {
//         val prefsKey = stringPreferencesKey(key)
//         val prefs = context?.dataStore?.data?.first()
//         return prefs!![prefsKey]
//     }

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