package com.example.raksha.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.raksha.data.Category
import com.example.raksha.data.SafetyTip
import com.example.raksha.repository.SafetyTipsRepository

class SafetyTipsViewModel : ViewModel() {

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> get() = _categories

    private val _tips = MutableLiveData<List<SafetyTip>>()
    val tips: LiveData<List<SafetyTip>> get() = _tips

    init {
        _categories.value = SafetyTipsRepository.categories

        _tips.value = SafetyTipsRepository.getTipsByCategory(SafetyTipsRepository.categories.first().name)
    }

    init {

        _categories.value = SafetyTipsRepository.categories.mapIndexed { index, category ->
            category.copy(isSelected = index == 0)
        }
        _tips.value = SafetyTipsRepository.getTipsByCategory(SafetyTipsRepository.categories.first().name)
    }


    fun selectCategory(selectedCategory: Category) {
        _categories.value = _categories.value?.map {
            it.copy(isSelected = it.name == selectedCategory.name)
        }
        _tips.value = SafetyTipsRepository.getTipsByCategory(selectedCategory.name)
    }
}
