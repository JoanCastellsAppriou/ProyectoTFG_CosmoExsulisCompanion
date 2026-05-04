package com.adarun.cosmoexsuliscompanion.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "character_action_xref",
    primaryKeys = ["charId", "actionCode"],
    foreignKeys = [
        ForeignKey(
            entity = Character::class,
            parentColumns = ["charId"],
            childColumns = ["charId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Action::class,
            parentColumns = ["actionCode"],
            childColumns = ["actionCode"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["actionCode"]),
        Index(value = ["charId"])
    ]
)
data class CharacterActionXref (
    val charId: Int,
    val actionCode: String
)