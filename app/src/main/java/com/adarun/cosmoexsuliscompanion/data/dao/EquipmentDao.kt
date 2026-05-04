package com.adarun.cosmoexsuliscompanion.data.dao

import androidx.room.*
import com.adarun.cosmoexsuliscompanion.data.model.Equipment
import com.adarun.cosmoexsuliscompanion.data.model.enums.EquipmentType
import kotlinx.coroutines.flow.Flow

@Dao
interface EquipmentDao {
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(equipment: List<Equipment>): List<Long>

    @Update
    suspend fun update (equipment: Equipment)

    @Delete
    suspend fun delete (equipment: Equipment)

    @Query ("SELECT * FROM equipment ORDER BY name")
    fun getAll (): Flow<List<Equipment>>

    @Query ("SELECT * FROM equipment WHERE equipmentCode = :code")
    suspend fun getByCode (code: String): Equipment

    @Query ("SELECT * FROM equipment WHERE type = :type")
    fun getByType (type: EquipmentType): Flow<List<Equipment>>
}