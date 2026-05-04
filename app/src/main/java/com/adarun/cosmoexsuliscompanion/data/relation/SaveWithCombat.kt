package com.adarun.cosmoexsuliscompanion.data.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.adarun.cosmoexsuliscompanion.data.model.CharacterCombatSave
import com.adarun.cosmoexsuliscompanion.data.model.CombatInstance

data class SaveWithCombat (
    @Embedded
    val save: CharacterCombatSave,

    @Relation (
        parentColumn = "combatId",
        entityColumn = "combatId"
    )
    val combat: CombatInstance
)