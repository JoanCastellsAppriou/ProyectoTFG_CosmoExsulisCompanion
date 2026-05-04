package com.adarun.cosmoexsuliscompanion.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adarun.cosmoexsuliscompanion.data.repository.CharacterRepository
import com.adarun.cosmoexsuliscompanion.data.repository.CombatInstanceRepository
import com.adarun.cosmoexsuliscompanion.ui.viewmodel.enums.InstanceTab
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class InstanceDetailViewModel (
    private val instanceId: Int,
    private val characterRepo: CharacterRepository,
    private val combatRepo: CombatInstanceRepository
): ViewModel() {
    val characters = characterRepo.getByInstance(instanceId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val combats = combatRepo.getWithParticipantsByInstance(instanceId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Selection ========================================================================
        private val _selectedCharacters = MutableStateFlow<Set<Int>>(emptySet())
        val selectedCharacters = _selectedCharacters.asStateFlow()

        private val _selectedCombats = MutableStateFlow<Set<Int>>(emptySet())
        val selectedCombats = _selectedCombats.asStateFlow()

        val hasSelection = combine(
            _selectedCharacters,
            _selectedCombats
        ) { chars, combats ->
            chars.isNotEmpty() || combats.isNotEmpty()
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            false
        )

        fun clearSelection() {
            _selectedCharacters.value = emptySet()
            _selectedCombats.value = emptySet()
        }

        fun toggleSelection(id: Int) {
            when (_currentTab.value) {
                InstanceTab.CHARACTERS -> {
                    _selectedCharacters.update {
                        if (it.contains(id)) it - id else it + id
                    }
                }
                InstanceTab.COMBATS -> {
                    _selectedCombats.update {
                        if (it.contains(id)) it - id else it + id
                    }
                }
            }
        }

        fun deleteSelection() {
            viewModelScope.launch {
                when (_currentTab.value) {
                    InstanceTab.CHARACTERS -> {
                        val ids = _selectedCharacters.value
                        if (ids.isNotEmpty()) {
                            characterRepo.deleteMultiple(ids.toList())
                            _selectedCharacters.value = emptySet()
                            combatRepo.deleteEmptyCombats()
                        }
                    }
                    InstanceTab.COMBATS -> {
                        val ids = _selectedCombats.value
                        if (ids.isNotEmpty()) {
                            combatRepo.deleteMultiple(ids.toList())
                            _selectedCombats.value = emptySet()
                        }
                    }
                }
            }
        }
    //===================================================================================

    private val _currentTab = MutableStateFlow(InstanceTab.CHARACTERS)
    val currentTab: StateFlow<InstanceTab> = _currentTab

    fun setTab(tab: InstanceTab) {
        _currentTab.value = tab
        clearSelection()
    }

    fun createCombat() {
        val ids = _selectedCharacters.value
        if (ids.isEmpty()) return

        viewModelScope.launch {
            combatRepo.insert(instanceId, ids.toList())
            _selectedCharacters.value = emptySet()
        }
    }
}