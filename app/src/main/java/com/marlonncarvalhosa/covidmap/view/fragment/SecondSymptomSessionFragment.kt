package com.marlonncarvalhosa.covidmap.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.marlonncarvalhosa.covidmap.R

class SecondSymptomSessionFragment: Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_second_symptom_session, container, false)

        view.findViewById<CardView>(R.id.cv_second_next).setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.quiz_container, ThirdSymptomSessionFragment())
                .addToBackStack("second")
                .commit()
        }

        view.findViewById<CardView>(R.id.cv_second_back).setOnClickListener {
            parentFragmentManager.popBackStack("first", 1)
        }

        return view
    }
}