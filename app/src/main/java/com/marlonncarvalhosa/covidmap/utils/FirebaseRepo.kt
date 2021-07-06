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
        item["febre"] = quiz.firstSymptomModel.febre
        item["arrepiosTremore"] = quiz.firstSymptomModel.arrepiosTremore
        item["tosse"] = quiz.firstSymptomModel.tosse
        item["faltaDeAr"] = quiz.firstSymptomModel.faltaDeAr
        item["faltaPaladarOfato"] = quiz.firstSymptomModel.faltaPaladarOfato
        item["congrecaoNasal"] = quiz.secondSymptomModel.congrecaoNasal
        item["escorrendo"] = quiz.secondSymptomModel.escorrendo
        item["dorCorpo"] = quiz.secondSymptomModel.dorCorpo
        item["dorCabeca"] = quiz.secondSymptomModel.dorCabeca
        item["fadigaIncomum"] = quiz.secondSymptomModel.fadigaIncomum
        item["vermelhidaoDosOlhos"] = quiz.secondSymptomModel.vermelhidaoDosOlhos
        item["nauseasVomitos"] = quiz.secondSymptomModel.nauseasVomitos

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