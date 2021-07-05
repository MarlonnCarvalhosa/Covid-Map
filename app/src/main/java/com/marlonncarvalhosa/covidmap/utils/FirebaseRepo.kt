package com.marlonncarvalhosa.covidmap.utils

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.marlonncarvalhosa.covidmap.model.QuizModel

class FirebaseRepo {

    var firestore = FirebaseFirestore.getInstance()
    private var mAuth = FirebaseAuth.getInstance().currentUser

    fun postQuiz(intensity: Double, let: String, lon: String, quiz: QuizModel) {

        val item = hashMapOf<String, Any>()
        item["Localização"] = createLocation(intensity, let, lon)
        item["positivoCovid"] = quiz.positivoCovid
        item["timeStamp"] = quiz.timeStamp
        item["febre"] = quiz.sintomasPrimarios.febre
        item["arrepiosTremore"] = quiz.sintomasPrimarios.arrepiosTremore
        item["tosse"] = quiz.sintomasPrimarios.tosse
        item["faltaDeAr"] = quiz.sintomasPrimarios.faltaDeAr
        item["faltaPaladarOfato"] = quiz.sintomasPrimarios.faltaPaladarOfato
        item["congrecaoNasal"] = quiz.sintomasSecundarios.congrecaoNasal
        item["escorrendo"] = quiz.sintomasSecundarios.escorrendo
        item["dorCorpo"] = quiz.sintomasSecundarios.dorCorpo
        item["dorCabeca"] = quiz.sintomasSecundarios.dorCabeca
        item["fadigaIncomum"] = quiz.sintomasSecundarios.fadigaIncomum
        item["vermelhidaoDosOlhos"] = quiz.sintomasSecundarios.vermelhidaoDosOlhos
        item["nauseasVomitos"] = quiz.sintomasSecundarios.nauseasVomitos

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

    private fun createAwser(quiz: QuizModel, id: String) {
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