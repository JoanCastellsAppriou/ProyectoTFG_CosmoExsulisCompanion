package com.adarun.cosmoexsuliscompanion.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adarun.cosmoexsuliscompanion.data.repository.CharacterRepository
import com.adarun.cosmoexsuliscompanion.data.repository.CombatInstanceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class InstanceDetailViewModel (
    private val instanceId: Int,
    private val characterRepo: CharacterRepository,
    private val combatRepo: CombatInstanceRepository
): ViewModel() {
    val characters = characterRepo.getByInstance(instanceId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val combats = combatRepo.getByInstance(instanceId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _selected = MutableStateFlow<Set<Int>> (emptySet())
    val selected: StateFlow<Set<Int>> = _selected

    fun toggleCharacter (character: Int) {
        _selected.update { current ->
            if (current.contains(character)) current - character
            else current + character
        }
    }

    fun clearSelection() {
        _selected.value = emptySet()
    }

    fun createCombat() {
        val selectedIds = _selected.value
        if (selectedIds.isEmpty()) return

        viewModelScope.launch {
            combatRepo.insert(instanceId, selectedIds.toList())
            clearSelection()
        }
    }
}