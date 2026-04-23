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

    @Query ("SELECT * FROM instance WHERE instanceId = :instanceId")
    suspend fun getInstanceById (instanceId: Int): Instance?

    @Query ("SELECT * FROM instance ORDER BY createdAt DESC")
    fun getAllInstances(): Flow<List<Instance>>

    @Transaction
    @Query("SELECT * FROM instance WHERE instanceId = :instanceId")
    suspend fun getInstanceWithCharacters(instanceId: Int): InstanceWithCharacters?

    @Transaction
    @Query("SELECT * FROM instance WHERE instanceId = :instanceId")
    suspend fun getInstanceWithCombats(instanceId: Int): InstanceWithCombats?
}