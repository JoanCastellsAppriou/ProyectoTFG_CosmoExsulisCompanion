package com.adarun.cosmoexsuliscompanion.data.database.dto

data class EquipmentDto(
    val equipmentCode: String,
    val name: String,
    val type: String,
    val linkedStat: String? = null,
    val diceCount: Int? = null,
    val strengthMod: Int? = null,
    val agilityMod: Int? = null,
    val resilienceMod: Int? = null,
    val gritMod: Int? = null,
    val convictionMod: Int? = null,
    val logicMod: Int? = null
)