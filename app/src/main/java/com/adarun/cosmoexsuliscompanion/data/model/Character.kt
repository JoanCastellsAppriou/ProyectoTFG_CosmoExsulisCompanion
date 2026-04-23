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
            parentColumns = ["eqId"],
            childColumns = ["weaponId"],
            onDelete = ForeignKey.SET_NULL
        ),
        ForeignKey(
            entity = Equipment::class,
            parentColumns = ["eqId"],
            childColumns = ["armorId"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [
        Index(value = ["instanceId"]),
        Index(value = ["weaponId"]),
        Index(value = ["armorId"])
    ]
)
data class Character (
    @PrimaryKey(autoGenerate = true)
    val charId: Int = 0,
    val instanceId: Int,
    val name: String,
    @Embedded (prefix = "base_")
    val baseStats: Stats,
    @ColumnInfo(name = "weaponId")
    val weaponId: Int? = null,
    @ColumnInfo(name = "armorId")
    val armorId: Int? = null
)