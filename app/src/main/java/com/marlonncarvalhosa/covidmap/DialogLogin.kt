package com.marlonncarvalhosa.covidmap

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class DialogLogin: DialogFragment() {

    var googleSignClient : GoogleSignInClient? = null
    val RC_SIGN_IN = 1000

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(R.layout.dialog_login, container, false)

        //Codigos

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignClient = GoogleSignIn.getClient(rootView.context,gso)

        val signInIntent = googleSignClient?.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)

        return rootView
    }

    fun firebaseAuthWithGoogle(acct : GoogleSignInAccount?) {
        val credencial = GoogleAuthProvider.getCredential(acct?.idToken, null)
        FirebaseAuth.getInstance().signInWithCredential(credencial).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(context, "Login efetuado com sucesso.", Toast.LENGTH_LONG).show()
                dialog?.dismiss()
            } else {
                Toast.makeText(context, "Algo de errado no login", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == RC_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                Log.e("DIALOGLOGIN", "Google SingIn Faield", e)
            }
        }
    }

}