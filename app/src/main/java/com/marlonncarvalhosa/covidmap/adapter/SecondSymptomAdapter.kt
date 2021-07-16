package com.marlonncarvalhosa.covidmap.adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.marlonncarvalhosa.covidmap.R
import kotlinx.android.synthetic.main.item_symptom.view.*

class SecondSymptomAdapter(val secondSymptomName: Array<String>, private var context: Context) :
    RecyclerView.Adapter<SecondSymptomAdapter.ViewHolder>() {

    private var selectedItem = -1

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val symptomName: TextView = itemView.findViewById(R.id.tv_symptom)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SecondSymptomAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_symptom, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: SecondSymptomAdapter.ViewHolder, position: Int) {
        holder.symptomName.text = secondSymptomName[position]

        val list = mutableListOf<Int>()

        holder.itemView.button_symptom.setOnClickListener {
            selectedItem = position
            list.add(selectedItem)

            holder.itemView.button_symptom.backgroundTintList = context.let {
                ContextCompat.getColorStateList(context, R.color.primary_purple)
            }
            holder.itemView.tv_symptom.setTextColor(Color.WHITE)

            notifyItemChanged(position)
            Log.d("Posição", "$list")
        }

        if (list.contains(position)) {
            holder.itemView.button_symptom.backgroundTintList = context.let {
                ContextCompat.getColorStateList(context, R.color.transparent_purple)
            }
            holder.itemView.tv_symptom.setTextColor(Color.WHITE)
        }

    }

    override fun getItemCount(): Int {
        return secondSymptomName.size
    }

}