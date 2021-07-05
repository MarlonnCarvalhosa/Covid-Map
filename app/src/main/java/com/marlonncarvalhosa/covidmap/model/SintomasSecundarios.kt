package com.marlonncarvalhosa.covidmap.model

data class SintomasSecundarios(
    var dorGarganta: Boolean = false,
    var congrecaoNasal: Boolean = false,
    var escorrendo: Boolean = false,
    var dorCorpo: Boolean = false,
    var dorCabeca: Boolean = false,
    var fadigaIncomum: Boolean = false,
    var vermelhidaoDosOlhos: Boolean = false,
    var nauseasVomitos: Boolean = false
)
