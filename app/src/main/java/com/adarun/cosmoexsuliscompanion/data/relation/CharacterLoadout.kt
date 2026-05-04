package com.adarun.cosmoexsuliscompanion.data.relation

import androidx.room.*
import com.adarun.cosmoexsuliscompanion.data.model.*

data class CharacterLoadout(

    @Embedded
    val character: Character,

    // EQUIPMENT

    @Relation(
        parentColumn = "weaponCode",
        entityColumn = "equipmentCode"
    )
    val weapon: Equipment?,

    @Relation(
        parentColumn = "armorCode",
        entityColumn = "equipmentCode"
    )
    val armor: Equipment?,

    // ACTIONS

    @Relation(
        parentColumn = "charId",
        entityColumn = "actionCode",
        associateBy = Junction(
            value = CharacterActionXref::class,
            parentColumn = "charId",
            entityColumn = "actionCode"
        )
    )
    val actions: List<Action>,

    // SAVES

    @Relation(
        parentColumn = "charId",
        entityColumn = "charId"
    )
    val combatSaves: List<CharacterCombatSave>
)