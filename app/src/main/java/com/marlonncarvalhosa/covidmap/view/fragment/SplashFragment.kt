package com.marlonncarvalhosa.covidmap.view.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.marlonncarvalhosa.covidmap.databinding.FragmentSplashBinding
import com.marlonncarvalhosa.covidmap.view.MapsActivity

class SplashFragment : Fragment() {

    private var binding: FragmentSplashBinding? = null
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(inflater, container, false)

        firebaseAuth = FirebaseAuth.getInstance()

        Handler(Looper.getMainLooper()).postDelayed({
            val sharedPref = activity?.getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
            val getLogin = sharedPref?.getBoolean("login", false)
            val getPermission = sharedPref?.getBoolean("permission", false)
            Toast.makeText(requireContext(), "permission $getPermission login $getLogin", Toast.LENGTH_LONG).show()
            if (getPermission != true && getLogin != true){
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToPermissionFragment())
                onDestroyView()
            } else if (getPermission == true){
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToOnBoardingFragment())
                onDestroyView()
            } else {
                startActivity(Intent(requireContext(), MapsActivity::class.java))
                onDestroyView()
            }
        },3000)

        return binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}