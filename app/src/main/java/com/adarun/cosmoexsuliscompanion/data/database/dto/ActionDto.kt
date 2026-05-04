package com.adarun.cosmoexsuliscompanion.data.database.dto

data class ActionDto(
    val actionCode: String,
    val name: String,
    val skill: String,
    val reqLevel: Int,
    val diceThrows: Int,
    val type: String,
    val behavior: String,
    val allowedTarget: String,
    val thenEffect: String? = null,
    val refActionCode: String? = null,
    val targetSkill: String? = null,
    val selfPhysStrain: Int? = null,
    val selfMentStrain: Int? = null,
    val otherPhysStrain: Int? = null,
    val otherMentStrain: Int? = null
)