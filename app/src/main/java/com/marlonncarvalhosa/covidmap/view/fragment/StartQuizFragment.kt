package com.marlonncarvalhosa.covidmap.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.marlonncarvalhosa.covidmap.R

class StartQuizFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_start_quiz, container, false)

        root.findViewById<CardView>(R.id.cv_saude_bem).setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.quiz_container, SafeQuizFragment())
                .disallowAddToBackStack()
                .commit()
        }

        root.findViewById<CardView>(R.id.cv_saude_mal).setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.quiz_container, FirstSymptomSessionFragment())
                .addToBackStack("start")
                .commit()
        }

        return root
    }
}