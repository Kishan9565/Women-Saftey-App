package com.example.raksha.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.raksha.data.SafetyTip
import com.example.raksha.databinding.ItemSafetyTipBinding

class SafetyTipsAdapter(
    private var tips: List<SafetyTip>
) : RecyclerView.Adapter<SafetyTipsAdapter.TipViewHolder>() {

    inner class TipViewHolder(val binding: ItemSafetyTipBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tip: SafetyTip) {
            binding.tvTitle.text = tip.title
            binding.tvDescription.text = tip.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TipViewHolder {
        val binding = ItemSafetyTipBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TipViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TipViewHolder, position: Int) {
        holder.bind(tips[position])
    }

    override fun getItemCount(): Int = tips.size

    fun updateTips(newTips: List<SafetyTip>) {
        tips = newTips
        notifyDataSetChanged()
    }
}
