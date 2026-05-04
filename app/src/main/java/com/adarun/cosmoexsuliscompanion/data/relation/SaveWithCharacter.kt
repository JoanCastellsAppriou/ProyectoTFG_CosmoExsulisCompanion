package com.adarun.cosmoexsuliscompanion.data.relation

import androidx.room.*
import com.adarun.cosmoexsuliscompanion.data.model.Character
import com.adarun.cosmoexsuliscompanion.data.model.CharacterCombatSave

data class SaveWithCharacter (
    @Embedded
    val save: CharacterCombatSave,

    @Relation (
        parentColumn = "charId",
        entityColumn = "charId"
    )
    val character: Character
)