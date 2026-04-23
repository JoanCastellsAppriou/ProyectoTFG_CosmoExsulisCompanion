package com.adarun.cosmoexsuliscompanion.data.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.adarun.cosmoexsuliscompanion.data.model.Character
import com.adarun.cosmoexsuliscompanion.data.model.CharacterCombatSave
data class CharacterWithCombatSaves (
    @Embedded
    val character: Character,

    @Relation (
        parentColumn = "charId",
        entityColumn = "charId"
    )
    val combatSaves: List<CharacterCombatSave>
)