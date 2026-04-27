package com.adarun.cosmoexsuliscompanion.data.repository

import com.adarun.cosmoexsuliscompanion.data.dao.CharacterActionXrefDao
import com.adarun.cosmoexsuliscompanion.data.model.CharacterActionXref

class CharacterActionXrefRepository (private val dao: CharacterActionXrefDao, private val character: Int) {
    suspend fun getAll() = dao.getActionIdsForCharacter(character)

    suspend fun insert(action: Int) {
        dao.insert(CharacterActionXref(character, action))
    }
}