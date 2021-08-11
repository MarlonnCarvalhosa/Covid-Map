package com.marlonncarvalhosa.covidmap.model

import java.io.Serializable

data class QuizModel(
    var positivoCovid: Boolean = false,
    var contatoComInfectado: Boolean = false,
    var secondSynthoms: HashMap<String, Boolean>?,
    var thirdSynthoms: HashMap<String, Boolean>?,
    var possibleInfected: Boolean = false,
    var latitude: Double = 0.0,
    var longitude: Double = 0.0,
    var intensity : Double = 0.0,
    var userId : String = "",
    var timeStamp : String = ""
) : Serializable
