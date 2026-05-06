package com.adarun.cosmoexsuliscompanion.data.dao

import androidx.room.*
import com.adarun.cosmoexsuliscompanion.data.model.CharacterCombatSave
import com.adarun.cosmoexsuliscompanion.data.model.CombatInstance
import com.adarun.cosmoexsuliscompanion.data.relation.CombatWithSaves
import com.adarun.cosmoexsuliscompanion.data.repository.CharacterCombatSaveRepository
import kotlinx.coroutines.flow.Flow

@Dao
interface CombatInstanceDao {
    @Insert
    suspend fun insert (combatInstance: CombatInstance): Long

    @Update
    suspend fun update (combatInstance: CombatInstance)

    @Delete
    suspend fun delete (combatInstance: CombatInstance)

    @Query("DELETE FROM combat_instance WHERE (SELECT COUNT(*) FROM save WHERE save.combatId = combat_instance.combatId ) <= 1")
    suspend fun deleteEmptyCombats()

    @Query ("DELETE FROM combat_instance WHERE combatId IN (:combatIds)")
    suspend fun deleteMultiple (combatIds: List<Int>)

    @Query ("SELECT * FROM combat_instance WHERE combatId = :combatId")
    suspend fun getById (combatId: Int): CombatInstance?

    @Query ("SELECT * FROM combat_instance WHERE combatId IN (:ids)")
    fun getMultipleById (ids: List<Int>): Flow<List<CombatInstance>>

    @Query ("SELECT * FROM combat_instance WHERE instanceId = :instanceId")
    fun getByInstance (instanceId: Int): Flow<List<CombatInstance>>

    @Transaction
    @Query ("SELECT * FROM combat_instance WHERE combatId = :combatId")
    suspend fun getWithSaves (combatId: Int): CombatWithSaves?

    @Transaction
    @Query ("SELECT * FROM combat_instance WHERE combatId IN (:ids)")
    fun getMultipleWithSaves (ids: List<Int>): Flow<List<CombatWithSaves>>

    @Transaction
    @Query ("SELECT * FROM combat_instance WHERE instanceId = :instanceId")
    fun getMultipleWithSaves (instanceId: Int): Flow<List<CombatWithSaves>>
}