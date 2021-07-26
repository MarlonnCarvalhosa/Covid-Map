package com.marlonncarvalhosa.covidmap.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marlonncarvalhosa.covidmap.databinding.ItemSymptomBinding
import com.marlonncarvalhosa.covidmap.model.ThirdSymptomModel

class ThirdSymptomAdapter(
    private val onThirdSymtomSelectedListener : (ThirdSymptomModel) -> Unit,
    private val onThirdSymptomDesselectedListener: (ThirdSymptomModel) -> Unit )
    : RecyclerView.Adapter<ThirdSymptomAdapter.ViewHolder>() {

    private var thirdSymptomName: List<ThirdSymptomModel> = ArrayList()
    lateinit var binding: ItemSymptomBinding
    inner class ViewHolder(binding: ItemSymptomBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(thirdSymptomModel: ThirdSymptomModel, position: Int) {
            binding.materialCheckBox.text = thirdSymptomModel.thirdSymptomName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThirdSymptomAdapter.ViewHolder {
        binding = ItemSymptomBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ThirdSymptomAdapter.ViewHolder, position: Int) {
        val thirdSymptomModel = thirdSymptomName[position]
        holder.bind(thirdSymptomModel, position)
        binding.materialCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            when(isChecked) {
                true -> onThirdSymtomSelectedListener(thirdSymptomModel)
                false -> onThirdSymptomDesselectedListener(thirdSymptomModel)
            }
        }
    }

    override fun getItemCount(): Int = thirdSymptomName.size

    fun updateThirdSymptom(thirdSymptomModel: List<ThirdSymptomModel>) {
        this.thirdSymptomName = thirdSymptomModel
        notifyDataSetChanged()
    }

}