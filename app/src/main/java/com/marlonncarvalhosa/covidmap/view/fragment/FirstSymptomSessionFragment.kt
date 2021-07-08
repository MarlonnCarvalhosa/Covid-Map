package com.marlonncarvalhosa.covidmap.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.marlonncarvalhosa.covidmap.R

class FirstSymptomSessionFragment: Fragment(), View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_first_symptom_session, container, false)

        view.findViewById<CardView>(R.id.cv_first_next).setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.quiz_container, SecondSymptomSessionFragment())
                .addToBackStack("first")
                .commit()
        }

        view.findViewById<CardView>(R.id.cv_first_back).setOnClickListener {
            parentFragmentManager.popBackStack("start", 1)
        }

        val teste1 = view.findViewById<CardView>(R.id.cv_covid_yes)
        teste1.setOnClickListener {this}

        return view
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.cv_covid_yes -> {
                Log.d("TESTE", "COVIDDDDDDDDDDDDDDD")
                Toast.makeText(context, "Teste", Toast.LENGTH_LONG).show()
            }

            else -> {
            }
        }
    }
}