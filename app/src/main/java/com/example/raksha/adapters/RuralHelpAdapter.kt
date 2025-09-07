package com.example.raksha.adapters

import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.raksha.R
import com.example.raksha.data.RuralHelpResource
import com.example.raksha.databinding.ItemRuralHelpBinding

class RuralHelpAdapter(
    private var resources: List<RuralHelpResource> = emptyList()
) : RecyclerView.Adapter<RuralHelpAdapter.ResourceViewHolder>() {

    inner class ResourceViewHolder(val binding: ItemRuralHelpBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RuralHelpResource) {

            val labelColor = ContextCompat.getColor(binding.root.context, R.color.yellow)
            val valueColor = ContextCompat.getColor(binding.root.context, R.color.light_sky_blue)

            setStyledText(binding.tvSymptoms, "Symptoms:- ", item.symptoms.joinToString(", "), labelColor, valueColor)
            setStyledText(binding.tvExplanation, "", item.explanation, labelColor, valueColor)
            setStyledText(binding.tvWhereToGo, "Where to Go:- ", item.whereToGo, labelColor, valueColor)
            setStyledText(binding.tvContact, "Contact:- ", item.contactNumber ?: "Not Available", labelColor, valueColor)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResourceViewHolder {
        val binding = ItemRuralHelpBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ResourceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ResourceViewHolder, position: Int) {
        holder.bind(resources[position])
    }

    override fun getItemCount(): Int = resources.size

    fun submitList(newList: List<RuralHelpResource>) {
        resources = newList
        notifyDataSetChanged()
    }

    private fun setStyledText(textView: TextView, label: String, value: String, labelColor: Int, valueColor: Int) {
        val fullText = if(label.isEmpty()) value else "$label $value"
        val spannable = SpannableString(fullText)
        if(label.isNotEmpty()) {
            spannable.setSpan(
                ForegroundColorSpan(labelColor),
                0,
                label.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            spannable.setSpan(
                ForegroundColorSpan(valueColor),
                label.length + 1,
                fullText.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        } else {
            spannable.setSpan(
                ForegroundColorSpan(valueColor),
                0,
                fullText.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        textView.text = spannable
    }

}
