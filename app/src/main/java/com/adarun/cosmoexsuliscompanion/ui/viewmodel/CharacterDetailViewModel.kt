package com.adarun.cosmoexsuliscompanion.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adarun.cosmoexsuliscompanion.data.model.enums.ActionType
import com.adarun.cosmoexsuliscompanion.data.relation.CharacterLoadout
import com.adarun.cosmoexsuliscompanion.data.repository.CharacterRepository
import com.adarun.cosmoexsuliscompanion.data.repository.CombatInstanceRepository
import com.adarun.cosmoexsuliscompanion.ui.viewmodel.enums.CharacterTab
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CharacterDetailViewModel(
    private val characterId: Int,
    private val characterRepo: CharacterRepository,
    private val combatRepo: CombatInstanceRepository
) : ViewModel() {

    // LOADOUT ==========================================================================
        private val _loadout = MutableStateFlow<CharacterLoadout?>(null)
        val loadout = _loadout.asStateFlow()

        init {
            viewModelScope.launch {
                _loadout.value = characterRepo.getLoadout(characterId)
            }
        }
    //===================================================================================


    // COMBATS ==========================================================================
    @OptIn(ExperimentalCoroutinesApi::class)
    val combatsWithSaves =
            loadout
                .filterNotNull()
                .flatMapLatest { currentLoadout ->
                    combatRepo.getWithParticipantsByIds(
                        currentLoadout.combatSaves.map { save ->
                            save.combatId
                        }
                    )
                }
                .stateIn(
                    viewModelScope,
                    SharingStarted.WhileSubscribed(5000),
                    emptyList()
                )
    //===================================================================================


    // ACTIONS ==========================================================================
        val actions =
            loadout
                .filterNotNull()
                .map { currentLoadout ->
                    currentLoadout.actions.filter { action ->
                        action.type == ActionType.ACTION
                    }
                }
                .stateIn(
                    viewModelScope,
                    SharingStarted.WhileSubscribed(5000),
                    emptyList()
                )

        val abilities =
            loadout
                .filterNotNull()
                .map { currentLoadout ->
                    currentLoadout.actions.filter { action ->
                        action.type == ActionType.ABILITY
                    }
                }
                .stateIn(
                    viewModelScope,
                    SharingStarted.WhileSubscribed(5000),
                    emptyList()
                )
    //===================================================================================


    // TAB ==============================================================================
        private val _currentTab = MutableStateFlow(CharacterTab.DETAIL)
        val currentTab: StateFlow<CharacterTab> = _currentTab

        fun setTab(tab: CharacterTab) {
            _currentTab.value = tab
        }
    //===================================================================================
}