package com.marlonncarvalhosa.covidmap.view

import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.google.android.material.color.MaterialColors
import com.google.android.material.transition.platform.MaterialArcMotion
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import com.marlonncarvalhosa.covidmap.R
import com.marlonncarvalhosa.covidmap.view.fragment.StartQuizFragment

class QuizActivity : AppCompatActivity() {

    private val manager = supportFragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
        setContentView(R.layout.activity_quiz)

        setEnterSharedElementCallback(MaterialContainerTransformSharedElementCallback())
        window.sharedElementEnterTransition = buildTransitions()
        window.sharedElementExitTransition = buildTransitions()
        window.sharedElementReenterTransition = buildTransitions()

        super.onCreate(savedInstanceState)

        showFragment()
    }

    private fun showFragment() {
        val transaction = manager.beginTransaction()
        val fragment = StartQuizFragment()
        transaction.replace(R.id.quiz, fragment)
        transaction.commit()
    }

    private fun buildTransitions(): MaterialContainerTransform {
        return MaterialContainerTransform().apply {
            addTarget(R.id.containerQuiz)
            setAllContainerColors(
                MaterialColors.getColor(
                    findViewById(R.id.containerQuiz),
                    R.attr.colorSurface
                )
            )
            duration = 600
            pathMotion = MaterialArcMotion()
            interpolator = FastOutSlowInInterpolator()
            fadeMode = MaterialContainerTransform.FADE_MODE_IN

        }
    }
}