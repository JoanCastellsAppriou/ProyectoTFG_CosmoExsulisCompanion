package com.adarun.cosmoexsuliscompanion.data.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.adarun.cosmoexsuliscompanion.data.model.Character
import com.adarun.cosmoexsuliscompanion.data.model.Equipment

data class CharacterWithEquipment (
    @Embedded
    val character: Character,

    @Relation (
        parentColumn = "weaponId",
        entityColumn = "eqId"
    )
    val weapon: Equipment?,

    @Relation (
        parentColumn = "armorId",
        entityColumn = "eqId"
    )
    val armor: Equipment?
)