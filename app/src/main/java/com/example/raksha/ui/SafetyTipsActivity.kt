package com.example.raksha.ui


import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.raksha.R
import com.example.raksha.adapters.CategoryAdapter
import com.example.raksha.adapters.SafetyTipsAdapter
import com.example.raksha.databinding.ActivitySafetyTipsBinding
import com.example.raksha.viewModel.SafetyTipsViewModel

class SafetyTipsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySafetyTipsBinding
    private val viewModel: SafetyTipsViewModel by viewModels()

    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var tipsAdapter: SafetyTipsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w = window
            w.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }

        binding = ActivitySafetyTipsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setupCategoryRecycler()
        setupTipsRecycler()
        observeViewModel()
    }

    private fun setupCategoryRecycler() {
        categoryAdapter = CategoryAdapter(emptyList()) { category ->
            viewModel.selectCategory(category)
        }
        binding.rvCategories.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        binding.rvCategories.adapter = categoryAdapter
    }

    private fun setupTipsRecycler() {
        tipsAdapter = SafetyTipsAdapter(emptyList())
        binding.rvTips.layoutManager = LinearLayoutManager(this)
        binding.rvTips.adapter = tipsAdapter
    }

    private fun observeViewModel() {
        viewModel.categories.observe(this) { categories ->
            categoryAdapter.updateCategories(categories)
        }

        viewModel.tips.observe(this) { tips ->
            tipsAdapter.updateTips(tips)
        }
    }

}