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
import com.marlonncarvalhosa.covidmap.databinding.ActivityQuizBinding

class QuizActivity : AppCompatActivity() {

    private var binding: ActivityQuizBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
        super.onCreate(savedInstanceState)

        binding = ActivityQuizBinding.inflate(layoutInflater).apply { setContentView(root) }

        setEnterSharedElementCallback(MaterialContainerTransformSharedElementCallback())
        window.sharedElementEnterTransition = buildTransitions()
        window.sharedElementExitTransition = buildTransitions()
        window.sharedElementReenterTransition = buildTransitions()

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
            duration = 500
            pathMotion = MaterialArcMotion()
            interpolator = FastOutSlowInInterpolator()
            fadeMode = MaterialContainerTransform.FADE_MODE_IN

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}