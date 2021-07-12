package com.marlonncarvalhosa.covidmap.model

data class ThirdSymptomModel(
    var dorGarganta: Boolean = false,
    var congestaoNasal: Boolean = false,
    var diarreia: Boolean = false,
    var dorCorpo: Boolean = false,
    var dorCabeca: Boolean = false,
    var fadigaIncomum: Boolean = false,
    var vermelhidaoDosOlhos: Boolean = false,
    var nauseasVomitos: Boolean = false
)
