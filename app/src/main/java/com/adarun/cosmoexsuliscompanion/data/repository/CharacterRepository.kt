package com.adarun.cosmoexsuliscompanion.data.repository

import com.adarun.cosmoexsuliscompanion.data.dao.CharacterDao
import com.adarun.cosmoexsuliscompanion.data.model.Character
import com.adarun.cosmoexsuliscompanion.data.model.Skills

class CharacterRepository (private val dao: CharacterDao) {

    suspend fun insert (instance: Int, name: String, skills: Skills, weapon: String?, armor: String?): Long {
        return dao.insert(Character(instanceId = instance, name = name, baseSkills = skills, weaponCode = weapon, armorCode = armor))
    }
    suspend fun insert (character: Character) = dao.insert(character)

    suspend fun update (id: Int, instance: Int, name: String, skills: Skills, weapon: String?, armor: String?) =
        dao.update(Character(charId = id, instance, name, skills, weapon, armor))

    suspend fun deleteMultiple (ids: List<Int>) = dao.deleteMultiple(ids)

    suspend fun getById (id: Int) = dao.getById(id)
    fun getByInstance(instance: Int) = dao.getByInstance(instance)
    suspend fun getLoadout (id: Int) = dao.getLoadout (id)
}