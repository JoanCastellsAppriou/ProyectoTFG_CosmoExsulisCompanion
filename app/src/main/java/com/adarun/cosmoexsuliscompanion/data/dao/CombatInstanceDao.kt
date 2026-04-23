package com.adarun.cosmoexsuliscompanion.data.dao

import androidx.room.*
import com.adarun.cosmoexsuliscompanion.data.model.CombatInstance
import com.adarun.cosmoexsuliscompanion.data.relation.CombatWithParticipants
import kotlinx.coroutines.flow.Flow

@Dao
interface CombatInstanceDao {
    @Insert
    suspend fun insert (combatInstance: CombatInstance): Long

    @Update
    suspend fun update (combatInstance: CombatInstance)

    @Delete
    suspend fun delete (combatInstance: CombatInstance)

    @Query ("SELECT * FROM combat_instance WHERE combatId = :combatId")
    suspend fun getCombatById (combatId: Int): CombatInstance?

    @Query ("SELECT * FROM combat_instance WHERE instanceId = :instanceId")
    fun getCombatsByInstance (instanceId: Int): Flow<List<CombatInstance>>

    @Transaction
    @Query ("SELECT * FROM combat_instance WHERE combatId = :combatId")
    suspend fun getCombatWithParticipants (combatId: Int): CombatWithParticipants?
}