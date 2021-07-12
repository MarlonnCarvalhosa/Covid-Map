package com.marlonncarvalhosa.covidmap.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.marlonncarvalhosa.covidmap.R

class SecondSymptomAdapter(val secondSymptomName: Array<String>):
    RecyclerView.Adapter<SecondSymptomAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val symptomName: TextView = itemView.findViewById(R.id.tv_symptom)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SecondSymptomAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_symptom, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: SecondSymptomAdapter.ViewHolder, position: Int) {
        holder.symptomName.text = secondSymptomName[position]
    }

    override fun getItemCount(): Int {
        return secondSymptomName.size
    }
}