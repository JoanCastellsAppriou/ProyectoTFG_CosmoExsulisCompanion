package com.adarun.cosmoexsuliscompanion.data.repository

import com.adarun.cosmoexsuliscompanion.data.dao.CharacterCombatSaveDao
import com.adarun.cosmoexsuliscompanion.data.model.CharacterCombatSave

class CharacterCombatSaveRepository (private val dao: CharacterCombatSaveDao) {
    fun getByCombat (combat: Int) = dao.getByCombat (combat)
    fun getByCharacter (character: Int) = dao.getByCharacter (character)

    suspend fun insert (save: CharacterCombatSave){
        dao.insert(save)
    }
}