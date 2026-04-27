package com.adarun.cosmoexsuliscompanion.data.repository

import com.adarun.cosmoexsuliscompanion.data.dao.CharacterCombatSaveDao
import com.adarun.cosmoexsuliscompanion.data.model.CharacterCombatSave
import com.adarun.cosmoexsuliscompanion.data.model.Stats

class CharacterCombatSaveRepository (private val dao: CharacterCombatSaveDao, private val combat: Int, private val character: Int) {
    fun getAll() = dao.getSavesByCombatId(combat)

    suspend fun insert (saveHealth: Int, saveTemper: Int, savePhStrain: Int, saveMeStrain: Int, saveStats: Stats){
        dao.insert(CharacterCombatSave(charId = character, combatId = combat, health = saveHealth, temper = saveTemper, physStrain = savePhStrain, mentStrain = saveMeStrain, modifiedStats = saveStats))
    }
}