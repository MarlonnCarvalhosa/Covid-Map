package com.marlonncarvalhosa.covidmap.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.marlonncarvalhosa.covidmap.R
import com.marlonncarvalhosa.covidmap.databinding.FragmentPoliticDialogBinding

class PoliticDialogFragment : DialogFragment() {

    private var binding: FragmentPoliticDialogBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPoliticDialogBinding.inflate(inflater, container, false)

        onClick()

        return binding?.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        dialog?.window?.setBackgroundDrawableResource(R.drawable.drawable_background_dialog)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    fun onClick(){
        binding?.btnClose?.setOnClickListener {
            dialog?.dismiss()
        }
    }
}