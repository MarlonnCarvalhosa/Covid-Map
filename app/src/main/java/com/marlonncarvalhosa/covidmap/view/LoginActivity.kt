package com.marlonncarvalhosa.covidmap.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.marlonncarvalhosa.covidmap.R
import com.marlonncarvalhosa.covidmap.databinding.ActivityLoginBinding
import com.marlonncarvalhosa.covidmap.dialog.DialogLogin

class LoginActivity : AppCompatActivity() {

    private var binding: ActivityLoginBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater).apply { setContentView(root) }

        onClick()
    }

    private fun onClick() {
        binding?.btnLoginGoogle?.setOnClickListener {
            val dialog = DialogLogin()
            if (dialog.isCancelable){
                dialog.onDestroy()
            }
            dialog.show(supportFragmentManager, "DialogLogin")
        }

        binding?.btnEnterNotLogin?.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}