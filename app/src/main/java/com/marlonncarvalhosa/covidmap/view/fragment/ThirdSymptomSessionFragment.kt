package com.marlonncarvalhosa.covidmap.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.marlonncarvalhosa.covidmap.R
import com.marlonncarvalhosa.covidmap.adapter.ThirdSymptomAdapter
import com.marlonncarvalhosa.covidmap.databinding.FragmentThirdSymptomSessionBinding
import com.marlonncarvalhosa.covidmap.model.QuizModel
import com.marlonncarvalhosa.covidmap.model.ThirdSymptomModel
import com.marlonncarvalhosa.covidmap.utils.DataStoreManager
import com.marlonncarvalhosa.covidmap.utils.FirebaseRepo

class ThirdSymptomSessionFragment : Fragment(R.layout.fragment_third_symptom_session) {

    private var binding: FragmentThirdSymptomSessionBinding? = null
    private val symptomAdapter by lazy {
        ThirdSymptomAdapter(
            ::onThirdSymtomSelectedListener,
            ::onThirdSymptomDesselectedListener
        )
    }
    private val symptom: HashMap<String, Boolean> = HashMap()
    private val thirdSymptom: HashMap<String, Boolean> = HashMap()
    private var quiz: QuizModel? = null
    private val fireRepo = FirebaseRepo()
    private val dataStoreManager by lazy { DataStoreManager(requireContext()) }
    private var latitude = 0.0
    private var longitude = 0.0

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

        onClick()
        initListThirdSymptom()
        getDataLatLong()

        symptomAdapter.updateThirdSymptom(symptoms())
    }

    private fun getDataLatLong() {
        dataStoreManager.getLatitudeFlow.asLiveData().observe(viewLifecycleOwner, Observer {
            this.latitude = it
        })
        dataStoreManager.getLongitudeFlow.asLiveData().observe(viewLifecycleOwner, Observer {
            this.longitude = it
        })
    }

    private fun initListThirdSymptom() {
        binding?.rvThirdSymptom?.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = symptomAdapter
        }
    }

    private fun onClick() {
        binding?.buttonThirdFinish?.setOnClickListener {
            quiz?.let {
                it.thirdSynthoms = thirdSymptom
                Log.d("QUIZ", Gson().toJson(quiz))
                if ((quiz?.contatoComInfectado == true) or (quiz?.positivoCovid == true) ||
                    (quiz?.secondSynthoms?.size!! >= 1) || (thirdSymptom.size >= 2)
                ) {
                    quiz?.possibleInfected = true
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, HighRiskContaminatedFragment())
                        .disallowAddToBackStack()
                        .commit()

                    fireRepo.postLocation(1.0, latitude, longitude, quiz!!)
                } else {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, LowRiskContaminatedFragment())
                        .disallowAddToBackStack()
                        .commit()
                }
            }
        }
        binding?.buttonThirdBack?.setOnClickListener {
            parentFragmentManager.popBackStack(
                "second",
                1
            )
        }
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

    private fun symptoms(): List<ThirdSymptomModel> {
        return listOf(
            ThirdSymptomModel("Dor de garganta"),
            ThirdSymptomModel("Congestão nasal"),
            ThirdSymptomModel("Diarréia"),
            ThirdSymptomModel("Dores no corpo"),
            ThirdSymptomModel("Dor de cabeça"),
            ThirdSymptomModel("Fadiga incomum"),
            ThirdSymptomModel("Vermelhidão nos olhos"),
            ThirdSymptomModel("Náusea ou vomito"),
            ThirdSymptomModel("Nenhum desses sintomas")
        )
    }

    companion object {
        fun newInstance(quiz: QuizModel): ThirdSymptomSessionFragment {
            val args = Bundle()
            args.putSerializable("quiz", quiz)
            val fragment = ThirdSymptomSessionFragment()
            fragment.arguments = args
            return fragment
        }

        private val LATITUDE = doublePreferencesKey("latitude")
        private val LONGITUDE = doublePreferencesKey("longitude")
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