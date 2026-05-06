package com.adarun.cosmoexsuliscompanion.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adarun.cosmoexsuliscompanion.data.model.Action
import com.adarun.cosmoexsuliscompanion.data.model.Skills
import com.adarun.cosmoexsuliscompanion.data.model.enums.ActionType
import com.adarun.cosmoexsuliscompanion.data.model.enums.SkillType
import com.adarun.cosmoexsuliscompanion.data.repository.ActionRepository
import com.adarun.cosmoexsuliscompanion.data.repository.CharacterActionXrefRepository
import com.adarun.cosmoexsuliscompanion.data.repository.CharacterCombatSaveRepository
import com.adarun.cosmoexsuliscompanion.data.repository.CharacterRepository
import com.adarun.cosmoexsuliscompanion.data.repository.CombatInstanceRepository
import com.adarun.cosmoexsuliscompanion.data.repository.EquipmentRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.collections.all

class CreateCharacterViewModel (
    private val instanceId: Int,
    private val characterRepo: CharacterRepository,
    private val equipmentRepo: EquipmentRepository,
    private val actionRepo: ActionRepository,
    private val chaXactRepo: CharacterActionXrefRepository,
    private val refCharacterId: Int? = null,
    private val savesRepo: CharacterCombatSaveRepository,
    private val combatsRepo: CombatInstanceRepository
): ViewModel() {



    // NAME =============================================================================
        private val _name = MutableStateFlow("")
        val nameState = _name.asStateFlow()

        var name by mutableStateOf("")
            private set

        fun onNameChange (value: String) {
            name = value
            _name.value = value
        }
    //===================================================================================


    // STATS ============================================================================
        private val _skills = MutableStateFlow(Skills())

        val skills = _skills.asStateFlow()

        val currentSkills: Skills
            get() = skills.value

        fun updateStat(update: (Skills) -> Skills) {
            val updatedSkills = update(_skills.value)
            _skills.value = updatedSkills

            val validChosen = mutableSetOf<String>()

            _chosenAbilities.value.forEach { code ->
                val ability = abilities.value.find { it.actionCode == code }

                if (
                    ability != null &&
                    checkRequiredLevel(ability, updatedSkills)
                ) {
                    val chosenForSkill = validChosen.count { selectedCode ->
                        abilities.value.any {
                            it.actionCode == selectedCode &&
                                    it.skill == ability.skill
                        }
                    }

                    val maxChoices = numberOfChoicesBySkill(
                        ability.skill,
                        updatedSkills
                    )

                    if (chosenForSkill < maxChoices) {
                        validChosen += code
                    }
                }
            }

            _chosenAbilities.value = validChosen
        }
    //===================================================================================


    // WEAPON ===========================================================================
        val weapons = equipmentRepo.getWeapons()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )

        var weapon by mutableStateOf<String?>(null)

        fun selectWeapon (code: String?) {
            weapon = code
        }
    //===================================================================================


    // ARMOR ============================================================================
        val armors = equipmentRepo.getArmors()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )

        var armor by mutableStateOf<String?>(null)

        fun selectArmor (code: String?) {
            armor = code
        }
    //===================================================================================

    val isEquipmentReady = combine(weapons, armors) { w, a -> w.isNotEmpty() && a.isNotEmpty() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    // ACTIONS ==========================================================================
        val actions = actionRepo.getAllActions()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )

        val defaultActions = combine(actions, skills) { actionList, stats ->
            actionList.filter { checkRequiredLevel(it, stats) }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )
    //===================================================================================


    // ABILITIES ========================================================================
        val abilities = actionRepo.getAllAbilities()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

        private val _chosenAbilities = MutableStateFlow<Set<String>> (emptySet())
        val chosenAbilities: StateFlow<Set<String>> = _chosenAbilities.asStateFlow()

        val availableAbilities = combine(abilities, skills, _chosenAbilities) { actionList, skills, chosen ->
            actionList.filter { ability ->
                checkRequiredLevel(ability, skills) && areThereChoicesLeft(ability, skills, chosen) }
        }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )

        fun areThereChoicesLeft ( ability: Action, skills: Skills, chosen: Set<String>): Boolean {
            if (chosenAbilities.value.contains(ability.actionCode)) return true
            return numberOfChosenBySkill(ability.skill, chosen) < numberOfChoicesBySkill(ability.skill, skills)
        }

        fun numberOfChoicesBySkill (skill: SkillType, skills: Skills): Int {
            return when (skill) {
                SkillType.STRENGTH -> skills.strength / 5
                SkillType.RESILIENCE -> skills.resilience / 5
                SkillType.AGILITY -> skills.agility / 5
                SkillType.GRIT -> skills.grit / 5
                SkillType.CONVICTION -> skills.conviction / 5
                SkillType.LOGIC -> skills.logic / 5
            }
        }

        fun numberOfChosenBySkill (skill: SkillType, chosen: Set<String>): Int {
            return chosen.count { code ->
                abilities.value.any {
                    it.actionCode == code && it.skill == skill
                }
            }
        }

        fun toggleAbility (ability: Action) {
            if (chosenAbilities.value.contains(ability.actionCode)) {
                _chosenAbilities.value -= ability.actionCode
            }
            else {
                if (areThereChoicesLeft(ability, skills.value, chosenAbilities.value))
                    _chosenAbilities.value += ability.actionCode
            }
        }
    //===================================================================================


    //===================================================================================
    init {
        if ( refCharacterId != null) {
            viewModelScope.launch {
                val character = characterRepo.getById(refCharacterId)

                name = character.name
                _name.value = character.name

                _skills.value = character.baseSkills

                weapon = character.weaponCode
                armor = character.armorCode

                val chosenActions = chaXactRepo.get(refCharacterId).filter { actionCode ->
                    val action = actionRepo.getByCode(actionCode)
                    action.type == ActionType.ABILITY
                }
            }
        }
    }
    //===================================================================================

    fun checkRequiredLevel (action: Action, skills: Skills): Boolean {
        return when (action.skill) {
            SkillType.STRENGTH ->    skills.strength >=       action.reqLevel
            SkillType.AGILITY ->     skills.agility >=        action.reqLevel
            SkillType.RESILIENCE ->  skills.resilience >=     action.reqLevel
            SkillType.GRIT ->        skills.grit >=           action.reqLevel
            SkillType.CONVICTION ->  skills.conviction >=     action.reqLevel
            SkillType.LOGIC ->       skills.logic >=          action.reqLevel
        }
    }

    fun allChoicesExhausted(): Boolean {
        val available = availableAbilities.value
        val chosen = _chosenAbilities.value

        val noMoreChoices = available.all { ability ->
            chosen.contains(ability.actionCode)
        }

        return noMoreChoices
    }

    val isFormValid: StateFlow<Boolean> = combine(_name, _skills, availableAbilities, _chosenAbilities) {
        name, skills, available, chosen ->

        val noMoreChoices = available.all { ability ->
            chosen.contains(ability.actionCode)
        }

        name.isNotBlank() && skills.isValid() && noMoreChoices
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false
    )

    fun isValid(): Boolean = name.isNotBlank() && _skills.value.isValid() && allChoicesExhausted()

    fun saveCharacter (onDone: () -> Unit) {
        if (!isValid()) return


        viewModelScope.launch {
            val charId = if (refCharacterId != null) {
                characterRepo.update (
                    refCharacterId,
                    instanceId,
                    name,
                    currentSkills,
                    weapon,
                    armor
                )
                savesRepo.deleteMultiple(refCharacterId)
                combatsRepo.clean()
                chaXactRepo.deleteMultiple (refCharacterId)

                refCharacterId
            }
            else {
                characterRepo.insert (
                    instanceId,
                    name,
                    currentSkills,
                    weapon,
                    armor
                ).toInt()
            }

            val actionsToAssign =
                defaultActions.value.map { action -> action.actionCode } + chosenAbilities.value.toList()

            actionsToAssign.forEach { actionCode ->
                chaXactRepo.insert(charId, actionCode)
            }

            onDone()
        }
    }
}
