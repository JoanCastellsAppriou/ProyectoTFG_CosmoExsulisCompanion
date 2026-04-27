package com.adarun.cosmoexsuliscompanion.data.repository

import com.adarun.cosmoexsuliscompanion.data.dao.CharacterDao
import com.adarun.cosmoexsuliscompanion.data.model.Character
import com.adarun.cosmoexsuliscompanion.data.model.Stats

class CharacterRepository (private val dao: CharacterDao) {
    fun getByInstance(instance: Int) = dao.getCharactersByInstance(instance)
    suspend fun getById (id: Int): Character {
        val character = dao.getCharacterById(id)
        if (character != null) return character
        else throw error("There is no Character with Id = $id")
    }

    suspend fun insert (instance: Int, name: String, stats: Stats, weapon: Int?, armor: Int?){
        dao.insert(Character(instanceId = instance, name = name, baseStats = stats, weaponId = weapon, armorId = armor))
    }
}