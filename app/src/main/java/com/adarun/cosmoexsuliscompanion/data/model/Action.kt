package com.adarun.cosmoexsuliscompanion.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.adarun.cosmoexsuliscompanion.data.model.enums.ActionType
import com.adarun.cosmoexsuliscompanion.data.model.enums.StatType
import com.adarun.cosmoexsuliscompanion.data.model.enums.TargetType

@Entity (
    tableName = "action",
    foreignKeys = [
        ForeignKey (
            entity = Action::class,
            parentColumns = ["acId"],
            childColumns = ["refActionId"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [
        Index(value = ["refActionId"])
    ]
)
data class Action (
    @PrimaryKey (autoGenerate = true)
    val acId: Int = 0,
    val name: String,
    val skill: StatType,
    val reqLevel: Int,
    val diceThrows: Int,
    val type: ActionType,
    val allowedTarget: TargetType, // Propuesta: List<TargetType> para mayor complejidad a futuro
    val thenEffect: String? = null, // Probablemente necesite rehacerse
    val refActionId: Int? = null,
    val targetSkill: StatType? = null,
    val selfPhysStrain: Int? = null,
    val selfMentStrain: Int? = null,
    val otherPhysStrain: Int? = null,
    val otherMentStrain: Int? = null
)