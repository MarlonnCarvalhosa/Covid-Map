package com.marlonncarvalhosa.covidmap.utils

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.marlonncarvalhosa.covidmap.model.QuizModel
import java.text.SimpleDateFormat
import java.util.*

class FirebaseRepo {

    var firestore = FirebaseFirestore.getInstance()
    private var mAuth = FirebaseAuth.getInstance().currentUser

    fun postLocation(intensity: Double, latitude: Double, longitude: Double, quiz: QuizModel) {
            quiz.latitude = latitude
            quiz.longitude = longitude
            quiz.intensity= intensity
            quiz.userId = mAuth?.uid.toString()
            quiz.timeStamp = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.getDefault()).format(Date())

        firestore.collection("respostas")
            .document(mAuth?.uid.toString())
            .set(quiz)
            .addOnSuccessListener {
                Log.d("CREATE_FIREBASE", "OnSuccess Created Location Quiz")

             /*   createAwser(quiz, it.id)*/
                return@addOnSuccessListener
            }
            .addOnFailureListener { e ->
                Log.w("CREATE_LOCATIONQUIZ", "OnFailure Create: ", e)
                return@addOnFailureListener
            }
    }
}