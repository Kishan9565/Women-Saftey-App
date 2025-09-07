package com.example.raksha.viewModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.raksha.repository.RuralHelpRepository

class RuralHelpViewModelFactory(
    private val repository: RuralHelpRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RuralHelpViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RuralHelpViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
