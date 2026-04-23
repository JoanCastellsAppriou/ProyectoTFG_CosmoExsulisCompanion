package com.adarun.cosmoexsuliscompanion.data.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.adarun.cosmoexsuliscompanion.data.model.Character
import com.adarun.cosmoexsuliscompanion.data.model.Instance

data class InstanceWithCharacters (
    @Embedded
    val instance: Instance,

    @Relation (
        parentColumn = "instanceId",
        entityColumn = "instanceId"
    )
    val characters: List<Character>
)