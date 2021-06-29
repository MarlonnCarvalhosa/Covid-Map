package com.marlonncarvalhosa.covidmap.model

import com.google.gson.annotations.SerializedName

class CountryModel {

    @SerializedName("updated")
    var updated: String = ""

    @SerializedName("country")
    var country: String = ""

    @SerializedName("cases")
    var cases: String = ""

    @SerializedName("todayCases")
    var todayCases: String = ""

    @SerializedName("deaths")
    var deaths: String = ""

    @SerializedName("todayDeaths")
    var todayDeaths: String = ""

    @SerializedName("recovered")
    var recovered: String = ""

    @SerializedName("todayRecovered")
    var todayRecovered: String = ""

    @SerializedName("active")
    var active: String = ""

    @SerializedName("tests")
    var tests: String = ""

    @SerializedName("population")
    var population: String = ""
}
