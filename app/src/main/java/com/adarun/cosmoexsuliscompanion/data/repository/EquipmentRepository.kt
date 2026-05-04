package com.adarun.cosmoexsuliscompanion.data.repository

import com.adarun.cosmoexsuliscompanion.data.dao.EquipmentDao
import com.adarun.cosmoexsuliscompanion.data.model.enums.EquipmentType

class EquipmentRepository (
    private val equipment: EquipmentDao
) {
    fun getAll() = equipment.getAll()
    fun getWeapons() = equipment.getByType (EquipmentType.WEAPON)
    fun getArmors() = equipment.getByType (EquipmentType.ARMOR)
}