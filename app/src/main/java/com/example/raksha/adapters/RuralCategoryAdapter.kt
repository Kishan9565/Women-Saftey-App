package com.example.raksha.adapters

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.raksha.R
import com.example.raksha.data.RuralCategory
import com.example.raksha.databinding.ItemResourceCategoryBinding

class RuralCategoryAdapter(
    private var categories: List<RuralCategory>,
    private val onClick: (RuralCategory) -> Unit
) : RecyclerView.Adapter<RuralCategoryAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(val binding: ItemResourceCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: RuralCategory) {
            binding.tvNewCategory.text = category.name
            binding.catImage.setImageResource(category.imageRes)

            if (category.isSelected) {
                binding.linearLayout.setBackgroundResource(R.drawable.category_chip_selected)
                binding.tvNewCategory.setTextColor(ContextCompat.getColor(binding.root.context, R.color.white))
                binding.catImage.setColorFilter(ContextCompat.getColor(binding.root.context, R.color.white))
            } else {
                binding.linearLayout.setBackgroundResource(R.drawable.category_chip_background)
                binding.tvNewCategory.setTextColor(ContextCompat.getColor(binding.root.context, R.color.light_sky_blue))
                binding.catImage.setColorFilter(ContextCompat.getColor(binding.root.context, R.color.light_sky_blue))
            }

            binding.linearLayout.setOnClickListener {
                // deselect all
                categories.forEach { it.isSelected = false }
                category.isSelected = true
                notifyDataSetChanged()

                onClick(category)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemResourceCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {

        holder.bind(categories[position])
    }

    override fun getItemCount(): Int = categories.size

    // call this to update adapter data after selection change
    fun updateCategories(newCategories: List<RuralCategory>) {
        categories = newCategories
        notifyDataSetChanged()
    }

}