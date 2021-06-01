package com.marlonncarvalhosa.covidmap

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile_sheet_dialog.*

class ProfileSheetDialogActivity : Activity() {

    private var user = FirebaseAuth.getInstance().currentUser
    var firebaseAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()

        authenticator()
    }

    private fun authenticator(){
        if (firebaseAuth?.currentUser != null) {
            user = FirebaseAuth.getInstance().currentUser;
        }
    }
}