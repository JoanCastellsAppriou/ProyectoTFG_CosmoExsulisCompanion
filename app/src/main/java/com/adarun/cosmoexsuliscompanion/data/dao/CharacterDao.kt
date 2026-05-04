package com.adarun.cosmoexsuliscompanion.data.dao

import androidx.room.*
import com.adarun.cosmoexsuliscompanion.data.model.Character
import com.adarun.cosmoexsuliscompanion.data.relation.CharacterLoadout
import com.adarun.cosmoexsuliscompanion.data.relation.CharacterWithActions
import com.adarun.cosmoexsuliscompanion.data.relation.CharacterWithSaves
import com.adarun.cosmoexsuliscompanion.data.relation.CharacterWithEquipment
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {
    @Insert
    suspend fun insert (character: Character): Long

    // UPDATE QUERIES ===================================================================
        @Update
        suspend fun update (character: Character)

        @Query ("UPDATE character SET instanceId = :newInstanceId WHERE charId = :charId")
        suspend fun migrate (charId: Int, newInstanceId: Int)
    //===================================================================================

    // DELETE QUERIES ===================================================================
        @Delete
        suspend fun delete (character: Character)

        @Query ("DELETE FROM character WHERE charId IN (:charIds)")
        suspend fun deleteMultiple (charIds: List<Int>)
    //===================================================================================

    @Query ("SELECT * FROM `character` WHERE charId = :charId")
    suspend fun getById (charId: Int): Character

    @Query ("SELECT * FROM `character` WHERE instanceId = :instanceId")
    fun getByInstance (instanceId: Int): Flow<List<Character>>


    // TRANSACTION QUERIES ==============================================================
        @Transaction
        @Query("SELECT * FROM `character` WHERE charId = :charId")
        suspend fun getLoadout(charId: Int): CharacterLoadout

        @Transaction
        @Query("SELECT * FROM `character` WHERE charId = :charId")
        suspend fun getWithActions(charId: Int): CharacterWithActions

        @Transaction
        @Query("SELECT * FROM `character` WHERE charId = :charId")
        suspend fun getWithEquipment(charId: Int): CharacterWithEquipment

        @Transaction
        @Query("SELECT * FROM `character` WHERE charId = :charId")
        suspend fun getWithSaves (charId: Int): CharacterWithSaves
    //===================================================================================
}