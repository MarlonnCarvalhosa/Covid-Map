package com.marlonncarvalhosa.covidmap.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiUtilities private constructor(){

    companion object {

        private lateinit var retrofit: Retrofit
        private val baseUrl = "https://disease.sh/v3/covid-19/"

        private fun getApiInterface(): Retrofit {
            val httpClient = OkHttpClient.Builder()
            if (!::retrofit.isInitialized) {
                retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }

        fun <S> createService(serviceClass: Class<S>): S {
            return getApiInterface().create(serviceClass)
        }

    }

}