package com.adarun.cosmoexsuliscompanion.data.repository

import com.adarun.cosmoexsuliscompanion.data.dao.CharacterCombatSaveDao
import com.adarun.cosmoexsuliscompanion.data.dao.CombatInstanceDao
import com.adarun.cosmoexsuliscompanion.data.model.Character
import com.adarun.cosmoexsuliscompanion.data.model.CharacterCombatSave
import com.adarun.cosmoexsuliscompanion.data.model.CombatInstance

class CombatInstanceRepository (
    private val combats: CombatInstanceDao,
    private val participants: CharacterCombatSaveDao,
    private val characterRepo: CharacterRepository
) {
    fun getByInstance (instance: Int) = combats.getCombatsByInstance(instance)

    suspend fun insert (instance: Int, characters: List<Int>) {
        combats.insert(CombatInstance(instanceId = instance))

        for (id in characters){
            val char: Character = characterRepo.getById(id)
            participants.insert(
                CharacterCombatSave(
                    charId = char.charId,
                    combatId = 0, // GET NEWLY CREATED COMBAT ID IN SOME WAY
                    health = char.getHealth(),
                    temper = char.getTemper(),
                    physStrain = 0,
                    mentStrain = 0,
                    modifiedStats = char.baseStats)
            )
        }
    }
}