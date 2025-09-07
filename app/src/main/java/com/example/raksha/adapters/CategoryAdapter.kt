package com.example.raksha.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.raksha.R
import com.example.raksha.data.Category
import com.example.raksha.databinding.ItemCategoryBinding

class CategoryAdapter(
    private var categories: List<Category>,
    private val onClick: (Category) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {
            binding.tvCategory.text = category.name

            if (category.isSelected) {
                binding.linearLayout.setBackgroundResource(R.drawable.category_chip_selected)
                binding.tvCategory.setTextColor(ContextCompat.getColor(binding.root.context, R.color.white))
            } else {
                binding.linearLayout.setBackgroundResource(R.drawable.category_chip_background)
                binding.tvCategory.setTextColor(ContextCompat.getColor(binding.root.context, R.color.light_sky_blue))
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
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount(): Int = categories.size

    fun updateCategories(newCategories: List<Category>) {
        categories = newCategories
        notifyDataSetChanged()
    }
}
