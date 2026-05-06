package com.adarun.cosmoexsuliscompanion.data.dao

import androidx.room.*
import com.adarun.cosmoexsuliscompanion.data.model.Action
import com.adarun.cosmoexsuliscompanion.data.model.enums.ActionType
import com.adarun.cosmoexsuliscompanion.data.model.enums.BehaviorType
import kotlinx.coroutines.flow.Flow

@Dao
interface ActionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(actions: List<Action>): List<Long>

    @Update
    suspend fun update (action: Action)

    @Delete
    suspend fun delete (action: Action)

    @Query ("SELECT * FROM `action` WHERE actionCode = :code")
    suspend fun getByCode (code: String): Action

    @Query ("SELECT * FROM `action` WHERE actionCode IN (:codes)")
    fun getMultipleByCode (codes: List<String>): Flow<List<Action>>

    @Query ("SELECT * FROM `action` ORDER BY name")
    fun getAll (): Flow<List<Action>>

    @Query ("SELECT * FROM `action` WHERE type = :type")
    fun getByType (type: ActionType): Flow<List<Action>>

    @Query ("SELECT * FROM `action` WHERE behavior = :behavior")
    fun getByBehavior (behavior: BehaviorType): Flow<List<Action>>
}