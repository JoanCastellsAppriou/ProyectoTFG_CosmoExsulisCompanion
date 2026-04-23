package com.adarun.cosmoexsuliscompanion.data.dao

import androidx.room.*
import com.adarun.cosmoexsuliscompanion.data.model.Action
import kotlinx.coroutines.flow.Flow

@Dao
interface ActionDao {
    @Insert
    suspend fun insert (action: Action): Long

    @Update
    suspend fun update (action: Action)

    @Delete
    suspend fun delete (action: Action)

    @Query ("SELECT * FROM `action` WHERE acId = :acId")
    suspend fun getActionById (acId: Int): Action?

    @Query ("SELECT * FROM `action` ORDER BY name")
    fun getAllActions (): Flow<List<Action>>
}