package com.marlonncarvalhosa.covidmap.view

import android.os.Bundle
import android.util.Log
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.google.android.material.color.MaterialColors
import com.google.android.material.transition.platform.MaterialArcMotion
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import com.marlonncarvalhosa.covidmap.R
import com.marlonncarvalhosa.covidmap.api.ApiService
import com.marlonncarvalhosa.covidmap.api.RetrofitClient
import com.marlonncarvalhosa.covidmap.model.CountryModel
import kotlinx.android.synthetic.main.activity_brasil_status_covid.*
import org.eazegraph.lib.charts.PieChart
import org.eazegraph.lib.models.PieModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat

class BrasilStatusCovidActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
        setContentView(R.layout.activity_brasil_status_covid)

        setEnterSharedElementCallback(MaterialContainerTransformSharedElementCallback())
        window.sharedElementEnterTransition = buildTransitions()
        window.sharedElementExitTransition = buildTransitions()
        window.sharedElementReenterTransition = buildTransitions()

        super.onCreate(savedInstanceState)

        val pieChart = findViewById<PieChart>(R.id.piechart)
        val remote = RetrofitClient.createService(ApiService::class.java)
        val call: Call<List<CountryModel>> = remote.list()

        call.enqueue(object : Callback<List<CountryModel>>{
            override fun onResponse(call: Call<List<CountryModel>>, response: Response<List<CountryModel>>) {
                response.body()?.forEachIndexed { index, countryModel ->
                    if (response.body()!![index].country == "Brazil"){
                        tv_numero_confirmados.text = NumberFormat.getInstance().format(Integer.parseInt(response.body()!![index].cases))
                        tv_numero_ativos.text = NumberFormat.getInstance().format(Integer.parseInt(response.body()!![index].active))
                        tv_numero_recuperados.text = NumberFormat.getInstance().format(Integer.parseInt(response.body()!![index].recovered))
                        tv_numero_mortes.text = NumberFormat.getInstance().format(Integer.parseInt(response.body()!![index].deaths))
                        tv_numero_populacao.text = NumberFormat.getInstance().format(Integer.parseInt(response.body()!![index].population))

                        pieChart.addPieSlice(PieModel("Confirmados", response.body()!![index].cases.toFloat(),
                            ContextCompat.getColor(applicationContext, R.color.yellow)))
                        Log.d("TESTE", pieChart.toString())
                        pieChart.addPieSlice(PieModel("Ativos", response.body()!![index].active.toFloat(),
                            ContextCompat.getColor(applicationContext, R.color.blue)))
                        pieChart.addPieSlice(PieModel("Recuperados", response.body()!![index].recovered.toFloat(),
                            ContextCompat.getColor(applicationContext, R.color.green)))
                        pieChart.addPieSlice(PieModel("Mortes", response.body()!![index].deaths.toFloat(),
                            ContextCompat.getColor(applicationContext, R.color.red)))
                        pieChart.startAnimation()
                    }
                }
            }

            override fun onFailure(call: Call<List<CountryModel>>, t: Throwable) {
                val s = t.message
            }

        })

    }

    private fun buildTransitions(): MaterialContainerTransform {
        return MaterialContainerTransform().apply {
            addTarget(R.id.container)
            setAllContainerColors(MaterialColors.getColor(findViewById(R.id.container),
                R.attr.colorSurface
            ))
            duration = 600
            pathMotion = MaterialArcMotion()
            interpolator = FastOutSlowInInterpolator()
            fadeMode = MaterialContainerTransform.FADE_MODE_IN

        }

    }
}