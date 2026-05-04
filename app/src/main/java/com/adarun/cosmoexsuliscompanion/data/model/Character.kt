package com.adarun.cosmoexsuliscompanion.data.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "character",
    foreignKeys = [
        ForeignKey(
            entity = Instance::class,
            parentColumns = ["instanceId"],
            childColumns = ["instanceId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Equipment::class,
            parentColumns = ["equipmentCode"],
            childColumns = ["weaponCode"],
            onDelete = ForeignKey.SET_NULL
        ),
        ForeignKey(
            entity = Equipment::class,
            parentColumns = ["equipmentCode"],
            childColumns = ["armorCode"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [
        Index(value = ["instanceId"]),
        Index(value = ["weaponCode"]),
        Index(value = ["armorCode"])
    ]
)
data class Character (
    @PrimaryKey(autoGenerate = true)
    val charId: Int = 0,
    val instanceId: Int,
    val name: String,
    @Embedded (prefix = "base_")
    val baseSkills: Skills,
    @ColumnInfo(name = "weaponCode")
    val weaponCode: String? = null,
    @ColumnInfo(name = "armorCode")
    val armorCode: String? = null
) {
    fun getHealth(): Int {
        return baseSkills.resilience * 2
    }
    fun getTemper(): Int {
        return baseSkills.grit + baseSkills.conviction
    }
}