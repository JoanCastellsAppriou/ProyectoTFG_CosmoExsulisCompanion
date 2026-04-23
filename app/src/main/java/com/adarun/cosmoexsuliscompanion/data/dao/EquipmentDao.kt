package com.adarun.cosmoexsuliscompanion.data.dao

import androidx.room.*
import com.adarun.cosmoexsuliscompanion.data.model.Equipment
import kotlinx.coroutines.flow.Flow

@Dao
interface EquipmentDao {
    @Insert
    suspend fun insert (equipment: Equipment): Long

    @Update
    suspend fun update (equipment: Equipment)

    @Delete
    suspend fun delete (equipment: Equipment)

    @Query ("SELECT * FROM equipment WHERE eqId = :eqId")
    suspend fun getEquipmentById (eqId: Int): Equipment?

    @Query ("SELECT * FROM equipment ORDER BY name")
    fun getAllEquipment (): Flow<List<Equipment>>
}