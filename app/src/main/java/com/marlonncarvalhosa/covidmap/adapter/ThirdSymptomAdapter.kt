package com.marlonncarvalhosa.covidmap.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.marlonncarvalhosa.covidmap.R
import com.marlonncarvalhosa.covidmap.databinding.ItemSymptomBinding
import com.marlonncarvalhosa.covidmap.model.SecondSymptomModel
import com.marlonncarvalhosa.covidmap.model.ThirdSymptomModel

class ThirdSymptomAdapter(
    private val onSymtomSelectedListener : (ThirdSymptomModel) -> Unit,
    private val onSymptomDesselectedListener: (ThirdSymptomModel) -> Unit )
    : RecyclerView.Adapter<ThirdSymptomAdapter.ViewHolder>() {

    private var thirdSymptomName: List<ThirdSymptomModel> = ArrayList()
    lateinit var binding: ItemSymptomBinding
    inner class ViewHolder(binding: ItemSymptomBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(thirdSymptomName: ThirdSymptomModel) {
            binding.materialCheckBox.text = thirdSymptomName.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThirdSymptomAdapter.ViewHolder {
        binding = ItemSymptomBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ThirdSymptomAdapter.ViewHolder, position: Int) {
        val thirdSymptomModel = thirdSymptomName[position]
        holder.bind(thirdSymptomModel)
        binding.materialCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            when(isChecked) {
                true -> onSymtomSelectedListener(thirdSymptomModel)
                false -> onSymptomDesselectedListener(thirdSymptomModel)
            }
        }
    }

    override fun getItemCount(): Int = thirdSymptomName.size

}