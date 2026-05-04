package com.adarun.cosmoexsuliscompanion.data.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.adarun.cosmoexsuliscompanion.data.model.CharacterCombatSave
import com.adarun.cosmoexsuliscompanion.data.model.CombatInstance

data class CombatWithSaves (
    @Embedded
    val combat: CombatInstance,

    @Relation (
        parentColumn = "combatId",
        entityColumn = "chSaveId"
    )
    val saves: List<CharacterCombatSave>,

    @Relation (
        entity = CharacterCombatSave::class,
        parentColumn = "combatId",
        entityColumn = "combatId"
    )
    val savesWithCharacter: List<SaveWithCharacter>
)