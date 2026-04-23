package com.adarun.cosmoexsuliscompanion.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "character_action_xref",
    primaryKeys = ["charId", "acId"],
    foreignKeys = [
        ForeignKey(
            entity = Character::class,
            parentColumns = ["charId"],
            childColumns = ["charId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Action::class,
            parentColumns = ["acId"],
            childColumns = ["acId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["acId"]),
        Index(value = ["charId"])
    ]
)
data class CharacterActionXref (
    val charId: Int,
    val acId: Int
)