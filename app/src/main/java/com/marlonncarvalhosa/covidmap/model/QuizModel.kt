package com.marlonncarvalhosa.covidmap.model

import java.io.Serializable

data class QuizModel(
    var positivoCovid: Boolean = false,
    var contatoComInfectado: Boolean = false,
    var synthoms : HashMap<String,Boolean>?,
    var thirdSymptomModel: ThirdSymptomModel?
) : Serializable
