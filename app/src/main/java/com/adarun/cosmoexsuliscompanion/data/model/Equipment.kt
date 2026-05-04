package com.adarun.cosmoexsuliscompanion.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.adarun.cosmoexsuliscompanion.data.model.enums.EquipmentType
import com.adarun.cosmoexsuliscompanion.data.model.enums.SkillType

@Entity(
    tableName = "equipment",
    indices = [
        Index ("equipmentCode")
    ]
)
data class Equipment (
    @PrimaryKey
    val equipmentCode: String,
    val name: String,
    val type: EquipmentType,
    val linkedStat: SkillType? = null,
    val diceCount: Int? = null,
    val strengthMod: Int? = null,
    val agilityMod: Int? = null,
    val resilienceMod: Int? = null,
    val gritMod: Int? = null,
    val convictionMod: Int? = null,
    val logicMod: Int? = null
) {
    fun toStatsModifiers(): Skills = Skills(
        strength = strengthMod ?: 0,
        agility = agilityMod ?: 0,
        resilience = resilienceMod ?: 0,
        grit = gritMod ?: 0,
        conviction = convictionMod ?: 0,
        logic = logicMod ?: 0
    )
}