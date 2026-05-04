package com.adarun.cosmoexsuliscompanion.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.adarun.cosmoexsuliscompanion.data.model.enums.ActionType
import com.adarun.cosmoexsuliscompanion.data.model.enums.BehaviorType
import com.adarun.cosmoexsuliscompanion.data.model.enums.SkillType
import com.adarun.cosmoexsuliscompanion.data.model.enums.TargetType

@Entity (
    tableName = "action",
    foreignKeys = [
        ForeignKey (
            entity = Action::class,
            parentColumns = ["actionCode"],
            childColumns = ["refActionCode"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [
        Index("actionCode"),
        Index("refActionCode")
    ]
)
data class Action (
    @PrimaryKey
    val actionCode: String,
    val name: String,
    val skill: SkillType,
    val reqLevel: Int,
    val diceThrows: Int,
    val type: ActionType,
    val behavior: BehaviorType, // Necesita incluir si es daño a salud o temple
    val allowedTarget: TargetType, // Propuesta: List<TargetType> para mayor complejidad a futuro
    val thenEffect: String? = null, // Probablemente necesite rehacerse
    val refActionCode: String? = null,
    val targetSkill: SkillType? = null,
    val selfPhysStrain: Int? = null,
    val selfMentStrain: Int? = null,
    val otherPhysStrain: Int? = null,
    val otherMentStrain: Int? = null
)