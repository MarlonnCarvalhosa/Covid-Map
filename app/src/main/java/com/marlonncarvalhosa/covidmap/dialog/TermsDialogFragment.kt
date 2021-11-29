package com.marlonncarvalhosa.covidmap.dialog

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.marlonncarvalhosa.covidmap.R
import com.marlonncarvalhosa.covidmap.databinding.FragmentTermsDialogBinding

class TermsDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentTermsDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTermsDialogBinding.inflate(inflater, container, false)
        onClick()
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        dialog?.window?.setBackgroundDrawableResource(R.drawable.drawable_background_dialog)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    fun onClick(){
        binding.bntAccept.setOnClickListener {
            dialog?.dismiss()
        }
        binding.btnClose.setOnClickListener {
            accept(false)
            dialog?.dismiss()
        }
    }

    fun accept(condition: Boolean): Boolean{
        return condition
    }
}