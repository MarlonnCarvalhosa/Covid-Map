package com.marlonncarvalhosa.covidmap.api

import retrofit2.Call
import retrofit2.http.GET

interface CountryService {

    @GET("countries")
    fun list(): Call<List<CountryModel>>
}