package com.adarun.cosmoexsuliscompanion.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adarun.cosmoexsuliscompanion.data.repository.InstanceRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class InstanceListViewModel (private val repository: InstanceRepository): ViewModel() {
    val instances = repository.getAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun createInstance (name: String){
        viewModelScope.launch { repository.insert(name) }
    }
}