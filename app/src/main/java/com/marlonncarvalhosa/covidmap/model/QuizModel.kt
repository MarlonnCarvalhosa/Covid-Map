package com.marlonncarvalhosa.covidmap.model

data class QuizModel(
    var positivoCovid: Boolean = false,
    var contatoComInfectado: Boolean = false,
    var timeStamp: String = "",
    var secondSymptomModel: SecondSymptomModel,
    var thirdSymptomModel: ThirdSymptomModel
)
