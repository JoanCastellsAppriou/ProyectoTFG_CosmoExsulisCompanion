package com.adarun.cosmoexsuliscompanion.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adarun.cosmoexsuliscompanion.data.repository.InstanceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class InstanceListViewModel (
    private val repository: InstanceRepository
): ViewModel() {
    val instances = repository.getAll()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    private val _selectedInstances = MutableStateFlow<Set<Int>>(emptySet())
    val selectedInstances = _selectedInstances.asStateFlow()

    val hasSelection = _selectedInstances.map { it.isNotEmpty() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            false
        )

    fun clearSelection() {
        _selectedInstances.value = emptySet()
    }

    fun toggleSelection (id: Int) {
        _selectedInstances.update {
            if (it.contains(id)) it - id else it + id
        }
    }

    fun deleteSelection() {
        viewModelScope.launch {
            val ids = _selectedInstances.value
            if (ids.isNotEmpty()) {
                repository.deleteMultiple(ids.toList())
                clearSelection()
            }
        }
    }


    fun createInstance (name: String){
        viewModelScope.launch { repository.insert(name) }
    }
}