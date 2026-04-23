package com.adarun.cosmoexsuliscompanion.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.adarun.cosmoexsuliscompanion.data.model.enums.EquipmentType
import com.adarun.cosmoexsuliscompanion.data.model.enums.StatType

@Entity(tableName = "equipment")
data class Equipment (
    @PrimaryKey(autoGenerate = true)
    val eqId: Int = 0,
    val name: String,
    val type: EquipmentType,
    val linkedStat: StatType? = null,
    val diceCount: Int? = null,
    val strengthMod: Int? = null,
    val agilityMod: Int? = null,
    val resilienceMod: Int? = null,
    val gritMod: Int? = null,
    val convictionMod: Int? = null,
    val logicMod: Int? = null
) {
    fun toStatsModifiers(): Stats = Stats(
        strength = strengthMod ?: 0,
        agility = agilityMod ?: 0,
        resilience = resilienceMod ?: 0,
        grit = gritMod ?: 0,
        conviction = convictionMod ?: 0,
        logic = logicMod ?: 0
    )
}