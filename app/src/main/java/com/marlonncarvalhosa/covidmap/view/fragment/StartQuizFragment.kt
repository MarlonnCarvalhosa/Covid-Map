package com.marlonncarvalhosa.covidmap.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.marlonncarvalhosa.covidmap.R
import kotlinx.android.synthetic.main.fragment_start_quiz.*


class StartQuizFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_start_quiz, container, false)
    }
}