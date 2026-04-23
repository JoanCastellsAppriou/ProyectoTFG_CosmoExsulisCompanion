package com.adarun.cosmoexsuliscompanion.data.dao

import androidx.room.*
import com.adarun.cosmoexsuliscompanion.data.model.CharacterActionXref

@Dao
interface CharacterActionXrefDao {
    @Insert (onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert (xref: CharacterActionXref)

    @Delete
    suspend fun delete(xref: CharacterActionXref)

    @Query("SELECT acId FROM character_action_xref WHERE charId = :charId")
    suspend fun getActionIdsForCharacter(charId: Int): List<Int>
}