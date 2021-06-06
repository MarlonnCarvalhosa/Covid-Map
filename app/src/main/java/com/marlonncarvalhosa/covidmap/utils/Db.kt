package com.marlonncarvalhosa.covidmap.utils

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class Db {

    var db = FirebaseFirestore.getInstance()
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

        db.collection("locations")
            .add(item)
            .addOnSuccessListener {
                Log.d("CREATE_FIREBASE", "OnSuccess Created Location Quiz")

                createAwser(quiz, it.id)
                return@addOnSuccessListener
            }
            .addOnFailureListener {
                    e -> Log.w("CREATE_LOCATIONQUIZ", "OnFailure Create: ", e)
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
        db.collection("locations")
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

    fun updateMap(idList: String, nomeDaLista: String, mercado: String) {
        val item = hashMapOf<String, Any>()
        item["nomeDaLista"] = nomeDaLista
        item["nomeDoMercado"] = mercado

        db.collection(mAuth?.uid.toString())
            .document(idList)
            .update(item)
            .addOnSuccessListener {
                Log.d("UPDATE_FIREBASE_LISTA", "OnSuccess Update:")
                return@addOnSuccessListener
            }
            .addOnFailureListener {
                    e -> Log.w("UPDATE_FIREBASE_LISTA", "OnFailure Update: ", e)
                return@addOnFailureListener
            }
    }
}