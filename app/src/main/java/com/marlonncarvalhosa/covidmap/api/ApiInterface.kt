package com.marlonncarvalhosa.covidmap.api

import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("countries")
    fun list(): Call<List<CountryModel>>
}