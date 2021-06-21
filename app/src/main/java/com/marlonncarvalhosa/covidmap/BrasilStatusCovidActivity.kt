package com.marlonncarvalhosa.covidmap

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.marlonncarvalhosa.covidmap.api.ApiInterface
import com.marlonncarvalhosa.covidmap.api.ApiUtilities
import com.marlonncarvalhosa.covidmap.api.CountryModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


class BrasilStatusCovidActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_brasil_status_covid)

        val remote = ApiUtilities.createService(ApiInterface::class.java)
        val call: Call<List<CountryModel>> = remote.list()

        val list: MutableList<String> = mutableListOf()

        val response = call.enqueue(object : Callback<List<CountryModel>>{
            override fun onResponse(call: Call<List<CountryModel>>, response: Response<List<CountryModel>>) {
                list.addAll(listOf(mutableListOf(response.body()).toString()))

                for (i in 0 until list.size){
                    if (list[i] == "Brazil"){
                    }
                }

            }

            override fun onFailure(call: Call<List<CountryModel>>, t: Throwable) {
                val s = t.message
            }

        })

    }
}