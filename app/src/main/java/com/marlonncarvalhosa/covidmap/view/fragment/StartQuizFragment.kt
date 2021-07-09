package com.marlonncarvalhosa.covidmap.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.marlonncarvalhosa.covidmap.R
import com.marlonncarvalhosa.covidmap.databinding.FragmentStartQuizBinding

class StartQuizFragment : Fragment() {

    // inicializar como null para utilizar o null safety e garantir que a aplicação não vá quebrar caso alguma view seja nula
    private var binding: FragmentStartQuizBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): ConstraintLayout? {
        binding = FragmentStartQuizBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.cvSaudeBem?.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.quiz_container, LowRiskFragment())
                .disallowAddToBackStack()
                .commit()
        }

        binding?.cvSaudeMal?.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.quiz_container, FirstSymptomSessionFragment())
                .addToBackStack("start")
                .commit()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}