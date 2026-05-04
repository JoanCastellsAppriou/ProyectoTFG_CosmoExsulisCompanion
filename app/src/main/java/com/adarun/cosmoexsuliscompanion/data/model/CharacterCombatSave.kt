package com.adarun.cosmoexsuliscompanion.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity (
    tableName = "save",
    foreignKeys = [
        ForeignKey (
            entity = Character::class,
            parentColumns = ["charId"],
            childColumns = ["charId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey (
            entity = CombatInstance::class,
            parentColumns = ["combatId"],
            childColumns = ["combatId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["charId", "combatId"], unique = true),
        Index(value = ["charId"]),
        Index(value = ["combatId"])
    ]
)
data class CharacterCombatSave (
    @PrimaryKey (autoGenerate = true)
    val chSaveId: Int = 0,
    val charId: Int,
    val combatId: Int,
    val health: Int,
    val temper: Int,
    val physStrain: Int,
    val mentStrain: Int,
    @Embedded (prefix = "mod_")
    val modifiedSkills: Skills
)