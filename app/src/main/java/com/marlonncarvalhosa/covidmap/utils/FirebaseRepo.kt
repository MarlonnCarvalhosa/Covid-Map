package com.marlonncarvalhosa.covidmap.utils

import android.os.Bundle
import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.marlonncarvalhosa.covidmap.model.QuizModel
import com.marlonncarvalhosa.covidmap.view.fragment.ThirdSymptomSessionFragment

class FirebaseRepo {

    var firestore = FirebaseFirestore.getInstance()
    private var mAuth = FirebaseAuth.getInstance().currentUser

    fun postLocation(intensity: Double, let: String, lon: String) {

        val item = createLocation(intensity, let, lon)

        //documento do quiz
        val quiz = hashMapOf<String, Any>()
        //Array com os sintomas selecionados
        val sintomas = arrayListOf<String>()

        sintomas.add("Dor de cabeça")
        sintomas.add("Sem sintomas")

        quiz["timestamp"] = Timestamp.now()
        //Pergunta se estar bem ou não
        quiz["question1"] = true
        //Se estivar mal pergunta os sitomas e marca eles
        quiz["question2"] = sintomas
        //Pergunta se esteve contato com alguem com covid-19
        quiz["question3"] = false


        Log.i("NOTIFY_CREATE_FIREBASE", "CRIANDO NEWLIST")

        firestore.collection("locations")
            .add(item)
            .addOnSuccessListener {
                Log.d("CREATE_FIREBASE", "OnSuccess Created Location Quiz")

                createAwser(quiz, it.id)
                return@addOnSuccessListener
            }
            .addOnFailureListener { e ->
                Log.w("CREATE_LOCATIONQUIZ", "OnFailure Create: ", e)
                return@addOnFailureListener
            }
    }

    private fun createLocation(inten: Double, lat: String, lon: String): HashMap<String, Any> {
        //Pegando informações da localização do usuario
        val item = hashMapOf<String, Any>()
        item["intensity"] = inten
        item["lat"] = lat
        item["lon"] = lon
        item["timestamp"] = Timestamp.now()
        item["id_user"] = mAuth?.uid.toString()
        return item
    }

    private fun createAwser(quiz: HashMap<String, Any>, id: String) {
        firestore.collection("locations")
            .document(id)
            .collection("quiz")
            .add(quiz)
            .addOnSuccessListener {
                Log.d("CREATE_QUIZ", "OnSuccess Created Quiz")
            }
            .addOnFailureListener { e ->
                Log.w("CREATE_QUIZ", "OnFailure Create: ", e)
                return@addOnFailureListener
            }
    }
}