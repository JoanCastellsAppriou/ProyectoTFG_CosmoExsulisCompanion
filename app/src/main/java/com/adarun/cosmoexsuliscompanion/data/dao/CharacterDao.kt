package com.adarun.cosmoexsuliscompanion.data.dao

import androidx.room.*
import com.adarun.cosmoexsuliscompanion.data.model.Character
import com.adarun.cosmoexsuliscompanion.data.relation.CharacterWithActions
import com.adarun.cosmoexsuliscompanion.data.relation.CharacterWithCombatSaves
import com.adarun.cosmoexsuliscompanion.data.relation.CharacterWithEquipment
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {
    @Insert
    suspend fun insert (character: Character): Long

    @Update
    suspend fun update (character: Character)

    @Delete
    suspend fun delete (character: Character)

    @Query ("SELECT * FROM `character` WHERE charId = :charId")
    suspend fun getCharacterById (charId: Int): Character?

    @Query ("SELECT * FROM `character` WHERE instanceId = :instanceId")
    fun getCharactersByInstance (instanceId: Int): Flow<List<Character>>

    @Query ("UPDATE character SET instanceId = :newInstanceId WHERE charId = :charId")
    suspend fun migrateCharacter (charId: Int, newInstanceId: Int)

    @Transaction
    @Query("SELECT * FROM `character` WHERE charId = :charId")
    suspend fun getCharacterWithEquipment(charId: Int): CharacterWithEquipment?

    @Transaction
    @Query("SELECT * FROM `character` WHERE charId = :charId")
    suspend fun getCharacterWithCombatSaves(charId: Int): CharacterWithCombatSaves?

    @Transaction
    @Query("SELECT * FROM `character` WHERE charId = :charId")
    suspend fun getCharacterWithActions(charId: Int): CharacterWithActions?
}