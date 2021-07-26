package com.marlonncarvalhosa.covidmap.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marlonncarvalhosa.covidmap.databinding.ItemSymptomBinding
import com.marlonncarvalhosa.covidmap.model.SecondSymptomModel

class SecondSymptomAdapter(
    private val onSymtomSelectedListener : (SecondSymptomModel) -> Unit,
    private val onSymptomDesselectedListener: (SecondSymptomModel) -> Unit )
    : RecyclerView.Adapter<SecondSymptomAdapter.ViewHolder>() {

    private var secondSymptomModel: List<SecondSymptomModel> = ArrayList()
    lateinit var binding: ItemSymptomBinding
    inner class ViewHolder(binding: ItemSymptomBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(secondSymptomModel: SecondSymptomModel, position: Int) {
            binding.materialCheckBox.text = secondSymptomModel.sintomas
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SecondSymptomAdapter.ViewHolder {
        binding = ItemSymptomBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SecondSymptomAdapter.ViewHolder, position: Int) {
        val secondSymptomModel = secondSymptomModel[position]
        holder.bind(secondSymptomModel, position)
        binding.materialCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            when(isChecked) {
                true -> onSymtomSelectedListener(secondSymptomModel)
                false -> onSymptomDesselectedListener(secondSymptomModel)
            }
        }
    }

    override fun getItemCount(): Int = secondSymptomModel.size


    fun updateSymptom(secondSymptomModel: List<SecondSymptomModel>) {
        this.secondSymptomModel = secondSymptomModel
        notifyDataSetChanged()
    }

}