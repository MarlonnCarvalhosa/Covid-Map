package com.marlonncarvalhosa.covidmap.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.checkbox.MaterialCheckBox
import com.marlonncarvalhosa.covidmap.R
import com.marlonncarvalhosa.covidmap.databinding.ItemSymptomBinding
import com.marlonncarvalhosa.covidmap.model.SecondSymptomModel

class SecondSymptomAdapter(
    private val onSymtomSelectedListener: (SecondSymptomModel) -> Unit,
    private val onSymptomDesselectedListener: (SecondSymptomModel) -> Unit
) : RecyclerView.Adapter<SecondSymptomAdapter.ViewHolder>() {

    private var secondSymptomModel: List<SecondSymptomModel> = ArrayList()
    lateinit var binding: ItemSymptomBinding
    private val holders: MutableList<SecondSymptomAdapter.ViewHolder> = ArrayList()

    inner class ViewHolder(binding: ItemSymptomBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(secondSymptomModel: SecondSymptomModel, position: Int) {
            binding.materialCheckBox.text = secondSymptomModel.secondSymptomName
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SecondSymptomAdapter.ViewHolder {
        binding = ItemSymptomBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SecondSymptomAdapter.ViewHolder, position: Int) {
        holders.add(holder)
        val secondSymptomModel = secondSymptomModel[position]
        holder.bind(secondSymptomModel, position)
        if (secondSymptomModel.secondSymptomName == "Nenhum desses sintomas") { binding.materialCheckBox.tag = "REMOVEALL" }

        binding.materialCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            buttonView.apply {
                when (tag) {
                    "REMOVEALL" -> onRemoveAllSelected(this)
                    else -> checkStatus(isChecked, secondSymptomModel)
                }
            }
        }
    }

    private fun onRemoveAllSelected(compoundButton: CompoundButton?) {
        if (binding.materialCheckBox.isChecked) {
            holders.forEach {
                it.itemView.findViewById<MaterialCheckBox>(R.id.materialCheckBox).isChecked = false
            }
            compoundButton?.isChecked = true
            holders.forEach {
                it.itemView.findViewById<MaterialCheckBox>(R.id.materialCheckBox).isEnabled = false
                compoundButton?.isEnabled = true
            }
        } else {
            compoundButton?.isChecked = false
            holders.forEach {
                it.itemView.findViewById<MaterialCheckBox>(R.id.materialCheckBox).isEnabled = true
            }
        }
    }

    private fun checkStatus(checked: Boolean, secondSymptomModel: SecondSymptomModel) {
        when (checked) {
            true -> onSymtomSelectedListener(secondSymptomModel)
            false -> onSymptomDesselectedListener(secondSymptomModel)
        }

    }

    override fun getItemCount(): Int = secondSymptomModel.size

    fun updateSymptom(secondSymptomModel: List<SecondSymptomModel>) {
        this.secondSymptomModel = secondSymptomModel
        notifyDataSetChanged()
    }

}