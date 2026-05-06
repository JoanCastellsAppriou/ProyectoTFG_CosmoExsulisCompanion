package com.adarun.cosmoexsuliscompanion.data.repository

import com.adarun.cosmoexsuliscompanion.data.dao.CharacterActionXrefDao
import com.adarun.cosmoexsuliscompanion.data.model.CharacterActionXref

class CharacterActionXrefRepository (private val dao: CharacterActionXrefDao) {

    suspend fun insert (characterId: Int, code: String) {
        dao.insert(CharacterActionXref(characterId, code))
    }

    suspend fun deleteMultiple (charId: Int) = dao.deleteMultiple(charId)

    suspend fun get (charId: Int) = dao.getActionCodesForCharacter(charId)
}