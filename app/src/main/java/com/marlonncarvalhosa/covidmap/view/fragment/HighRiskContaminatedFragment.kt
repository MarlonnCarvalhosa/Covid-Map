package com.marlonncarvalhosa.covidmap.view.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.marlonncarvalhosa.covidmap.databinding.FragmentHighRiskContaminatedBinding

class HighRiskContaminatedFragment : Fragment() {

    private var binding: FragmentHighRiskContaminatedBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): ConstraintLayout? {
        binding = FragmentHighRiskContaminatedBinding.inflate(layoutInflater)
        openCall()
        openWhatsapp()
        return binding?.root
    }

    private fun openCall() {
        binding?.cvCall?.setOnClickListener {
            val uri: Uri = Uri.parse("tel:$136")
            val intent = Intent(Intent.ACTION_DIAL, uri)
            startActivity(intent)
        }
    }

    private fun openWhatsapp() {
        binding?.cvWpp?.setOnClickListener {
            val uriWpp: Uri = Uri.parse("https://api.whatsapp.com/send?phone=556199380031&text=oi&source=&data=")
            val intentWpp = Intent(Intent.ACTION_VIEW, uriWpp)
            startActivity(intentWpp)
        }

    }
}