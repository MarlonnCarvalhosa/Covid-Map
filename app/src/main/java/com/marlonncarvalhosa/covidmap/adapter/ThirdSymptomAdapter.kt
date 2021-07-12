package com.marlonncarvalhosa.covidmap.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.marlonncarvalhosa.covidmap.R

class ThirdSymptomAdapter(val thirdSymptomName: Array<String>):
    RecyclerView.Adapter<ThirdSymptomAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val symptomName: TextView = itemView.findViewById(R.id.tv_symptom)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThirdSymptomAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_symptom, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ThirdSymptomAdapter.ViewHolder, position: Int) {
        holder.symptomName.text = thirdSymptomName[position]
    }

    override fun getItemCount(): Int {
        return thirdSymptomName.size
    }
}