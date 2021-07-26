package com.marlonncarvalhosa.covidmap.utils

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.marlonncarvalhosa.covidmap.model.QuizModel

class FirebaseRepo {

/*    var firestore = FirebaseFirestore.getInstance()
    private var mAuth = FirebaseAuth.getInstance().currentUser

    fun postQuiz(intensity: Double, let: String, lon: String, quiz: QuizModel) {

        val item = hashMapOf<String, Any>()
        item["Localização"] = createLocation(intensity, let, lon)
        item["positivoCovid"] = quiz.positivoCovid
        item["timeStamp"] = quiz.timeStamp
        item["febre"] = quiz.secondSymptomModel.febre
        item["arrepiosTremore"] = quiz.secondSymptomModel.arrepiosTremore
        item["tosse"] = quiz.secondSymptomModel.tosse
        item["faltaDeAr"] = quiz.secondSymptomModel.faltaDeAr
        item["faltaPaladarOfato"] = quiz.secondSymptomModel.faltaPaladarOfato
        item["congrecaoNasal"] = quiz.thirdSymptomModel.congestaoNasal
        item["escorrendo"] = quiz.thirdSymptomModel.diarreia
        item["dorCorpo"] = quiz.thirdSymptomModel.dorCorpo
        item["dorCabeca"] = quiz.thirdSymptomModel.dorCabeca
        item["fadigaIncomum"] = quiz.thirdSymptomModel.fadigaIncomum
        item["vermelhidaoDosOlhos"] = quiz.thirdSymptomModel.vermelhidaoDosOlhos
        item["nauseasVomitos"] = quiz.thirdSymptomModel.nauseasVomitos

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
    }*/
}