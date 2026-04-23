package com.adarun.cosmoexsuliscompanion.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity (
    tableName = "combat_instance",
    foreignKeys = [
        ForeignKey (
            entity = Instance::class,
            parentColumns = ["instanceId"],
            childColumns = ["instanceId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["instanceId"])
    ]
)
data class CombatInstance (
    @PrimaryKey (autoGenerate = true)
    val combatId: Int = 0,
    val instanceId: Int
)