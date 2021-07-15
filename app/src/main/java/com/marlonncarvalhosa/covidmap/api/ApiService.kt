package com.marlonncarvalhosa.covidmap.api

import com.marlonncarvalhosa.covidmap.model.CountryModel
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("countries")
    fun list(): Call<List<CountryModel>>
}