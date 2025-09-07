package com.example.raksha.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.example.raksha.data.AppDatabase
import com.example.raksha.data.RuralHelpResource
import com.example.raksha.data.Seeder
import com.example.raksha.repository.RuralHelpRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RuralHelpViewModel(private val repository: RuralHelpRepository) : ViewModel() {

    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory = _selectedCategory.asStateFlow()

    val resources: StateFlow<List<RuralHelpResource>> =
        _selectedCategory
            .flatMapLatest { category ->
                if (category.isNullOrEmpty()) {
                    repository.getAllResources()
                } else {
                    repository.getByCategory(category)
                }
            }
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun setCategory(category: String?) {
        _selectedCategory.value = category
    }

    fun insertResources(resources: List<RuralHelpResource>) {
        viewModelScope.launch {
            repository.insertAll(resources)
        }
    }
}


