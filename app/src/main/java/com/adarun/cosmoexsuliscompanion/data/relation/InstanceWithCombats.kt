package com.adarun.cosmoexsuliscompanion.data.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.adarun.cosmoexsuliscompanion.data.model.CombatInstance
import com.adarun.cosmoexsuliscompanion.data.model.Instance

data class InstanceWithCombats (
    @Embedded
    val instance: Instance,

    @Relation (
        parentColumn = "instanceId",
        entityColumn = "instanceId"
    )
    val combats: List<CombatInstance>
)