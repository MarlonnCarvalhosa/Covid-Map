package com.marlonncarvalhosa.covidmap.model

import java.io.Serializable

data class QuizModel(
    var positivoCovid: Boolean = false,
    var contatoComInfectado: Boolean = false,
    var secondSynthoms: HashMap<String, Boolean>?,
    var thirdSynthoms: HashMap<String, Boolean>?,
    var possibleInfected: Boolean = false
) : Serializable
