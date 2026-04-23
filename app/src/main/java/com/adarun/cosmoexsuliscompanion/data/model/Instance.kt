package com.adarun.cosmoexsuliscompanion.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "instance")
data class Instance (
    @PrimaryKey(autoGenerate = true)
    val instanceId: Int = 0,
    val name: String,
    val createdAt: LocalDateTime = LocalDateTime.now()
)