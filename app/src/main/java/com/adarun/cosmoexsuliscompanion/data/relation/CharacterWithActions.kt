package com.adarun.cosmoexsuliscompanion.data.relation

import androidx.room.*
import com.adarun.cosmoexsuliscompanion.data.model.Action
import com.adarun.cosmoexsuliscompanion.data.model.Character
import com.adarun.cosmoexsuliscompanion.data.model.CharacterActionXref

data class CharacterWithActions (
    @Embedded
    val character: Character,

    @Relation (
        parentColumn = "charId",
        entityColumn = "actionCode",
        associateBy = Junction (
            value = CharacterActionXref::class,
            parentColumn = "charId",
            entityColumn = "actionCode"
        )
    )
    val actions: List<Action>
)