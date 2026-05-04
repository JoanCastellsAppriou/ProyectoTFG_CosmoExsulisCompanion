package com.adarun.cosmoexsuliscompanion.data.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.adarun.cosmoexsuliscompanion.data.model.Character
import com.adarun.cosmoexsuliscompanion.data.model.Equipment

data class CharacterWithEquipment (
    @Embedded
    val character: Character,

    @Relation (
        parentColumn = "weaponCode",
        entityColumn = "equipmentCode"
    )
    val weapon: Equipment?,

    @Relation (
        parentColumn = "armorCode",
        entityColumn = "equipmentCode"
    )
    val armor: Equipment?
)