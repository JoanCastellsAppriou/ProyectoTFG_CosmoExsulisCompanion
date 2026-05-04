package com.adarun.cosmoexsuliscompanion.data.dao

import androidx.room.*
import com.adarun.cosmoexsuliscompanion.data.model.Instance
import com.adarun.cosmoexsuliscompanion.data.relation.InstanceWithCharacters
import com.adarun.cosmoexsuliscompanion.data.relation.InstanceWithCombats
import kotlinx.coroutines.flow.Flow

@Dao
interface InstanceDao {
    @Insert
    suspend fun insert (instance: Instance): Long

    @Update
    suspend fun update (instance: Instance)

    @Delete
    suspend fun delete (instance: Instance)

    @Query ("DELETE FROM instance WHERE instanceId IN (:ids)")
    suspend fun deleteMultiple (ids: List<Int>)

    @Query ("SELECT * FROM instance ORDER BY createdAt DESC")
    fun getAll(): Flow<List<Instance>>

    @Query ("SELECT * FROM instance WHERE instanceId = :instanceId")
    suspend fun getById (instanceId: Int): Instance?

    @Transaction
    @Query("SELECT * FROM instance WHERE instanceId = :instanceId")
    suspend fun getWithCharacters(instanceId: Int): InstanceWithCharacters?

    @Transaction
    @Query("SELECT * FROM instance WHERE instanceId = :instanceId")
    suspend fun getWithCombats(instanceId: Int): InstanceWithCombats?
}