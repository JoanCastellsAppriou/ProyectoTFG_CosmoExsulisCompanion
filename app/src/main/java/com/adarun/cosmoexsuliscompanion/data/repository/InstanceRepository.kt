package com.adarun.cosmoexsuliscompanion.data.repository

import com.adarun.cosmoexsuliscompanion.data.dao.InstanceDao
import com.adarun.cosmoexsuliscompanion.data.model.Instance
import java.time.LocalDateTime

class InstanceRepository (private val dao: InstanceDao) {
    fun getAll() = dao.getAllInstances()

    suspend fun insert (name: String) {
        dao.insert(Instance(name = name, createdAt = LocalDateTime.now()))
    }
}