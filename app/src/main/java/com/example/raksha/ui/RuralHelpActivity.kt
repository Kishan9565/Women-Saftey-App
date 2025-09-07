package com.example.raksha.ui

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.raksha.R
import com.example.raksha.adapters.RuralCategoryAdapter
import com.example.raksha.adapters.RuralHelpAdapter
import com.example.raksha.data.AppDatabase
import com.example.raksha.data.RuralCategory
import com.example.raksha.data.Seeder
import com.example.raksha.databinding.ActivityRuralHelpBinding
import com.example.raksha.repository.RuralHelpRepository
import com.example.raksha.viewModel.RuralHelpViewModel
import com.example.raksha.viewModel.RuralHelpViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RuralHelpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRuralHelpBinding
    private lateinit var categoryAdapter: RuralCategoryAdapter
    private lateinit var helpAdapter: RuralHelpAdapter

    private val viewModel: RuralHelpViewModel by viewModels {
        val dao = AppDatabase.getDatabase(application).ruralHelpDao()
        val repo = RuralHelpRepository(dao)
        RuralHelpViewModelFactory(repo)
    }

    private val categories = mutableListOf<RuralCategory>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w = window
            w.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }

        binding = ActivityRuralHelpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1️⃣ Seed DB if empty
        lifecycleScope.launch {
            Seeder(AppDatabase.getDatabase(application).ruralHelpDao()).seedIfEmpty()
        }

        setupCategoryList()
        setupCategoryRecycler()
        setupHelpRecycler()
        observeFlows()
    }

    private fun setupCategoryList() {
        categories.addAll(
            listOf(
                RuralCategory("Domestic Violence", R.drawable.img_11),
                RuralCategory("Sexual Harassment", R.drawable.img_12),
                RuralCategory("Sexual Assault", R.drawable.img_12),
                RuralCategory("Sexual Abuse", R.drawable.img_12),
                RuralCategory("Child Marriage", R.drawable.img_13),
                RuralCategory("Child Rights", R.drawable.img_14),
                RuralCategory("Cyber Harassment", R.drawable.img_11),
                RuralCategory("Health Rights", R.drawable.img_11),
                RuralCategory("Legal Rights", R.drawable.img_11),
                RuralCategory("Public Services", R.drawable.img_11),
                RuralCategory("Social Discrimination", R.drawable.img_11),
                RuralCategory("Financial Abuse", R.drawable.img_11),
                RuralCategory("Human Trafficking", R.drawable.img_11),
            )
        )

        categories[0].isSelected = true
        viewModel.setCategory(categories[0].name)
    }

    private fun setupCategoryRecycler() {
        categoryAdapter = RuralCategoryAdapter(categories) { selectedCategory ->
            Log.d("CategoryClick", "Selected category: ${selectedCategory.name}")
            // update selected state
            categories.forEach { it.isSelected = it.name == selectedCategory.name }
            categoryAdapter.updateCategories(categories)

            // tell ViewModel which category is selected
            viewModel.setCategory(selectedCategory.name)
        }

        binding.categoryRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@RuralHelpActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = categoryAdapter
        }
    }

    private fun setupHelpRecycler() {
        helpAdapter = RuralHelpAdapter()
        binding.helpRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@RuralHelpActivity)
            adapter = helpAdapter
        }
    }

    private fun observeFlows() {
        lifecycleScope.launch {
            viewModel.resources.collectLatest { list ->
                Log.d("RuralHelpActivity", "Resources received: ${list.size} items")
                list.forEach { Log.d("RuralHelpActivity", it.toString()) }
                helpAdapter.submitList(list)
            }
        }
    }
}
