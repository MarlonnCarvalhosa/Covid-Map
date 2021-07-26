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
import com.marlonncarvalhosa.covidmap.adapter.ThirdSymptomAdapter
import com.marlonncarvalhosa.covidmap.databinding.FragmentSecondSymptomSessionBinding
import com.marlonncarvalhosa.covidmap.databinding.FragmentThirdSymptomSessionBinding
import com.marlonncarvalhosa.covidmap.model.SecondSymptomModel
import kotlinx.android.synthetic.main.fragment_third_symptom_session.*

class ThirdSymptomSessionFragment : Fragment(R.layout.fragment_third_symptom_session){
    private var binding: FragmentThirdSymptomSessionBinding? = null
    private val symptomAdapter by lazy { SecondSymptomAdapter(::onSymtomSelectedListener, ::onSymptomDesselectedListener) }
    private val symptom: MutableList<SecondSymptomModel> = ArrayList()
    private var sintomas : HashMap<*, *>? = null

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
        sintomas = arguments?.getSerializable("sintomas") as HashMap<*, *>

        Log.d("teste", Gson().toJson(sintomas))
        binding?.rvThirdSymptom?.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = symptomAdapter
        }
        binding?.cvThirdFinish?.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.quiz_container, HighRiskContaminatedFragment())
                .disallowAddToBackStack()
                .commit()
        }
        binding?.cvThirdBack?.setOnClickListener { parentFragmentManager.popBackStack("second", 1) }

    }

    private fun onSymtomSelectedListener(secondSymptomModel: SecondSymptomModel) {
        symptom.add(secondSymptomModel)
    }

    private fun onSymptomDesselectedListener(secondSymptomModel: SecondSymptomModel) {
        symptom.remove(secondSymptomModel)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null //retirar a referencia de view binding para evitar memory leak
    }

    companion object {
    fun newInstance(sintomas : HashMap<String, Boolean>): ThirdSymptomSessionFragment {
        val args = Bundle()
        args.putSerializable("sintomas", sintomas)
        val fragment = ThirdSymptomSessionFragment()
        fragment.arguments = args
        return fragment
    }
    }
}