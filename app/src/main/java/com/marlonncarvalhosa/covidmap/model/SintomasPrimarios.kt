package com.marlonncarvalhosa.covidmap.model

data class SintomasPrimarios(
    var febre: Boolean = false,
    var arrepiosTremore: Boolean = false,
    var tosse: Boolean = false,
    var faltaDeAr: Boolean = false,
    var faltaPaladarOfato: Boolean = false
)
