package com.adarun.cosmoexsuliscompanion.data.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.adarun.cosmoexsuliscompanion.data.model.CharacterCombatSave
import com.adarun.cosmoexsuliscompanion.data.model.CombatInstance

data class CombatWithParticipants (
    @Embedded
    val combat: CombatInstance,

    @Relation (
        parentColumn = "combatId",
        entityColumn = "combatId"
    )
    val participants: List<CharacterCombatSave>
)