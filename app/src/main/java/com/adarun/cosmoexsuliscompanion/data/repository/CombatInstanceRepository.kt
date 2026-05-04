package com.adarun.cosmoexsuliscompanion.data.repository

import com.adarun.cosmoexsuliscompanion.data.dao.CharacterCombatSaveDao
import com.adarun.cosmoexsuliscompanion.data.dao.CombatInstanceDao
import com.adarun.cosmoexsuliscompanion.data.model.Character
import com.adarun.cosmoexsuliscompanion.data.model.CharacterCombatSave
import com.adarun.cosmoexsuliscompanion.data.model.CombatInstance
import com.adarun.cosmoexsuliscompanion.data.relation.CombatWithSaves
import kotlinx.coroutines.flow.Flow

class CombatInstanceRepository (
    private val combats: CombatInstanceDao,
    private val participants: CharacterCombatSaveDao,
    private val characterRepo: CharacterRepository
) {
    suspend fun insert (instance: Int, characters: List<Int>) {
        val combatId = combats.insert(CombatInstance(instanceId = instance)).toInt()

        for (id in characters){
            val char: Character = characterRepo.getById(id)

            participants.insert(
                CharacterCombatSave(
                    charId = char.charId,
                    combatId = combatId,
                    health = char.getHealth(),
                    temper = char.getTemper(),
                    physStrain = 0,
                    mentStrain = 0,
                    modifiedSkills = char.baseSkills)
            )
        }
    }

    suspend fun delete (combat: CombatInstance) = combats.delete(combat)
    suspend fun deleteEmptyCombats() = combats.deleteEmptyCombats()
    suspend fun deleteMultiple (combatList: List<Int>) = combats.deleteMultiple(combatList)

    suspend fun getById (id: Int) = combats.getById(id)
    fun getByIds (ids: List<Int>) = combats.getMultipleById(ids)
    fun getByInstance (instance: Int) = combats.getByInstance(instance)
    suspend fun getWithParticipantsById (id: Int) = combats.getWithSaves(id)
    fun getWithParticipantsByIds (ids: List<Int>) = combats.getMultipleWithSaves(ids)
    fun getWithParticipantsByInstance (instance: Int): Flow<List<CombatWithSaves>> = combats.getMultipleWithSaves (instance)

}