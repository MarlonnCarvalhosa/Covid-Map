package com.marlonncarvalhosa.covidmap.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.checkbox.MaterialCheckBox
import com.marlonncarvalhosa.covidmap.R
import com.marlonncarvalhosa.covidmap.databinding.ItemSymptomBinding
import com.marlonncarvalhosa.covidmap.model.ThirdSymptomModel

class ThirdSymptomAdapter(
    private val onThirdSymtomSelectedListener : (ThirdSymptomModel) -> Unit,
    private val onThirdSymptomDesselectedListener: (ThirdSymptomModel) -> Unit )
    : RecyclerView.Adapter<ThirdSymptomAdapter.ViewHolder>() {

    private val holders: MutableList<ThirdSymptomAdapter.ViewHolder> = ArrayList()
    private var thirdSymptomName: List<ThirdSymptomModel> = ArrayList()
    lateinit var binding: ItemSymptomBinding

    inner class ViewHolder(binding: ItemSymptomBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(thirdSymptomModel: ThirdSymptomModel, position: Int) {
            binding.materialCheckBox.text = thirdSymptomModel.thirdSymptomName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThirdSymptomAdapter.ViewHolder {
        binding = ItemSymptomBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ThirdSymptomAdapter.ViewHolder, position: Int) {
        holders.add(holder)
        val thirdSymptomModel = thirdSymptomName[position]
        holder.bind(thirdSymptomModel, position)
        if (thirdSymptomModel.thirdSymptomName == "Nenhum desses sintomas") { binding.materialCheckBox.tag = "REMOVEALL" }

        binding.materialCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            buttonView.apply {
                when (tag) {
                    "REMOVEALL" -> onRemoveAllSelected(this)
                    else -> checkStatus(isChecked, thirdSymptomModel)
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

    private fun checkStatus(checked: Boolean, thirdSymptomModel: ThirdSymptomModel) {
        when (checked) {
            true -> onThirdSymtomSelectedListener(thirdSymptomModel)
            false -> onThirdSymptomDesselectedListener(thirdSymptomModel)
        }
    }

    override fun getItemCount(): Int = thirdSymptomName.size

    fun updateThirdSymptom(thirdSymptomModel: List<ThirdSymptomModel>) {
        this.thirdSymptomName = thirdSymptomModel
        notifyDataSetChanged()
    }

}