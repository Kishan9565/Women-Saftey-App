package com.example.raksha.viewModel

import androidx.lifecycle.*
import com.example.raksha.data.EmergencyContact
import com.example.raksha.repository.EmergencyRepository
import kotlinx.coroutines.launch

class EmergencyViewModel(private val repository: EmergencyRepository) : ViewModel() {

    val allContacts: LiveData<List<EmergencyContact>> =
        repository.allContacts.asLiveData()

    fun insert(contact: EmergencyContact) = viewModelScope.launch {
        repository.insert(contact)
    }

    fun delete(contact: EmergencyContact) = viewModelScope.launch {
        repository.delete(contact)
    }
}

class EmergencyViewModelFactory(private val repository: EmergencyRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EmergencyViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EmergencyViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
