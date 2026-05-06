package com.adarun.cosmoexsuliscompanion.data.dao

import androidx.room.*
import com.adarun.cosmoexsuliscompanion.data.model.CharacterCombatSave
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterCombatSaveDao {
    @Insert
    suspend fun insert (save: CharacterCombatSave): Long

    @Update
    suspend fun update (save: CharacterCombatSave)

    @Delete
    suspend fun delete (save: CharacterCombatSave)

    @Query ("DELETE FROM save WHERE charId = :charId")
    suspend fun deleteMultiple (charId: Int)

    @Query ("SELECT * FROM save WHERE chSaveId = :saveId")
    suspend fun getById (saveId: Int): CharacterCombatSave?

    @Query ("SELECT * FROM save WHERE charId = :charId")
    fun getByCharacter (charId: Int): Flow<List<CharacterCombatSave>>

    @Query ("SELECT * FROM save WHERE combatId = :combatId")
    fun getByCombat (combatId: Int): Flow<List<CharacterCombatSave>>

    @Query ("SELECT * FROM save WHERE charId = :charId AND combatId = :combatId")
    suspend fun get (charId: Int, combatId: Int): CharacterCombatSave?
}